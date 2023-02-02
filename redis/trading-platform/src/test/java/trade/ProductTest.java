package trade;

import java.util.List;
import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import trade.dao.ProductMapper;
import trade.entity.Product;
import trade.service.ProductService;

/**
 * @author Oliver
 * @date 2023年01月31日 17:53
 */
@SpringBootTest(classes = TradeApplication.class)
public class ProductTest {

    @Resource
    private ProductService service;

    @Resource
    private ProductMapper mapper;
    @Test
    public void product() {
        List<Product> products = mapper.selectList(null);
        System.out.println("products.size() = " + products.size());
        List<Product> list = service.list();
        System.out.println(list);
    }

}
