package fr.nathzaf.exercices.blogger.service;

import fr.nathzaf.exercices.blogger.exceptions.CategoryNotFoundException;
import fr.nathzaf.exercices.blogger.exceptions.PostNotFoundException;
import fr.nathzaf.exercices.blogger.models.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllByCategoryId(UUID categoryId) throws CategoryNotFoundException;

    List<Post> getAll();

    Post getById(UUID id) throws PostNotFoundException;

    Post create(String title, String content, UUID categoryId) throws CategoryNotFoundException;

    Post update(UUID id, String title, String content, UUID categoryId) throws CategoryNotFoundException, PostNotFoundException;

    void deleteById(UUID id) throws PostNotFoundException;
}
