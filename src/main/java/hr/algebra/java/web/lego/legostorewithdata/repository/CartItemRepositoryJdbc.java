package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.CartItem;
import hr.algebra.java.web.lego.legostorewithdata.domain.Category;
import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;
import hr.algebra.java.web.lego.legostorewithdata.domain.ShoppingCart;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class CartItemRepositoryJdbc implements CartItemRepository {

    private static final String CART_ITEM_TABLE_NAME = "CartItems";
    private static final String CART_ITEM_TABLE_NAME_ID = "cart_item_id";

    private static final String SELECT_ALL_CART_ITEMS = "SELECT * FROM CartItems WHERE 1=1 ";

    private static final String SELECT_CART_ITEM = "SELECT * FROM CartItems WHERE usernamefk= ?";


    private static final String SELECT_CART_ITEM_ID = "SELECT * FROM CartItems WHERE cart_item_id=";

    private static final String DELETE_CART_ITEM = "DELETE FROM CartItems WHERE cart_item_id= ";

    private LegoRepository legoRepository;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public CartItemRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate =jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(CART_ITEM_TABLE_NAME)
                .usingGeneratedKeyColumns(CART_ITEM_TABLE_NAME_ID);
    }

    private CartItem mapRowToCartItem(ResultSet rs, int rowNum) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setId(rs.getInt(CART_ITEM_TABLE_NAME_ID));
        cartItem.setQuantity(rs.getInt("quantity"));

        int legoId = rs.getInt("lego_id");
        Lego lego = jdbcTemplate.queryForObject("SELECT * FROM Lego_pieces WHERE Id_Lego=?", new BeanPropertyRowMapper<>(Lego.class), legoId);
        cartItem.setLego(lego);

        // Calculate the price
        BigDecimal price = lego.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        cartItem.setPrice(price);

        return cartItem;
    }

    @Override
    public List<CartItem> getCartItems(String username) {
        return jdbcTemplate.query(SELECT_CART_ITEM , this::mapRowToCartItem, username);
    }

    @Override
    public CartItem getCartItem(int cartItemId) {
        String sql = "SELECT ci.cart_item_id, ci.quantity, ci.usernamefk, l.ID_LEGO, l.name, l.price,l.category " +
                "FROM CartItems ci " +
                "JOIN LEGO_PIECES l ON ci.Lego_id = l.ID_lego " +
                "WHERE ci.cart_item_id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{cartItemId}, (resultSet, rowNum) -> {
            CartItem cartItem = new CartItem();
            cartItem.setId(resultSet.getInt("cart_item_id"));
            cartItem.setQuantity(resultSet.getInt("quantity"));
            cartItem.setUsernamefk(resultSet.getString("usernamefk"));

            Lego lego = new Lego();
            lego.setId(resultSet.getInt("ID_LEGO"));
            lego.setName(resultSet.getString("name"));
            lego.setCategory(resultSet.getString("category"));
            lego.setPrice(resultSet.getBigDecimal("price"));

            cartItem.setLego(lego);
            BigDecimal bg= cartItem.getLego().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            cartItem.setPrice(bg);
            return cartItem;
        });
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        BigDecimal bg = cartItem.getLego().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        cartItem.setPrice(bg);
        String query = "UPDATE CartItems SET quantity=" + cartItem.getQuantity() +
                ", price=" + cartItem.getPrice() +
                " WHERE cart_item_id=" + cartItem.getId();
        jdbcTemplate.execute(query);
    }
    @Override
    public void deleteCartItem(CartItem cartItem) {
        String query = DELETE_CART_ITEM + cartItem.getId();
        jdbcTemplate.execute(query);
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        saveCartItemDetails(cartItem);
    }

    private void saveCartItemDetails(CartItem cartItem) {
        Map<String, Object> cartItemDetails = new HashMap<>();

        cartItemDetails.put(CART_ITEM_TABLE_NAME, cartItem.getId());
        cartItemDetails.put("usernamefk", cartItem.getUsernamefk());
        cartItemDetails.put("lego_id", cartItem.getLego().getId());
        cartItemDetails.put("quantity", cartItem.getQuantity());

        simpleJdbcInsert.executeAndReturnKey(cartItemDetails);
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return jdbcTemplate.query(SELECT_ALL_CART_ITEMS, this::mapRowToCartItem);
    }
}
