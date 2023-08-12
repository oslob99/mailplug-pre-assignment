package kr.co.oslob.oslob.board.controller;

import kr.co.oslob.oslob.board.dto.request.BoardRequestModifyDTO;
import kr.co.oslob.oslob.board.dto.request.BoardRequestWriteDTO;
import kr.co.oslob.oslob.board.dto.response.BoardListResponseDTO;
import kr.co.oslob.oslob.board.dto.response.BoardResponseDTO;
import kr.co.oslob.oslob.board.service.BoardService;
import kr.co.oslob.oslob.page.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static kr.co.oslob.oslob.common.exception.ErrorCode.INVALID_PARAMETER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oslob/board")
@Slf4j
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> list(
            PageDTO pageDTO
            , @RequestParam(required = false) String[] types
            ){

        log.info("/api/oslob/board/list?offset={}&limit={}&types={}"
                ,pageDTO.getOffset(),pageDTO.getLimit(),types);

        List<String> typeList = types != null ? Arrays.asList(types) : Collections.emptyList();

        BoardListResponseDTO requestDTO = boardService.getList(pageDTO,typeList);

        return ResponseEntity.ok().body(requestDTO);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detail(
            @RequestParam Long boardId
    ){

        try {
            BoardResponseDTO responseDTO = boardService.detail(boardId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 게시판은 존재하지 않습니다.");
        }

    }

    @PostMapping("/write")
    public ResponseEntity<?> write(
           @Validated @RequestBody BoardRequestWriteDTO writeDTO
           , BindingResult bindingResult
    ){

        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(INVALID_PARAMETER);
        }

        boardService.write(writeDTO);

        return ResponseEntity.ok().body("");
    }

    @PatchMapping("/modify")
    public ResponseEntity<?> modify(
            @Validated @RequestBody BoardRequestModifyDTO modifyDTO
    ){

        boardService.modify(modifyDTO);

        return ResponseEntity.ok().body("");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestParam Long boardId
    ){

        boardService.delete(boardId);

        return null;
    }


}
