package kr.co.oslob.oslob.reply.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kr.co.oslob.oslob.post.entity.Post;
import kr.co.oslob.oslob.reply.entity.Reply;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyRequestWriteDTO {

    @NotBlank
    @Size(min = 1 ,max = 300)
    private String replyContent;

    @NotBlank
    private String replyWriter;

    public Reply toEntity(Post post){
        return Reply.builder()
                .replyContent(this.getReplyContent())
                .replyWriter(this.getReplyWriter())
                .post(post)
                .build();
    }
}
