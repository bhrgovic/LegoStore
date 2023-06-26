package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryRepositoryJdbc implements CategoryRepository {
    private static final String CATEGORY_TABLE_NAME = "categories";
    private static final String CATEGORY_TABLE_COLUMN_ID = "idCategory";
    private static final String CATEGORY_TABLE_COLUMN_NAME = "name";

    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE " + CATEGORY_TABLE_COLUMN_ID + " = ?";
    private static final String INSERT_CATEGORY = "INSERT INTO categories (" + CATEGORY_TABLE_COLUMN_NAME + ") VALUES (?)";
    private static final String UPDATE_CATEGORY = "UPDATE categories SET " + CATEGORY_TABLE_COLUMN_NAME + " = ? WHERE " + CATEGORY_TABLE_COLUMN_ID + " = ?";
    private static final String DELETE_CATEGORY = "DELETE FROM categories WHERE " + CATEGORY_TABLE_COLUMN_ID + " = ?";

    private JdbcTemplate jdbcTemplate;

    public CategoryRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Category mapRowToCategory(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt(CATEGORY_TABLE_COLUMN_ID));
        category.setName(rs.getString(CATEGORY_TABLE_COLUMN_NAME));
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        return jdbcTemplate.query(SELECT_ALL_CATEGORIES, this::mapRowToCategory);
    }

    @Override
    public Category getCategoryById(int id) {
        return jdbcTemplate.queryForObject(SELECT_CATEGORY_BY_ID, this::mapRowToCategory, id);
    }

    @Override
    public void addCategory(Category category) {
        jdbcTemplate.update(INSERT_CATEGORY, category.getName());
    }

    @Override
    public void updateCategory(Category category) {
        jdbcTemplate.update(UPDATE_CATEGORY, category.getName(), category.getId());
    }

    @Override
    public void deleteCategory(int id) {
        jdbcTemplate.update(DELETE_CATEGORY, id);
    }
}
