package trade.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import trade.dao.ProductMapper;
import trade.entity.Product;
import trade.service.ProductService;

/**
 * @author Oliver
 * @date 2023年01月31日 17:52
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements
    ProductService {

    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Double getTurnover() {
        // 查询缓存中有没有
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps("turnover");
        Object o = ops.get();
        if (o == null) {
            // 写入缓存，加个定时时间，总额不用实时更新
            o = this.baseMapper.getTurnover();
            ops.set(o, 10, TimeUnit.SECONDS);
        }
        return (Double) o;
    }

    @Override
    @Cacheable(value = "pc", key = "'product_all'")
    public List<Product> all() {
        return this.list();
    }

    @Override
    @Cacheable(value = "pc", key = "'product_'+#name")
    public Product get(@NonNull String name) {
        return this.lambdaQuery().eq(Product::getName, name).one();
    }

    @Override
    @CacheEvict(value = "pc", allEntries = true) //只要方法被执行，立即清楚PC缓存中的所有数据
    public List<Product> add(@NonNull Product product) {
        if (this.save(product)) {
            return this.list();
        }
        return new ArrayList<>();
    }

}
