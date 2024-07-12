package fr.nathzaf.exercices.blogger.controllers;

import fr.nathzaf.exercices.blogger.dto.CreationPostRequest;
import fr.nathzaf.exercices.blogger.dto.UpdatePostRequest;
import fr.nathzaf.exercices.blogger.exceptions.CategoryNotFoundException;
import fr.nathzaf.exercices.blogger.exceptions.PostNotFoundException;
import fr.nathzaf.exercices.blogger.models.Post;
import fr.nathzaf.exercices.blogger.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(
        name = "Post API",
        description = "Manages all operations about posts : creation, get, update, delete."
)
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @Operation(summary = "Create a new post", description = "Creates a new post in the blog")
    public ResponseEntity<Post> createPost(@RequestBody CreationPostRequest creationPostRequest) throws CategoryNotFoundException {
        Post post = postService.create(creationPostRequest.getTitle(), creationPostRequest.getContent(), creationPostRequest.getCategoryId());
        return ResponseEntity
                .created(URI.create("v1/posts/" + post.getId()))
                .body(post);
    }

    @PutMapping
    @Operation(summary = "Update an existing post", description = "Updates an existing post by ID")
    public ResponseEntity<Post> updatePost(@RequestBody UpdatePostRequest post) throws PostNotFoundException, CategoryNotFoundException {
        return ResponseEntity.ok(postService.update(post.getId(), post.getTitle(), post.getContent(), post.getCategoryId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing post", description = "Deletes an existing post by ID")
    public ResponseEntity<Void> deletePost(@Parameter(description = "ID of the post to be deleted") @PathVariable UUID id) throws PostNotFoundException {
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Retrieve all posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a post", description = "Retrieve a post by ID")
    public ResponseEntity<Post> getPostById(@Parameter(description = "ID of the post to be get") @PathVariable UUID id) throws PostNotFoundException {
        return ResponseEntity.ok(postService.getById(id));
    }

    @GetMapping("/by-category")
    @Operation(summary = "Retrieve posts by category", description = "Retrieves all posts filtered by a category ID")
    public ResponseEntity<List<Post>> getPostsByCategory(@Parameter(description = "Category to filter posts") @RequestParam UUID categoryId) throws CategoryNotFoundException {
        return ResponseEntity.ok(postService.getAllByCategoryId(categoryId));
    }
}