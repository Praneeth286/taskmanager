package com.praneeth.taskmanager;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/categories")
    public List<Category> getCategories(){
        return categoryService.getAllCategories();
    }
    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category.getName());
    }
}
