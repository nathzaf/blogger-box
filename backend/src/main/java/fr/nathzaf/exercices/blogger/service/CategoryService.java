package fr.nathzaf.exercices.blogger.service;

import fr.nathzaf.exercices.blogger.exceptions.CategoryNameAlreadyExistsException;
import fr.nathzaf.exercices.blogger.exceptions.CategoryNotFoundException;
import fr.nathzaf.exercices.blogger.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAll();

    Category getById(UUID id) throws CategoryNotFoundException;

    Category create(String name) throws CategoryNameAlreadyExistsException;

    Category updateName(UUID id, String name) throws CategoryNotFoundException, CategoryNameAlreadyExistsException;

    void deleteById(UUID id) throws CategoryNotFoundException;
}
