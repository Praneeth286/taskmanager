package com.praneeth.taskmanager;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public Category createCategory(String name){
        Category category = new Category(name);
        return categoryRepository.save(category);
    }
}
