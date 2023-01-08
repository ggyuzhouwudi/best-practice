package database.entity;

import java.io.Serializable;

/**
 * 数据模型接口
 *
 * @param <ID> 主键类型
 * @author Oliver
 * @date 2021/7/11
 */
public interface Entity<ID extends Serializable> extends Serializable {
    /**
     * 获取主键
     *
     * @return 主键
     */
    ID getId();

    /**
     * 设置主键
     *
     * @param id 主键
     */
    void setId(ID id);
}
