package kr.co.oslob.oslob.reply.repository;

import kr.co.oslob.oslob.reply.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r " +
            "WHERE (:keyword IS NULL OR LOWER(r.replyContent) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(r.replyWriter) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            "AND r.post.postId = :postId")
    Page<Reply> findByKeyword(Long postId, String keyword, Pageable pageable);

    Page<Reply> findByPostPostId(Long postId, Pageable pageable);
}
