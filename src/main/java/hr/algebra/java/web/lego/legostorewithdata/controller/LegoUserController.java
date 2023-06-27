package hr.algebra.java.web.lego.legostorewithdata.controller;


import hr.algebra.java.web.lego.legostorewithdata.domain.CartItem;
import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;
import hr.algebra.java.web.lego.legostorewithdata.publisher.CustomSpringEventPublisher;
import hr.algebra.java.web.lego.legostorewithdata.repository.CartItemRepository;
import hr.algebra.java.web.lego.legostorewithdata.repository.LegoRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/lego")
@AllArgsConstructor
@SessionAttributes("legoPieces")
public class LegoUserController {


    private LegoRepository legoRepository;
    private CartItemRepository cartItemRepository;

    private CustomSpringEventPublisher customSpringEventPublisher;


    @GetMapping("/homePageUser.html")
    public String openLegoStorePage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Home page user opened!");
        model.addAttribute("legoPieces",
                legoRepository.getAllLegoPiecesList());
        model.addAttribute("newLegoPiece", new Lego());
        return "homePageUser";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("legoId") int legoId, HttpSession session, Principal principal) {
        // Get the Lego piece from the repository
        Lego legoPiece = legoRepository.getLegoPiece(legoId);

        // Get the username of the logged-in user
        String username = principal.getName();

        // Create a new CartItem
        CartItem cartItem = new CartItem(username, legoPiece, 1);

        // Save the CartItem to the database
        cartItemRepository.saveCartItem(cartItem);

        // Redirect back to the Lego Store page
        return "redirect:/lego/homePageUser.html";
    }


}
