package fr.nathzaf.exercices.blogger.controllers;

import fr.nathzaf.exercices.blogger.exceptions.CategoryNameAlreadyExistsException;
import fr.nathzaf.exercices.blogger.exceptions.CategoryNotFoundException;
import fr.nathzaf.exercices.blogger.models.Category;
import fr.nathzaf.exercices.blogger.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(
        name = "Category API",
        description = "Manages all operations about categories : creation, get, update, delete."
)
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all categories")
    public ResponseEntity<List<Category>> retrieveAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a category", description = "Retrieve a category by ID")
    public ResponseEntity<Category> retrieveCategoryById(@PathVariable UUID id) throws CategoryNotFoundException {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates a new category to be used by posts")
    public ResponseEntity<Category> createCategory(@RequestBody String name) throws CategoryNameAlreadyExistsException {
        Category category = categoryService.create(name);
        return ResponseEntity
                .created(URI.create("v1/categories/" + category.getId()))
                .body(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category", description = "Updates the name of a category by ID")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @RequestBody String name) throws CategoryNameAlreadyExistsException, CategoryNotFoundException {
        return ResponseEntity.ok(categoryService.updateName(id, name));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Delete a category by ID")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) throws CategoryNotFoundException {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
