package hr.algebra.java.web.lego.legostorewithdata.controller;

import hr.algebra.java.web.lego.legostorewithdata.domain.Category;
import hr.algebra.java.web.lego.legostorewithdata.domain.Lego;
import hr.algebra.java.web.lego.legostorewithdata.publisher.CustomSpringEventPublisher;
import hr.algebra.java.web.lego.legostorewithdata.repository.CategoryRepository;
import hr.algebra.java.web.lego.legostorewithdata.repository.LegoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/legoCategory")
@AllArgsConstructor
@SessionAttributes("categories")
public class CategoryController {
    private CategoryRepository categoryRepository;



    @GetMapping("/categoryAdmin.html")
    public String viewCategories(Model model) {
        List<Category> categories = categoryRepository.getAllCategories();
        model.addAttribute("categories", categories);
        return "categoryAdmin.html";
    }

    @GetMapping("/addCategory.html")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "addCategory.html";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category) {
        categoryRepository.addCategory(category);
        return "redirect:/legoCategory/categoryAdmin.html";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable int id, Model model) {
        Category category = categoryRepository.getCategoryById(id);
        model.addAttribute("category", category);
        return "editCategory.html";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable int id, @ModelAttribute Category category) {
        category.setId(id);
        categoryRepository.updateCategory(category);
        return "redirect:/categoryAdmin.html";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryRepository.deleteCategory(id);
        return "redirect:/legoCategory/categoryAdmin.html";
    }
}
