package lock.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Value("${server.port}")
    public String port;
    @Resource
    private StringRedisTemplate template;

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
