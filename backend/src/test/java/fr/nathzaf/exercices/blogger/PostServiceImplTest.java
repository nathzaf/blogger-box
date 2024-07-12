package fr.nathzaf.exercices.blogger;

import fr.nathzaf.exercices.blogger.exceptions.CategoryNotFoundException;
import fr.nathzaf.exercices.blogger.exceptions.PostNotFoundException;
import fr.nathzaf.exercices.blogger.models.Category;
import fr.nathzaf.exercices.blogger.models.Post;
import fr.nathzaf.exercices.blogger.repository.PostRepository;
import fr.nathzaf.exercices.blogger.service.CategoryService;
import fr.nathzaf.exercices.blogger.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CategoryService categoryService;

    @Test
    void shouldReturnAllPostsWhenGetAll() {
        Category category1 = new Category("Category 1");
        Post post1 = new Post(category1, "Title 1", "Content 1");
        Post post2 = new Post(category1, "Title 2", "Content 2");
        Post post3 = new Post(new Category("Category 2"), "Title 3", "Content 3");

        List<Post> expectedPosts = List.of(post1, post2, post3);
        when(postRepository.findAll((Sort) any())).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getAll();

        assertEquals(expectedPosts, actualPosts);
        verify(postRepository).findAll((Sort) any());
    }

    @Test
    void shouldReturnPostsByCategoriesWhenGetAllByCategoryId() throws CategoryNotFoundException {
        UUID categoryId = UUID.randomUUID();
        Category category1 = new Category("Category 1");
        Post post1 = new Post(category1, "Title 1", "Content 1");
        Post post2 = new Post(category1, "Title 2", "Content 2");

        List<Post> expectedPosts = List.of(post1, post2);

        when(categoryService.getById(categoryId)).thenReturn(category1);
        when(postRepository.findByCategoryId(categoryId)).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getAllByCategoryId(categoryId);

        assertEquals(expectedPosts, actualPosts);
        verify(categoryService).getById(categoryId);
        verify(postRepository).findByCategoryId(categoryId);

    }

    @Test
    void shouldReturnPostWhenGetById() throws PostNotFoundException {
        UUID id = UUID.randomUUID();
        Post expectedPost = new Post(new Category("Category"), "Title", "Content");

        when(postRepository.findById(id)).thenReturn(Optional.of(expectedPost));

        Post actualPost = postService.getById(id);

        assertEquals(expectedPost, actualPost);
        verify(postRepository).findById(id);
    }

    @Test
    void shouldThrowPostNotFoundExceptionWhenNotFoundById() {
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        PostNotFoundException e = assertThrows(PostNotFoundException.class,
                () -> postService.getById(id));

        assertEquals("No Post of id " + id + " found.", e.getMessage());
        verify(postRepository).findById(id);
    }

    @Test
    void shouldReturnPostWhenCreate() throws CategoryNotFoundException {
        UUID categoryId = UUID.randomUUID();
        Category category = new Category("Category");
        Post expectedPost = new Post(category, "Title", "Content");

        when(categoryService.getById(categoryId)).thenReturn(category);
        when(postRepository.save(any())).thenReturn(expectedPost);

        Post actualPost = postService.create("Title", "Content", categoryId);

        assertEquals(expectedPost, actualPost);
        verify(categoryService).getById(categoryId);
        verify(postRepository).save(any());
    }

    @Test
    void shouldReturnPostWhenUpdate() throws CategoryNotFoundException, PostNotFoundException {
        UUID categoryId = UUID.randomUUID();
        Category category = new Category("Category");
        UUID id = UUID.randomUUID();
        Post oldPost = new Post(new Category("Old Category"), "Old title", "Old content");
        Post expectedPost = new Post(category, "Title", "Content");

        when(postRepository.findById(id)).thenReturn(Optional.of(oldPost));
        when(categoryService.getById(categoryId)).thenReturn(category);
        when(postRepository.save(any())).thenReturn(expectedPost);

        Post actualPost = postService.update(id, "Title", "Content", categoryId);

        assertEquals(expectedPost, actualPost);
        verify(postRepository).findById(id);
        verify(categoryService).getById(categoryId);
        verify(postRepository).save(any());
    }

    @Test
    void shouldThrowPostNotFoundExceptionWhenNotFoundByIdOnUpdate() throws CategoryNotFoundException {
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        PostNotFoundException e = assertThrows(PostNotFoundException.class,
                () -> postService.update(id, "Title", "Content", UUID.randomUUID()));

        assertEquals("No Post of id " + id + " found.", e.getMessage());
        verify(postRepository).findById(id);
        verify(categoryService, never()).getById(any());
        verify(postRepository, never()).save(any());
    }

    @Test
    void shouldDeleteWhenDelete() throws PostNotFoundException {
        UUID id = UUID.randomUUID();

        when(postRepository.findById(id)).thenReturn(Optional.of(new Post(new Category("Category"), "Title", "Content")));

        postService.deleteById(id);

        verify(postRepository).deleteById(id);
    }

    @Test
    void shouldThrowPostNotFoundExceptionWhenNotFoundByIdOnDelete() {
        UUID id = UUID.randomUUID();

        when(postRepository.findById(id)).thenReturn(Optional.empty());

        PostNotFoundException e = assertThrows(PostNotFoundException.class,
                () -> postService.getById(id));

        assertEquals("No Post of id " + id + " found.", e.getMessage());

        verify(postRepository, never()).deleteById(any());
    }
}
