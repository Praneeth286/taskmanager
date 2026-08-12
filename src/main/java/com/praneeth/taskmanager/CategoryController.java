package com.praneeth.taskmanager;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category.getName());
    }
}
