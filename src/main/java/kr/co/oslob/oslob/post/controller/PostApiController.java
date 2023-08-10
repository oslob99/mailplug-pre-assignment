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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        PostResponseDTO responseDTO = postService.detail(postId);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/write")
    public ResponseEntity<?> write(
            @Validated @RequestBody PostRequestWriteDTO writeDTO
    ){

        postService.write(writeDTO);

        return ResponseEntity.ok().body("");
    }

    @PatchMapping("/modify")
    public ResponseEntity<?> modify(
            @Validated @RequestBody PostRequestModifyDTO modifyDTO
    ){

        postService.modify(modifyDTO);

        return ResponseEntity.ok().body("");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestParam Long postId
    ){

        postService.delete(postId);

        return null;
    }
}
