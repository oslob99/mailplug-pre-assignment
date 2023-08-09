package kr.co.oslob.oslob.board.service;

import kr.co.oslob.oslob.board.dto.response.BoardListResponseDTO;
import kr.co.oslob.oslob.board.dto.response.BoardResponseDTO;
import kr.co.oslob.oslob.board.entity.Board;
import kr.co.oslob.oslob.board.repository.BoardRepository;
import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.page.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardListResponseDTO getList(PageDTO pageDTO, List<String> typeList) {

        Pageable pageable = PageRequest.of(
                pageDTO.getOffset() - 1,
                pageDTO.getLimit(),
                Sort.by(Sort.Direction.DESC,"boardType")
        );

        Page<Board> boards;
        if (typeList.isEmpty()){
            boards = boardRepository.findAll(pageable);
        }else {
            boards = boardRepository.findByBoardTypeInIgnoreCase(typeList, pageable);
        }

        log.info("board : {}",boards);

        List<BoardResponseDTO> list = boards.stream().map(
                board -> new BoardResponseDTO().toEntity(board)
        ).collect(Collectors.toList());

        return BoardListResponseDTO.builder()
                .value(list)
                .pageInfo(new PageResponseDTO<Board>(boards))
                .count(list.size())
                .build();
    }
}
