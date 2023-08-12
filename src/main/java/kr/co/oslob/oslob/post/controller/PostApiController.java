package kr.co.oslob.oslob.post.controller;

import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.post.dto.request.PostRequestModifyDTO;
import kr.co.oslob.oslob.post.dto.request.PostRequestWriteDTO;
import kr.co.oslob.oslob.post.dto.response.PostListResponseDTO;
import kr.co.oslob.oslob.post.dto.response.PostResponseDTO;
import kr.co.oslob.oslob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static kr.co.oslob.oslob.common.exception.ErrorCode.INVALID_PARAMETER;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/oslob/post")
public class PostApiController {

    private final PostService postService;

    @GetMapping("/list")
    public ResponseEntity<?> list(
            PageDTO pageDTO
            , @RequestParam(required = false)String keyword
            ){

        log.info("/api/oslob/board/list?offset={}&limit={}&keyword={}"
                ,pageDTO.getOffset(),pageDTO.getLimit(),keyword);

        PostListResponseDTO responseDTO = postService.getList(pageDTO, keyword);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detail(
            @RequestParam Long postId
    ){

        try {
            PostResponseDTO responseDTO = postService.detail(postId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 게시글은 존재하지 않습니다.");
        }

    }

    @PostMapping("/write")
    public ResponseEntity<?> write(
            @Validated @RequestBody PostRequestWriteDTO writeDTO
            , BindingResult bindingResult
    ){

        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(INVALID_PARAMETER);
        }

        PostResponseDTO responseDTO = postService.write(writeDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PatchMapping("/modify")
    public ResponseEntity<?> modify(
            @Validated @RequestBody PostRequestModifyDTO modifyDTO
            , BindingResult bindingResult
    ){

        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(INVALID_PARAMETER);
        }
        try {
            PostResponseDTO responseDTO = postService.modify(modifyDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 게시글은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestParam Long postId
    ){
        try {
            postService.delete(postId);
            return ResponseEntity.ok().body("삭제 성공했습니다.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 게시글은 존재하지 않습니다.");
        }

    }
}
