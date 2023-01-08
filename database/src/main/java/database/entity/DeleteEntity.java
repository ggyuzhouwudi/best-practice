package database.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 可删除数据模型
 *
 * @author Oliver
 * @date 2022/5/26
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class DeleteEntity<ID extends Serializable> {

    /**
     * 是否已删除
     */
    @TableField
    private Boolean deleted;
}
