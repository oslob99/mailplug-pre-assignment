package kr.co.oslob.oslob.reply.service;

import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.post.dto.request.PostRequestModifyDTO;
import kr.co.oslob.oslob.reply.dto.request.ReplyRequestModifyDTO;
import kr.co.oslob.oslob.reply.dto.request.ReplyRequestWriteDTO;
import kr.co.oslob.oslob.reply.dto.response.ReplyListResponseDTO;
import kr.co.oslob.oslob.reply.dto.response.ReplyResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
//@Rollback
class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Test
    @DisplayName("1번 게시판이면서 댓글 페이징 요청을 보내면 페이징 처리와 키워드 검색된 모든 댓글이 조회되어야한다")
    void getList() {
        //given
        Long postId = 1L;
        PageDTO pageDTO = new PageDTO();
        String keyword = "";

        //when
        ReplyListResponseDTO list = replyService.getList(postId, pageDTO, keyword);

        //then
        System.out.println("list = " + list);
    }

    @Test
    @DisplayName("5번 댓글이 상세조회가 되어야만한다")
    void detail() {
        //given
        Long replyId = 5L;

        //when
        ReplyResponseDTO detail = replyService.detail(replyId);

        //then
        System.out.println("detail = " + detail);
    }

    @Test
    @DisplayName("댓글 작성을 하면 테이블에 저장이 되어야만 한다")
    void write() {
        //given
        Long postId = 1L;
        ReplyRequestWriteDTO writeDTO = new ReplyRequestWriteDTO();
        writeDTO.setReplyContent("여기 게시판 깔끔한 것 같아요~");
        writeDTO.setPostWriter("mail");

        //when
        ReplyResponseDTO write = replyService.write(postId, writeDTO);

        //then
        System.out.println("write = " + write);
    }

    @Test
    @DisplayName("5번 댓글을 수정하면 수정이 되어야만 한다")
    void modify() {
        //given
        Long replyId = 5L;
        ReplyRequestModifyDTO modifyDTO =  new ReplyRequestModifyDTO();
        modifyDTO.setReplyId(replyId);
        modifyDTO.setReplyContent("너무 좋아요!");

        //when
        ReplyResponseDTO modify = replyService.modify(modifyDTO);

        //then
        System.out.println("modify = " + modify);
    }

    @Test
    @DisplayName("6번 댓글이 삭제되어야만 한다")
    void delete() {
        //given
        Long replyId = 6L;

        //when
        replyService.delete(replyId);

    }
}