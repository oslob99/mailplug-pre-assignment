package kr.co.oslob.oslob.reply.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyRequestModifyDTO {

    @NotBlank
    private Long replyId;

    @NotBlank
    @Size(min = 1, max = 300)
    private String replyContent;

    @NotBlank
    private String replyWriter;

}
