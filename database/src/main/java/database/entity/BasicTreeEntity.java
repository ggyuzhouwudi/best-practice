package database.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 树形基础数据模型
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
public abstract class BasicTreeEntity<ID extends Serializable, T extends BasicTreeEntity<ID, T>> extends BasicEntity<ID>
        implements TreeEntity<ID, T> {
    /**
     * 上级主键
     */
    private ID parentId;

    /**
     * 树层级
     */
    private Integer level;

    /**
     * 序列号
     */
    private String sequence;

    /**
     * 子节点列表
     */
    @TableField(exist = false)
    private List<T> children;
}
