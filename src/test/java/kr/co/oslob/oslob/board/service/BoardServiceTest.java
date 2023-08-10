package kr.co.oslob.oslob.board.service;

import kr.co.oslob.oslob.board.dto.request.BoardRequestModifyDTO;
import kr.co.oslob.oslob.board.dto.request.BoardRequestWriteDTO;
import kr.co.oslob.oslob.board.dto.response.BoardListResponseDTO;
import kr.co.oslob.oslob.page.PageDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
@Rollback
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("페이징 요청을 보내면 페이징 처리된 모든 게시판이 조회되어야한다")
    void getList() {

        //given
        PageDTO pageDTO = new PageDTO();
        List<String> typeList = new ArrayList<>();

        //when
        BoardListResponseDTO list = boardService.getList(pageDTO, typeList);

        //then
        System.out.println("list 조회 : "+list);

    }

    @Test
    @DisplayName("게시판 작성을 하면 테이블에 저장이 되어야만 한다")
    void write() {

        //given
        BoardRequestWriteDTO dto = new BoardRequestWriteDTO();
        dto.setBoardName("자유게시판");
        dto.setBoardType("일반");

        //when
        boardService.write(dto);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setLimit(50);
        List<String> typeList = new ArrayList<>();

        BoardListResponseDTO list = boardService.getList(pageDTO, typeList);

        //then
        System.out.println("list 조회 : "+list);

    }

    @Test
    @DisplayName("23번 게시판을 수정하면 수정이 되어야만 한다")
    void modify() {

        //given
        BoardRequestModifyDTO dto = new BoardRequestModifyDTO();
        dto.setBoardId(23L);
        dto.setBoardName("Q&N");
        dto.setBoardType("고급");

        //when
        boardService.modify(dto);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setLimit(50);
        List<String> typeList = new ArrayList<>();

        BoardListResponseDTO list = boardService.getList(pageDTO, typeList);

        //then
        System.out.println("list 조회 : "+list);

    }

    @Test
    @DisplayName("22번 게시판이 삭제되어야만 한다")
    void delete() {

        //given
        Long boardId = 22L;

        //when
        boardService.delete(boardId);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setLimit(50);
        List<String> typeList = new ArrayList<>();

        BoardListResponseDTO list = boardService.getList(pageDTO, typeList);

        //then
        System.out.println("list 조회 : "+list);
    }
}