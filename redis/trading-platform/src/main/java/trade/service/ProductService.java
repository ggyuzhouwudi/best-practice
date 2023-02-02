package trade.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;
import trade.entity.Product;

/**
 * @author Oliver
 * @date 2023年01月31日 17:50
 */
public interface ProductService extends IService<Product> {

    /**
     * 获取交易总量
     *
     * @return java.lang.Double
     * @author Oliver
     * @date 2023/1/31 18:41
     */
    Double getTurnover();

    /**
     * 查询所有
     *
     * @author Oliver
     */
    List<Product> all();

    /**
     * 根据名称获取产品
     * @author Oliver
     * @param name 名称
     */
    Product get(@NonNull String name);

    /**
     * 新增产品
     *
     * @param product 产品
     * @author Oliver
     */
    List<Product> add(@NonNull Product product);
}
