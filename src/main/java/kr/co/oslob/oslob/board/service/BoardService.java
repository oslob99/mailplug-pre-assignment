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

    /**
     * @param pageDTO 게시판 페이징 처리 정보
     * @param typeList 게시판 타입을 중복 필터링 할 정보
     * @return 게시판 타입 중복 필터링 처리와 페이징 처리로 조회해 게시판 전체 목록을 반환한다
     */
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

    /**
     * @param boardId 상세보기할 게시판 번호
     * @return 상세보기에 필요한 게시판 정보를 반환한다
     */
    public BoardResponseDTO detail(Long boardId) {

        Board findByBoard = getFindByBoard(boardId);

        return BoardResponseDTO.builder()
                .boardId(boardId)
                .boardName(findByBoard.getBoardName())
                .boardType(findByBoard.getBoardType())
                .build();
    }

    /**
     * @param writeDTO 작성에 필요한 정보
     * @return 작성된 게시판을 반환한다
     */
    public BoardResponseDTO write(BoardRequestWriteDTO writeDTO) {
        return new BoardResponseDTO().toEntity(boardRepository.save(writeDTO.toEntity()));
    }

    /**
     * @param modifyDTO 수정에 필요한 정보
     * @return 수정된 게시판을 반환한다
     */
    public BoardResponseDTO modify(BoardRequestModifyDTO modifyDTO) {

        Board findByBoard = getFindByBoard(modifyDTO.getBoardId());

        findByBoard.setBoardName(modifyDTO.getBoardName());
        findByBoard.setBoardType(modifyDTO.getBoardType());

        return new BoardResponseDTO().toEntity(boardRepository.save(findByBoard));
    }

    /**
     * @param boardId 삭제할 게시판 번호
     */
    public void delete(Long boardId) {
        boardRepository.delete(getFindByBoard(boardId));
    }

    /**
     * @param boardId 조회할 게시판 번호
     * @return 예외처리를 포함해 게시판을 반환하다
     */
    private Board getFindByBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> {
                    throw new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD, boardId);
                }
        );
    }


}
