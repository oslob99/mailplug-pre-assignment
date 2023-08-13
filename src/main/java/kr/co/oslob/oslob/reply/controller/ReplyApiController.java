package kr.co.oslob.oslob.reply.controller;

import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.reply.dto.request.ReplyRequestModifyDTO;
import kr.co.oslob.oslob.reply.dto.request.ReplyRequestWriteDTO;
import kr.co.oslob.oslob.reply.dto.response.ReplyListResponseDTO;
import kr.co.oslob.oslob.reply.dto.response.ReplyResponseDTO;
import kr.co.oslob.oslob.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static kr.co.oslob.oslob.common.exception.ErrorCode.INVALID_PARAMETER;

@RestController
@RequestMapping("/api/oslob/reply")
@Slf4j
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    @GetMapping("/list/{postId}")
    public ResponseEntity<?> list(
            @PathVariable Long postId
            ,PageDTO pageDTO
            , @RequestParam(required = false)String keyword
    ){

        log.info("/api/oslob/reply/list/{}?offset={}&limit={}&keyword={}"
                ,postId,pageDTO.getOffset(),pageDTO.getLimit(),keyword);

        ReplyListResponseDTO responseDTO = replyService.getList(postId, pageDTO, keyword);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/detail/{replyId}")
    public ResponseEntity<?> detail(
            @PathVariable Long replyId
    ){
        log.info("/api/oslob/reply/detail/{}",replyId);

        try {
            ReplyResponseDTO responseDTO = replyService.detail(replyId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 댓글은 존재하지 않습니다.");
        }

    }

    @PostMapping("/write/{postId}")
    public ResponseEntity<?> write(
            @PathVariable Long postId
            ,@Validated @RequestBody ReplyRequestWriteDTO writeDTO
            ,BindingResult bindingResult
    ){
        log.info("/api/oslob/reply/write : writeDTO : {}",writeDTO);

        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(INVALID_PARAMETER);
        }

            ReplyResponseDTO responseDTO = replyService.write(postId, writeDTO);
            return ResponseEntity.ok().body(responseDTO);
    }

    @PatchMapping("/modify")
    public ResponseEntity<?> modify(
            @Validated @RequestBody ReplyRequestModifyDTO modifyDTO
            , BindingResult bindingResult
    ){
        log.info("/api/oslob/reply/modify : modifyDTO : {}",modifyDTO);

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(INVALID_PARAMETER);
        }

        try {
            ReplyResponseDTO responseDTO = replyService.modify(modifyDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 댓글은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/delete/{replyId}")
    public ResponseEntity<?> delete(
            @PathVariable Long replyId
    ){
        log.info("/api/oslob/reply/delete/{}",replyId);

        try {
            replyService.delete(replyId);
            return ResponseEntity.ok().body("삭제가 완료되었습니다.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 댓글은 존재하지 않습니다.");
        }
    }
}
