package kr.co.oslob.oslob.reply.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.oslob.oslob.reply.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyResponseDTO {

    private long replyId;
    private String replyContent;
    private String replyWriter;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime replyDate;

    public ReplyResponseDTO toEntity(Reply reply){
        return ReplyResponseDTO.builder()
                .replyId(reply.getReplyId())
                .replyContent(reply.getReplyContent())
                .replyWriter(reply.getReplyWriter())
                .replyDate(reply.getReplyDate())
                .build();
    }
}
