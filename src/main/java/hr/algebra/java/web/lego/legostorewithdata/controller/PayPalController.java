package hr.algebra.java.web.lego.legostorewithdata.controller;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/paypal")
public class PayPalController {

    @Autowired
    private PayPalHttpClient payPalHttpClient;

    @Value("${paypal.success.url}")
    private String successUrl;

    @Value("${paypal.cancel.url}")
    private String cancelUrl;

    @GetMapping("/checkout-pay.html")
    public String displayCheckout(Model model) {
        // Cart total is already added to the model in your method logic
        return "checkout-pay"; // Return the checkout Thymeleaf template
    }

    @PostMapping("/create-order")
    public String createOrder(Model model) throws IOException {
        // Retrieve payment details from the form
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String itemName = request.getParameter("item_name");
        String amount = request.getParameter("amount");

        // Create an order request
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>();
        purchaseUnits.add(new PurchaseUnitRequest().amountWithBreakdown(
                new AmountWithBreakdown().currencyCode("USD").value(amount)));
        orderRequest.purchaseUnits(purchaseUnits);

        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);

        try {
            // Create the order
            HttpResponse<Order> response = payPalHttpClient.execute(ordersCreateRequest);
            Order order = response.result();

            for (LinkDescription link : order.links()) {
                if ("approve".equals(link.rel())) {
                    // Redirect the user to PayPal for payment approval
                    return "redirect:" + link.href();
                }
            }
        } catch (IOException e) {
            // Handle errors
            model.addAttribute("error", "Failed to create order");
            return "error"; // Render an error page
        }

        return "redirect:/";
    }


    @GetMapping("/success")
    public String success() {
        // Handle a successful payment
        return "success.html"; // Render a success page
    }

    @GetMapping("/cancel")
    public String cancel() {
        // Handle a canceled payment
        return "cancel.html"; // Render a cancel page
    }
}