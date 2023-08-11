package kr.co.oslob.oslob.reply.repository;

import kr.co.oslob.oslob.reply.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("SELECT p FROM Post p " +
            "WHERE (:keyword IS NULL OR LOWER(p.replyContent) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.replyWriter) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    Page<Reply> findByKeyword(String keyword, Pageable pageable);
}
