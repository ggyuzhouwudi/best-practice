package trade.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trade.entity.Product;
import trade.service.ProductService;

/**
 * @author Oliver
 * @date 2023年01月31日 17:49
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService service;

    @GetMapping("/turnover")
    public Double index() {
        return service.getTurnover();
    }

    @GetMapping("/all")
    public List<Product> all() {
        return service.all();
    }

    @GetMapping("/get/{name}")
    public Product get(@PathVariable String name) {
        return service.get(name);
    }

    @PostMapping("/add")
    public List<Product> add(@RequestBody Product product) {
        return service.add(product);
    }

}
