package hr.algebra.java.web.lego.legostorewithdata.controller;


import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;
import hr.algebra.java.web.lego.legostorewithdata.publisher.CustomSpringEventPublisher;
import hr.algebra.java.web.lego.legostorewithdata.repository.LegoRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lego")
@AllArgsConstructor
@SessionAttributes("legoPieces")
public class LegoUserController {


    private LegoRepository legoRepository;

    private CustomSpringEventPublisher customSpringEventPublisher;

    @GetMapping("/homePageUser.html")
    public String openLegoStorePage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Home page user opened!");
        model.addAttribute("legoPieces",
                legoRepository.getAllLegoPiecesList());
        model.addAttribute("newLegoPiece", new Lego());
        return "homePageUser";
    }


    @GetMapping("/saveToCart/{id}")
    public String saveToCart(@ModelAttribute Lego id, HttpSession session){

        return "redirect:/lego/homePageUser.html";
    }






}
