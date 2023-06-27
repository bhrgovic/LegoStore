package hr.algebra.java.web.lego.legostorewithdata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {
    private int id;
    private String userOrdered;
    private BigDecimal price;
}
