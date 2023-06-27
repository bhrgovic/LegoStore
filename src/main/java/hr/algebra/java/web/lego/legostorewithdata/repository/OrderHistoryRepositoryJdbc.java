package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.OrderHistory;
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
public class OrderHistoryRepositoryJdbc implements OrderHistoryRepository {

    private static final String ORDER_HISTORY_TABLE_NAME = "orderHistory";
    private static final String ORDER_HISTORY_TABLE_NAME_ID = "IdOrderHistory";

    private static final String SELECT_ALL_ORDER_HISTORY = "SELECT * FROM orderHistory WHERE 1=1 ";

    private static final String SELECT_ORDER_HISTORY_BY_USER = "SELECT * FROM orderHistory WHERE user = ?";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public OrderHistoryRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(ORDER_HISTORY_TABLE_NAME)
                .usingGeneratedKeyColumns(ORDER_HISTORY_TABLE_NAME_ID);
    }

    private OrderHistory mapRowToOrderHistory(ResultSet rs, int rowNum) throws SQLException {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setId(rs.getInt(ORDER_HISTORY_TABLE_NAME_ID));
        orderHistory.setUserOrdered(rs.getString("userOrdered"));
        orderHistory.setPrice(rs.getBigDecimal("price"));
        return orderHistory;
    }


    @Override
    public List<OrderHistory> getOrderHistoryByUser(String username) {
        return jdbcTemplate.query(SELECT_ORDER_HISTORY_BY_USER, this::mapRowToOrderHistory, username);
    }

    @Override
    public void saveOrderHistory(OrderHistory orderHistory) {
        Map<String, Object> orderHistoryDetails = new HashMap<>();
        orderHistoryDetails.put("userOrdered", orderHistory.getUserOrdered());
        orderHistoryDetails.put("price", orderHistory.getPrice());
        simpleJdbcInsert.execute(orderHistoryDetails);
    }

    @Override
    public List<OrderHistory> getAllOrderHistory() {
        return jdbcTemplate.query(SELECT_ALL_ORDER_HISTORY, this::mapRowToOrderHistory);
    }
}
