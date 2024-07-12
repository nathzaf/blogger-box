package fr.nathzaf.exercices.blogger.service.impl;

import fr.nathzaf.exercices.blogger.exceptions.CategoryNotFoundException;
import fr.nathzaf.exercices.blogger.exceptions.PostNotFoundException;
import fr.nathzaf.exercices.blogger.models.Category;
import fr.nathzaf.exercices.blogger.models.Post;
import fr.nathzaf.exercices.blogger.repository.PostRepository;
import fr.nathzaf.exercices.blogger.service.CategoryService;
import fr.nathzaf.exercices.blogger.service.PostService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final CategoryService categoryService;

    public PostServiceImpl(PostRepository postRepository, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Post> getAllByCategoryId(UUID categoryId) throws CategoryNotFoundException {
        categoryService.getById(categoryId);
        return postRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate"));
    }

    @Override
    public Post getById(UUID id) throws PostNotFoundException {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    public Post create(String title, String content, UUID categoryId) throws CategoryNotFoundException {
        Category category = categoryService.getById(categoryId);
        Post post = new Post(category, title, content);
        return postRepository.save(post);
    }

    @Override
    public Post update(UUID id, String title, String content, UUID categoryId) throws CategoryNotFoundException, PostNotFoundException {
        Post post = getById(id);
        Category category = categoryService.getById(categoryId);
        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);
        return postRepository.save(post);
    }

    @Override
    public void deleteById(UUID id) throws PostNotFoundException {
        getById(id);
        postRepository.deleteById(id);

    }
}
