package lock.controller;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 秒杀控制器
 *
 * @author Oliver
 * @date 2023年02月02日 22:31
 */
@RestController
@RequestMapping(value = "distributed-lock")
public class SeckillController {

    private static final String GOODS_NUMBER_REDIS_KEY = "sk:0008";
    public static final String REDIS_LOCK_KEY = "redis-lock";
    @Value("${server.port}")
    public String port;
    @Value("${spring.redis.host}")
    public String redisHost;
    @Value("${spring.redis.port}")
    public Integer redisPort;
    @Resource
    private StringRedisTemplate template;
    @Resource
    private Redisson redisson;

    /**
     * 存在并发问题
     *
     * @author Oliver
     */
    @GetMapping("/sk")
    public String sk() {
        String result = "已售罄";
        result = sell(result);
        return result;
    }

    /**
     * 存在多实例并发问题
     *
     * @author Oliver
     */
    @GetMapping("/sk-synchronized")
    public String skSynchronized() {
        String result = "已售罄";
        synchronized (this) {
            result = sell(result);
        }
        return result;
    }

    @GetMapping("/sk-lua")
    public String skNx() {
        String clientId = UUID.randomUUID().toString();
        String result = "抢购失败";
        try {
            // setIfAbsent 即为redis里的setnx
            // clientId 随机生成的ID，用于之后解锁时比较，确认加锁和解锁的实例相同
            // 设置过期时间：因为解锁之前系统崩溃或者停电等因素导致锁一直不释放
            Boolean locked = template.opsForValue()
                .setIfAbsent(REDIS_LOCK_KEY, clientId, 5, TimeUnit.SECONDS);
            // 这个过期时间如果小于程序执行时间，在程序执行过程中就会过期，之后使用Redisson解决
            if (Boolean.FALSE.equals(locked)) {
                return result;
            }
            // 加锁成功进行抢购
            result = sell(result);
        } finally {
            /*
            这里的逻辑为：解锁之前对比clientId，保证加解锁的是同一个客户端，分为两步：
            1.判断是否相同 2.解锁
            很明显这两个逻辑不是原子性的，有可能在别的实例获取锁后这个实例又给删除了。要使用lua脚本进行原子化
            if (Objects.equals(template.opsForValue().get(REDIS_NX_LOCK_KEY), clientId)) {
                //释放锁
                template.delete(REDIS_NX_LOCK_KEY);
            }*/
            // 使用jedis客户端，有eval方法
            // redis.call() 是lua对redis命令的调用函数
            // KEYS[1] 用来表示在redis 中用作键值的参数占位，主要用來传递在redis 中用作key值的参数。
            // ARGV[1] 用来表示在redis 中用作参数的占位，主要用来传递在redis中用做value值的参数。
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(30);
            config.setMaxIdle(10);
            JedisPool jedisPool = new JedisPool(config, redisHost, redisPort, 1800, "1");
            try (Jedis jedis = jedisPool.getResource()) {
                // 定义Lua脚本，注意每行后面要有个空格
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then "
                    + "return redis.call('del',KEYS[1]) "
                    + "end "
                    + "return 0";
                Object eval = jedis.eval(script, Collections.singletonList(REDIS_LOCK_KEY),
                    Collections.singletonList(clientId));
                if ("1".equals(eval.toString())) {
                    System.out.println("释放锁成功");
                } else {
                    System.out.println("释放锁失败");
                }
            }
        }
        return result;
    }

    @GetMapping("/sk-redisson")
    public String skRedisson() {
        String result = "抢购失败";
        RLock lock = redisson.getLock(REDIS_LOCK_KEY);
        try {
            // 异步的执行以下逻辑：
            // 查询是否有key,如果没有就设置并设置过期时间，如果有判断下是不是相同的线程，如果是则续命，计数器加一
            // 如果不是就返回当前锁的过期时间并转换成布尔
            //boolean tryLock = lock.tryLock();
            // 指定锁的过期时间为5秒。如果申请锁失败，则最长等待20S
            // 实现方式：在死循环中判断等待时间，在等待时间一直获取锁
            boolean tryLock = lock.tryLock(20, 5, TimeUnit.SECONDS);
            if (!tryLock) {
                return result;
            }
            // 加锁成功进行抢购
            result = sell(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放锁，做了以下逻辑
            // 判断是否是相同线程，不是啥也不管，是的话将计数器减1，没到0的话续命不删除，到0才删除
            lock.unlock();
        }
        return result;
    }

    /**
     * 售出商品
     *
     * @author Oliver
     */
    private String sell(String result) {
        String stock = template.opsForValue().get(GOODS_NUMBER_REDIS_KEY);
        int amount = stock == null ? 0 : Integer.parseInt(stock);
        if (amount > 0) {
            template.opsForValue().set("sk:0008", String.valueOf(--amount));
            result = "库存剩余：" + amount + "端口:" + port;
        }
        return result;
    }

}
