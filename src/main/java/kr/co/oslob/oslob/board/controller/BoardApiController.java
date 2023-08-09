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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oslob/board")
@Slf4j
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> list(
            PageDTO pageDTO
            , @RequestParam(required = false)SearchDTO searchDTO
            ){

        log.info("/api/oslob/board/list?offset={}&limit={}&type={}&keywords={}"
                ,pageDTO.getOffset(),pageDTO.getLimit(),searchDTO.getTypes(),searchDTO.getKeyword());


        BoardListResponseDTO requestDTO = boardService.getList(pageDTO, searchDTO);

        return ResponseEntity.ok().body(requestDTO);
    }


}
