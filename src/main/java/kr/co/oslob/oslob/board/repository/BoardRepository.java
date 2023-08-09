package kr.co.oslob.oslob.board.repository;

import kr.co.oslob.oslob.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByBoardTypeInIgnoreCase(List<String> typeList, Pageable pageable);
}
