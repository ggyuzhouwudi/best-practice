package security.module.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import security.module.entity.Menu;

/**
 * @author Oliver
 * @date 2023年01月06日 12:28
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenus();
}
