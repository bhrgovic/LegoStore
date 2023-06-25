package hr.algebra.java.web.lego.legostorewithdata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private int id;
    private String usernamefk;
    private Lego lego;

    private int quantity;
}
