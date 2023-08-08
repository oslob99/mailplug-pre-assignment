package kr.co.oslob.oslob.board.repository;

import kr.co.oslob.oslob.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {


}
