package lock.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Oliver
 * @date 2023年02月04日 22:17
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    public String redisHost;

    @Value("${spring.redis.port}")
    public Integer redisPort;

    @Bean
    public Redisson redisson() {
        // 单机方式创建
        Config config = new Config();
        config.useSingleServer()
            .setAddress("redis://" + redisHost + ":" + redisPort)
            .setPassword("1")
            .setDatabase(0);
        return (Redisson) Redisson.create(config);
    }

}
