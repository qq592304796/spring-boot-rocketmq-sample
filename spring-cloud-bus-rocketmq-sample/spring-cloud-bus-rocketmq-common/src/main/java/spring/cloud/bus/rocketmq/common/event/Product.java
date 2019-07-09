package spring.cloud.bus.rocketmq.common.event;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jiangxinjun
 * @date 2019-05-08
 */
@AllArgsConstructor
@Data
public class Product {
    
    private Long id;
    
    private String name;

    private BigDecimal price;
}
