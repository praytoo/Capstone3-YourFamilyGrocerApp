package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;

// http://localhost:8080/categories
@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController {
    private CategoryService categoryService;
    private ProductService productService;

    //autowired constructor
    @Autowired
    public CategoriesController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    //http://localhost:8080/categories?name=dairy%20eggs
    //method for get all categories
    @GetMapping
    public ResponseEntity<List<Category>> getAll(@RequestParam(required = false) String name) {
        // find and return all categories
        List<Category> category = categoryService.getAllCategories(name);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    //method to get category by id
    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getById(@PathVariable int id) {
        // get the category by id
        Category category = categoryService.getById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    // https://localhost:8080/categories/1/products
    //method to get products by category id
    @GetMapping("{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable int categoryId) {
        List<Product> product = productService.listByCategoryId(categoryId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    //method to add a category
    //only admin can use this function
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category category1 = categoryService.create(category);
        if (category1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category1);
    }

    //update category by id
    //only admin has this function
    @PutMapping("{categoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable int categoryId, @RequestBody Category category) {
        categoryService.update(categoryId, category);
    }

    //delete category by categoryId
    //only admin has this function
    @DeleteMapping("{categoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable int categoryId) {
        categoryService.delete(categoryId);
    }
}
