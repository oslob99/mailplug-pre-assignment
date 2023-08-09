package kr.co.oslob.oslob.board.service;

import kr.co.oslob.oslob.board.dto.response.BoardListResponseDTO;
import kr.co.oslob.oslob.board.dto.response.BoardResponseDTO;
import kr.co.oslob.oslob.board.entity.Board;
import kr.co.oslob.oslob.board.repository.BoardRepository;
import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.page.SearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardListResponseDTO getList(PageDTO pageDTO, SearchDTO searchDTO) {

        List<String> typeList = searchDTO.getTypes() != null ? Arrays.asList(searchDTO.getTypes()) : Collections.emptyList();

        Pageable pageable = PageRequest.of(
                pageDTO.getOffset() - 1,
                pageDTO.getLimit(),
                Sort.by(Sort.Direction.DESC,"boardDate")
        );

        Page<Board> boards;
        if (typeList.isEmpty()){
            boards = boardRepository.findAll(pageable);
        }else {
            boards = boardRepository.findByBoardTitleContainingIgnoreCaseOrBoardContentContainingIgnoreCaseOrBoardWriterContainingIgnoreCase(typeList,pageable);
        }

        List<BoardResponseDTO> list = boardRepository.findAll().stream().map(
                board -> new BoardResponseDTO().toEntity(board)
        ).collect(Collectors.toList());

        return BoardListResponseDTO.builder()
                .value(list)
                .total(list.size())
                .build();
    }
}
