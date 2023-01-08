package database.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 多租户的树形基础数据模型
 *
 * @param <ID> 主键类型
 * @param <T>  子节点类型
 * @author Oliver
 * @date 2021/7/6
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BasicTenantTreeEntity<ID extends Serializable, T extends BasicTenantTreeEntity<ID, T>>
    extends BasicTreeEntity<ID, T> {

    /**
     * 租户编号
     */
    private Integer tenant;
}
