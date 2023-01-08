package database.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 基础数据模型
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
public abstract class BasicEntity<ID extends Serializable> extends ModifyEntity<ID> {

    /**
     * 是否已删除
     */
    @TableField
    private Boolean deleted;
}
