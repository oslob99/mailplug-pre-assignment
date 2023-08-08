package kr.co.oslob.oslob.board.service;

import kr.co.oslob.oslob.board.dto.response.BoardListResponseDTO;
import kr.co.oslob.oslob.board.dto.response.BoardResponseDTO;
import kr.co.oslob.oslob.board.entity.Board;
import kr.co.oslob.oslob.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardListResponseDTO getList() {

        List<Board> all = boardRepository.findAll();
        log.info("list : {}", all);

        List<BoardResponseDTO> list = boardRepository.findAll().stream().map(
                board -> new BoardResponseDTO().toEntity(board)
        ).collect(Collectors.toList());

        return BoardListResponseDTO.builder()
                .value(list)
                .total(list.size())
                .build();
    }
}
