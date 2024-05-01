package com.pro.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pro.model.Category;

import com.pro.repository.CategoryRepository;

@Service
public class CategoryService {
	
	 @Autowired
	    private CategoryRepository categoryRepository;


	 
	 public Category createCategory(Category category) {
	        // Additional logic can be added here, such as validation
	        return categoryRepository.save(category);
	    }

	public List<Category> getAllCategories() {
		 return categoryRepository.findAll();
	}
	


	public void deleteCategory(Long categoryid) {
		 if (categoryRepository.existsById(categoryid)) {
	            categoryRepository.deleteById(categoryid);
	        } else {
	            throw new NoSuchElementException("Category with id " + categoryid + " not found");
	        }
	}

	
	public Category getCategoryById(Long categoryid) {
		return categoryRepository.findById(categoryid).orElse(null);
	}


	public Category updateCategory(Long categoryid, Category category) {
		  Optional<Category> optionalCategory = categoryRepository.findById(categoryid);
		  if (optionalCategory.isPresent()) {
			  Category existingCategory = optionalCategory.get();
	            existingCategory.setName(category.getName());
	            return categoryRepository.save(existingCategory);
	            
		  } else {

	            throw new NoSuchElementException("Category with id " + categoryid + " not found");
	        }
	}

	
	
	 // Method to find a category by its ID
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                                 .orElse(null); // Return null if category is not found
    }

    // Method to find a category by its name
    public Category findByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }
}
