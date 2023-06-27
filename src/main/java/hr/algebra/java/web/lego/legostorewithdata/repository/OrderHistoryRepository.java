package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.OrderHistory;
import org.springframework.core.annotation.Order;

import java.util.List;

public interface OrderHistoryRepository {
    List<OrderHistory> getOrderHistoryByUser(String username);
    void saveOrderHistory(OrderHistory orderHistory);
    List<OrderHistory> getAllOrderHistory();
}
