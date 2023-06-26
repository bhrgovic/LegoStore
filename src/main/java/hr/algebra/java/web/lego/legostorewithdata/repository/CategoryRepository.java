package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(int id);
}
