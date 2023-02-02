package trade.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品
 *
 * @author Oliver
 * @date 2023年01月31日 17:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    /**
     * 利率
     */
    private BigDecimal rate;

    /**
     * 募集金额
     */
    private BigDecimal amount;

    /**
     * 已募集金额
     */
    private BigDecimal raised;

    /**
     * 周期
     */
    private Integer cycle;

    /**
     * 产品募集结束时间
     */
    private LocalDateTime endTime;

}
