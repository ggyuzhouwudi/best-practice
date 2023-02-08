package database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author Oliver
 * @date 2023年02月08日 16:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class GeneratorEntity {

    /**
     * 包名
     */
    private String packageName;

    /**
     * 包名
     */
    private String moduleName;
}
