package database.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 可隔离数据模型
 *
 * @author Oliver
 * @date 2022/5/26
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class IsolateEntity<ID extends Serializable> {

    /**
     * 租户编号
     */
    private Integer tenant;
}
