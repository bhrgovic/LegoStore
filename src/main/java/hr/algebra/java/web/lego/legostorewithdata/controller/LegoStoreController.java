package hr.algebra.java.web.lego.legostorewithdata.controller;

import hr.algebra.java.web.lego.legostorewithdata.domain.Category;
import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;
import hr.algebra.java.web.lego.legostorewithdata.domain.LegoRecord;
import hr.algebra.java.web.lego.legostorewithdata.publisher.CustomSpringEventPublisher;
import hr.algebra.java.web.lego.legostorewithdata.repository.CategoryRepository;
import hr.algebra.java.web.lego.legostorewithdata.repository.LegoRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.math.BigDecimal;

@Controller
@RequestMapping("/legoStore")
@AllArgsConstructor
@SessionAttributes("legoPieces")
public class LegoStoreController {
    private LegoRepository legoRepository;
    private CategoryRepository categoryRepository;

    private CustomSpringEventPublisher customSpringEventPublisher;

    @GetMapping("/homePage.html")
    public String openLegoStorePage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Home page opened!");
        model.addAttribute("legoPieces",
                legoRepository.getAllLegoPiecesList());
        model.addAttribute("categories", categoryRepository.getAllCategories());

        model.addAttribute("newLegoPiece", new Lego());
        return "homePage";
    }

    @PostMapping("/saveNewLegoPiece.html")
    public String saveNewLegoPiece(@ModelAttribute Lego newLegoPiece, Model model) {
        //System.out.println("New lego piece: " + newLegoPiece.getName());
        model.addAttribute("newLegoPiece", newLegoPiece);
        legoRepository.saveNewLegoPiece(newLegoPiece);
        /*model.addAttribute("legoPieces",
                legoRepository.getAllLegoPiecesList());*/
        return "redirect:/legoStore/homePage.html";
    }

    @GetMapping("/updateLego/{id}")
    public String showUpdateForm(@ModelAttribute Lego lego,Model model) {
        model.addAttribute("legoPiece",
                legoRepository.getLegoPiece(lego.getId()));
        return "updateLegoForm.html";
    }

    @PostMapping("/updateLego/{id}")
    public String updateLegoPiece(@ModelAttribute Lego lego){

        legoRepository.updateLego(lego);
        return "redirect:/legoStore/homePage.html";
    }

    @GetMapping("/deleteLego/{id}")
    public String deleteLegoPiece(@ModelAttribute Lego lego){
        customSpringEventPublisher.publishCustomEvent("Lego Piece Deleted");
        legoRepository.deleteLego(lego);
        return "redirect:/legoStore/homePage.html";
    }

    @GetMapping("/cleanSession.html")
    public String cleanSession(SessionStatus sessionStatus, HttpSession session) {
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/legoStore/homePage.html";
    }


}
