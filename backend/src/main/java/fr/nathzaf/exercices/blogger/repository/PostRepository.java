package fr.nathzaf.exercices.blogger.repository;

import fr.nathzaf.exercices.blogger.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findByCategoryId(UUID categoryId);
}
