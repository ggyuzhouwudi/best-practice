package operate.dao;

import operate.entity.PmsBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author User
* @description 针对表【pms_brand(品牌表)】的数据库操作Mapper
* @createDate 2023-02-08 17:35:48
* @Entity operate.entity.PmsBrand
*/
@Mapper
public interface PmsBrandMapper extends BaseMapper<PmsBrand> {

}




