package fr.nathzaf.exercices.blogger.service.impl;

import fr.nathzaf.exercices.blogger.exceptions.CategoryNameAlreadyExistsException;
import fr.nathzaf.exercices.blogger.exceptions.CategoryNotFoundException;
import fr.nathzaf.exercices.blogger.models.Category;
import fr.nathzaf.exercices.blogger.repository.CategoryRepository;
import fr.nathzaf.exercices.blogger.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(UUID id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category create(String name) throws CategoryNameAlreadyExistsException {
        if (categoryRepository.findCategoriesByName(name).isEmpty()) {
            Category category = new Category(name);
            return categoryRepository.save(category);
        } else throw new CategoryNameAlreadyExistsException(name);
    }

    @Override
    public Category updateName(UUID id, String newName) throws CategoryNotFoundException, CategoryNameAlreadyExistsException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        if (categoryRepository.findCategoriesByName(newName).isEmpty()) {
            category.setName(newName);
            categoryRepository.save(category);
            return category;
        } else throw new CategoryNameAlreadyExistsException(newName);
    }

    @Override
    public void deleteById(UUID id) throws CategoryNotFoundException {
        getById(id);
        categoryRepository.deleteById(id);
    }
}
