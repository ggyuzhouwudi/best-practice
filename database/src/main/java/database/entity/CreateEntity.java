package database.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 可创建数据模型
 *
 * @author Oliver
 * @date 2022/5/26
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class CreateEntity<ID extends Serializable> {

    /**
     * 创建用户
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;
}
