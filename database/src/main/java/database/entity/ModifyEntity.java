package database.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 可更新数据模型
 *
 * @author Oliver
 * @date 2022/5/26
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ModifyEntity<ID extends Serializable> {

    /**
     * 创建用户
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新用户
     */
    private Long updater;

    /**
     * 更新时间
     */
    private Date updateTime;
}
