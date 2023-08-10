package kr.co.oslob.oslob.board.service;

import kr.co.oslob.oslob.board.dto.request.BoardRequestModifyDTO;
import kr.co.oslob.oslob.board.dto.request.BoardRequestWriteDTO;
import kr.co.oslob.oslob.board.dto.response.BoardListResponseDTO;
import kr.co.oslob.oslob.board.dto.response.BoardResponseDTO;
import kr.co.oslob.oslob.board.entity.Board;
import kr.co.oslob.oslob.board.repository.BoardRepository;
import kr.co.oslob.oslob.common.exception.ErrorCode;
import kr.co.oslob.oslob.common.exception.NotFoundBoardException;
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

    public BoardResponseDTO detail(Long boardId) {

        Board findByBoard = getFindByBoard(boardId);

        return BoardResponseDTO.builder()
                .boardId(boardId)
                .boardName(findByBoard.getBoardName())
                .boardType(findByBoard.getBoardType())
                .build();
    }

    public void write(BoardRequestWriteDTO writeDTO) {

        Board saved = boardRepository.save(writeDTO.toEntity());


    }

    public void modify(BoardRequestModifyDTO modifyDTO) {

        Board findByBoard = getFindByBoard(modifyDTO.getBoardId());

        findByBoard.setBoardName(modifyDTO.getBoardName());
            findByBoard.setBoardType(modifyDTO.getBoardType());

            boardRepository.save(findByBoard);
    }

    public void delete(Long boardId) {

        Board findByBoard = getFindByBoard(boardId);

        boardRepository.delete(findByBoard);

    }

    /**
     * @param boardId
     * @return boardId의 게시글 찾아 Board를 반환하는 메서드
     */
    private Board getFindByBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> {
                    throw new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD, boardId);
                }
        );
    }


}
