package kr.co.oslob.oslob.reply.service;

import kr.co.oslob.oslob.common.exception.ErrorCode;
import kr.co.oslob.oslob.common.exception.NotFoundPostException;
import kr.co.oslob.oslob.common.exception.NotFoundReplyException;
import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.page.PageResponseDTO;
import kr.co.oslob.oslob.post.entity.Post;
import kr.co.oslob.oslob.post.repository.PostRepository;
import kr.co.oslob.oslob.reply.dto.request.ReplyRequestModifyDTO;
import kr.co.oslob.oslob.reply.dto.request.ReplyRequestWriteDTO;
import kr.co.oslob.oslob.reply.dto.response.ReplyListResponseDTO;
import kr.co.oslob.oslob.reply.dto.response.ReplyResponseDTO;
import kr.co.oslob.oslob.reply.entity.Reply;
import kr.co.oslob.oslob.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    /**
     * @param postId 게시판 번호
     * @param pageDTO 댓글 페이징 처리 정보
     * @param keyword 댓글 내용, 작성자를 조회하는 키워드
     * @return 페이징 처리와 키워드를 조회해 댓글 전체 목록을 반환한다
     */
    public ReplyListResponseDTO getList(Long postId, PageDTO pageDTO, String keyword) {

        // 게시글이 존재하는지 확인
        postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException(ErrorCode.INVALID_PARAMETER,postId)
        );

        Pageable pageable = PageRequest.of(
                pageDTO.getOffset() - 1,
                pageDTO.getLimit(),
                Sort.by(Sort.Direction.DESC,"replyDate")
        );

        Page<Reply> replies;

        if (keyword == null){
            replies = replyRepository.findByPostPostId(postId, pageable);
        }else {
            replies = replyRepository.findByKeyword(postId, keyword, pageable);
        }

        List<ReplyResponseDTO> responseDTOList = replies.stream().map(
                reply -> new ReplyResponseDTO().toEntity(reply)
        ).toList();

        return ReplyListResponseDTO.builder()
                .value(responseDTOList)
                .pageInfo(new PageResponseDTO<Reply>(replies))
                .count(responseDTOList.size())
                .build();
    }

    /**
     * @param replyId 상세보기할 댓글 번호
     * @return 상세보기에 필요한 댓글 정보를 반환한다
     */
    public ReplyResponseDTO detail(Long replyId) {
        return new ReplyResponseDTO().toEntity(findByReply(replyId));
    }

    /**
     * @param postId 게시글 번호
     * @param writeDTO 작성에 필요한 정보
     * @return 작성된 댓글을 반환한다
     */
    public ReplyResponseDTO write(Long postId, ReplyRequestWriteDTO writeDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException(ErrorCode.INVALID_PARAMETER, postId)
        );
        return new ReplyResponseDTO().toEntity(replyRepository.save(writeDTO.toEntity(post)));
    }

    /**
     * @param modifyDTO 수정에 필요한 정보
     * @return 수정된 댓글을 반환한다
     */
    public ReplyResponseDTO modify(ReplyRequestModifyDTO modifyDTO) {

        Reply byReply = findByReply(modifyDTO.getReplyId());

        byReply.setReplyContent(modifyDTO.getReplyContent());
        byReply.setReplyWriter(modifyDTO.getReplyWriter());

        return new ReplyResponseDTO().toEntity(replyRepository.save(byReply));
    }

    /**
     * @param replyId 삭제할 댓글 번호
     */
    public void delete(Long replyId) {
        replyRepository.delete(findByReply(replyId));
    }

    /**
     * @param replyId 조회할 댓글번호
     * @return 예외처리를 포함해 댓글을 반환하다
     */
    private Reply findByReply(Long replyId) {
        return replyRepository.findById(replyId).orElseThrow(
                () -> {
                    throw new NotFoundReplyException(ErrorCode.NOT_FOUND_REPLY, replyId);
                }
        );
    }
}
