package kr.co.oslob.oslob.board.controller;

import kr.co.oslob.oslob.board.dto.response.BoardListResponseDTO;
import kr.co.oslob.oslob.board.service.BoardService;
import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.page.SearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oslob/board")
@Slf4j
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> list(
            PageDTO pageDTO
            ){

        log.info("/api/oslob/board/list?offset={}&limit={}"
                ,pageDTO.getOffset(),pageDTO.getLimit());


        BoardListResponseDTO requestDTO = boardService.getList(pageDTO);

        return ResponseEntity.ok().body(requestDTO);
    }


}
