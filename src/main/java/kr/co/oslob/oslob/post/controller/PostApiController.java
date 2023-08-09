package kr.co.oslob.oslob.post.controller;

import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.page.SearchDTO;
import kr.co.oslob.oslob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/oslob/post")
public class PostApiController {

    private final PostService postService;

    @GetMapping("/list")
    public ResponseEntity<?> list(
            PageDTO pageDTO
            , @RequestParam(required = false)SearchDTO searchDTO
            ){

        log.info("/api/oslob/board/list?offset={}&limit={}&type={}&keywords={}"
                ,pageDTO.getOffset(),pageDTO.getLimit(),searchDTO.getTypes(),searchDTO.getKeyword());

        postService.getList(pageDTO,searchDTO);

        return null;
    }
}
