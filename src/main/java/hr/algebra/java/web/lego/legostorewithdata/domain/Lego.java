package hr.algebra.java.web.lego.legostorewithdata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lego {

    private String name;
    private Integer id;
    private String category;
    private BigDecimal price;

}
