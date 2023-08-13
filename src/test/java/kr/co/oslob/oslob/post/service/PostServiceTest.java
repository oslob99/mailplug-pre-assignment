package kr.co.oslob.oslob.post.service;

import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.post.dto.request.PostRequestModifyDTO;
import kr.co.oslob.oslob.post.dto.request.PostRequestWriteDTO;
import kr.co.oslob.oslob.post.dto.response.PostListResponseDTO;
import kr.co.oslob.oslob.post.dto.response.PostResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
//@Rollback
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("2번 게시판이면서 게시글 페이징 요청을 보내면 페이징 처리와 키워드 검색된 모든 게시글이 조회되어야한다")
    void getList() {

        //given
        Long boardId = 2L;
        PageDTO pageDTO = new PageDTO();
        String keyword = "";

        //when
        PostListResponseDTO list = postService.getList(boardId, pageDTO, keyword);

        //then
        System.out.println("list 조회 : " + list);
    }

    @Test
    @DisplayName("1번 게시글이 상세조회가 되어야만한다")
    void detail() {
        //given
        Long postId = 1L;

        //when
        PostResponseDTO responseDTO = postService.detail(postId);

        //then
        System.out.println("responseDTO : {}" + responseDTO);
    }

    @Test
    @DisplayName("게시글 작성을 하면 테이블에 저장이 되어야만 한다")
    void write() {
        //given
        Long boardId = 2L;
        PostRequestWriteDTO writeDTO = new PostRequestWriteDTO();
        writeDTO.setPostTitle("하하");
        writeDTO.setPostWriter("oslob");
        writeDTO.setPostContent("안녕하세요!");

        //when
        PostResponseDTO responseDTO = postService.write(boardId, writeDTO);

        // then
        System.out.println("responseDTO : " + responseDTO);
    }

    @Test
    @DisplayName("1번 게시글을 수정하면 수정이 되어야만 한다")
    void modify() {
        //given
        Long postId = 1L;
        PostRequestModifyDTO modifyDTO = new PostRequestModifyDTO();
        modifyDTO.setPostId(postId);
        modifyDTO.setPostContent("반갑습니다");
        modifyDTO.setPostTitle("메일플러그");

        //when
        PostResponseDTO modify = postService.modify(modifyDTO);

        //then
        System.out.println("modify : " + modify);
    }

    @Test
    @DisplayName("2번 게시글이 삭제되어야만 한다")
    void delete() {
        //given
        Long postId = 2L;

        //when
        postService.delete(postId);

    }
}