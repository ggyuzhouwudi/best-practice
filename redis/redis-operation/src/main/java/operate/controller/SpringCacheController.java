package operate.controller;

import operate.entity.PmsBrand;
import operate.service.PmsBrandService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 使用Spring Cache方式
 * @author Oliver
 * @date 2023年02月08日 17:54
 */
@RestController
@RequestMapping("spring/cache")
public class SpringCacheController {
    @Resource
    private PmsBrandService service;

    @GetMapping("/{id}")
    public PmsBrand getItem(@PathVariable Long id) {
        return service.getItem(id);
    }

    @PostMapping("/update")
    public Integer update(@RequestBody PmsBrand brand) {
        return service.update(brand.getId(), brand);
    }

    @PostMapping("/delete/{id}")
    public Integer delete(@PathVariable Long id) {
        return service.delete(id);
    }

}
