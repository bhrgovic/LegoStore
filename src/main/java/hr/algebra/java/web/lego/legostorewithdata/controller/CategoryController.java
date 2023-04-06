package hr.algebra.java.web.lego.legostorewithdata.controller;

import hr.algebra.java.web.lego.legostorewithdata.domain.Category;
import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;
import hr.algebra.java.web.lego.legostorewithdata.publisher.CustomSpringEventPublisher;
import hr.algebra.java.web.lego.legostorewithdata.repository.LegoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping("/legoCategory")
@AllArgsConstructor
@SessionAttributes("categories")
public class CategoryController {

    private LegoRepository legoRepository;

    private CustomSpringEventPublisher customSpringEventPublisher;

    @GetMapping("/categoryAdmin.html")
    public String openCategory(Model model){
        model.addAttribute("categories",
                legoRepository.getAllLegoPiecesList());
        model.addAttribute("newCategory", new Lego());
        return "categoryAdmin.html";
    }





}
