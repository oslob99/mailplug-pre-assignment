package kr.co.oslob.oslob.reply.service;

import kr.co.oslob.oslob.common.exception.ErrorCode;
import kr.co.oslob.oslob.common.exception.NotFoundReplyException;
import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.page.PageResponseDTO;
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

    private final ReplyRepository replyRepository;

    public ReplyListResponseDTO getList(PageDTO pageDTO, String keyword) {

        Pageable pageable = PageRequest.of(
                pageDTO.getOffset() - 1,
                pageDTO.getLimit(),
                Sort.by(Sort.Direction.DESC,"replyDate")
        );

        Page<Reply> replies;

        if (keyword.isEmpty()){
            replies = replyRepository.findAll(pageable);
        }else {
            replies = replyRepository.findByKeyword(keyword, pageable);
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

    public ReplyResponseDTO detail(Long replyId) {
        return new ReplyResponseDTO().toEntity(findByReply(replyId));
    }

    public void write(ReplyRequestWriteDTO writeDTO) {

        Reply savedReply = replyRepository.save(writeDTO.toEntity());

    }

    public void modify(ReplyRequestModifyDTO modifyDTO) {

        Reply byReply = findByReply(modifyDTO.getReplyId());

        byReply.setReplyContent(modifyDTO.getReplyContent());
        byReply.setReplyWriter(modifyDTO.getReplyWriter());

        replyRepository.save(byReply);

    }

    public void delete(Long replyId) {

        replyRepository.delete(findByReply(replyId));

    }

    private Reply findByReply(Long replyId) {
        return replyRepository.findById(replyId).orElseThrow(
                () -> {
                    throw new NotFoundReplyException(ErrorCode.NOT_FOUND_REPLY, replyId);
                }
        );
    }
}
