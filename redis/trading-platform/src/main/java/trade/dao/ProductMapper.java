package trade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import trade.entity.Product;

/**
 * @author Oliver
 * @date 2023年01月31日 17:48
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询交易额总量
     * @author Oliver
     * @date 2023/1/31 18:37
     */
    Double getTurnover();

}
