package hr.algebra.java.web.lego.legostorewithdata.controller;

import hr.algebra.java.web.lego.legostorewithdata.domain.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/legoCateogry")
public class CategoryController {

    @GetMapping("/categoryAdmin.html")
    public String showCategories(Model model) {
        // add categories to model for display in HTML
        model.addAttribute("categories", Category.values());
        return "categoryAdmin"; // name of HTML file
    }

    @PostMapping("/add-enum-value")
    public String addCategory(@RequestParam String valueName) {
        // add logic to add new category with valueName
        return "redirect:/legoCategory/categoryAdmin.html"; // redirect to categories page
    }

    @PostMapping("/remove-enum-value")
    public String removeCategory(@RequestParam String valueName) {
        // add logic to remove category with valueName
        return "redirect:/legoCategory/categoryAdmin.html"; // redirect to categories page
    }

}