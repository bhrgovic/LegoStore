package hr.algebra.java.web.lego.legostorewithdata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private int id;
    private String usernamefk;
    private Lego lego;
    private BigDecimal price;
    private int quantity;

    public CartItem(String username, Lego legoPiece, int quantity1) {
        usernamefk= username;
        lego=legoPiece;
        quantity=quantity1;
    }
}
