package hr.algebra.java.web.lego.legostorewithdata.controller;

import hr.algebra.java.web.lego.legostorewithdata.domain.OrderHistory;
import hr.algebra.java.web.lego.legostorewithdata.repository.OrderHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/order-history")
@AllArgsConstructor
@SessionAttributes("orderHistory")
public class OrderHistoryController {

    private final OrderHistoryRepository orderHistoryRepository;

    @GetMapping("/all")
    public String viewAllOrderHistory(Model model) {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.getAllOrderHistory();
        model.addAttribute("orderHistory", orderHistoryList);
        return "orderHistoryList";
    }
}
