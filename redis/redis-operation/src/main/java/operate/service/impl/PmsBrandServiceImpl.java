package operate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import operate.config.RedisConfig;
import operate.dao.PmsBrandMapper;
import operate.entity.PmsBrand;
import operate.service.PmsBrandService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author User
 * @description 针对表【pms_brand(品牌表)】的数据库操作Service实现
 * @createDate 2023-02-08 17:35:48
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand>
        implements PmsBrandService {

    @Resource
    private PmsBrandMapper mapper;

    @CacheEvict(value = RedisConfig.REDIS_KEY_DATABASE, key = "'pms:brand:'+#id")
    @Override
    public int update(@NonNull Long id, @NonNull PmsBrand brand) {
        brand.setId(id);
        return mapper.updateById(brand);
    }

    @CacheEvict(value = RedisConfig.REDIS_KEY_DATABASE, key = "'pms:brand:'+#id")
    @Override
    public int delete(@NonNull Long id) {
        return mapper.deleteById(id);
    }

    @Cacheable(value = RedisConfig.REDIS_KEY_DATABASE, key = "'pms:brand:'+#id", unless = "#result==null")
    @Override
    public PmsBrand getItem(@NonNull Long id) {
        return mapper.selectById(id);
    }

}




