package operate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import operate.entity.PmsBrand;

/**
* @author User
* @description 针对表【pms_brand(品牌表)】的数据库操作Service
* @createDate 2023-02-08 17:35:48
*/
public interface PmsBrandService extends IService<PmsBrand> {

     int update(Long id, PmsBrand brand);

     int delete(Long id);

     PmsBrand getItem(Long id);
}
