package com.bee.repository;
import com.bee.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findCommentById(Long id);
}
