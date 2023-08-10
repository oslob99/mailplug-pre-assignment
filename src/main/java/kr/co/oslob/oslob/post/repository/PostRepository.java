package kr.co.oslob.oslob.post.repository;

import kr.co.oslob.oslob.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p " +
            "WHERE (:keyword IS NULL OR LOWER(p.postTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.postContent) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.postWriter) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Post> findByKeyword(String keyword, Pageable pageable);
}
