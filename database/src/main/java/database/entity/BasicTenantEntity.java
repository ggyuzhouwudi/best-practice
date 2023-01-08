package database.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 多租户基础数据模型
 *
 * @param <ID> 主键类型
 * @author Oliver
 * @date 2021/6/25
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BasicTenantEntity<ID extends Serializable> extends BasicEntity<ID> {

    /**
     * 租户编号
     */
    private Integer tenant;
}
