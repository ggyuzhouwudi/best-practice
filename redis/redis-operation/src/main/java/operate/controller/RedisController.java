package operate.controller;

import operate.entity.PmsBrand;
import operate.service.PmsBrandService;
import operate.service.RedisService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Oliver
 * @date 2023年02月08日 19:05
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisService redisService;
    @Resource
    private PmsBrandService brandService;

    @GetMapping(value = "/simpleTest")
    public PmsBrand simpleTest() {
        PmsBrand brand = brandService.getItem(1L);
        String key = "redis:simple:1";
        redisService.set(key, brand);
        return brand;
    }

    @GetMapping(value = "/hashTest")
    public Map<Object, Object> hashTest() {
        String key = "redis:hash:1";
        Map<String, Object> map = new HashMap<>();
        map.put("name", "顽石");
        redisService.hSetAll(key, map);
        return redisService.hGetAll(key);
    }

    @GetMapping(value = "/setTest")
    public Set<Object> setTest() {
        List<PmsBrand> brandList = brandService.list();
        String key = "redis:set:all";
        redisService.sAdd(key, brandList.toArray());
        redisService.sRemove(key, brandList.get(0));
        return redisService.sMembers(key);
    }

    @GetMapping(value = "/listTest")
    public List<Object> listTest() {
        List<PmsBrand> brandList = brandService.list();
        String key = "redis:list:all";
        redisService.lPushAll(key, brandList.toArray());
        redisService.lRemove(key, 1, brandList.get(0));
        return redisService.lRange(key, 0, 3);
    }
}
