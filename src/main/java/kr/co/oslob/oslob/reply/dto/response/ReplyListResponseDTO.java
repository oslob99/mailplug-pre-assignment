package kr.co.oslob.oslob.reply.dto.response;

import kr.co.oslob.oslob.page.PageResponseDTO;
import kr.co.oslob.oslob.post.dto.response.PostResponseDTO;
import kr.co.oslob.oslob.reply.entity.Reply;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyListResponseDTO {

    private List<ReplyResponseDTO> value;
    private PageResponseDTO<Reply> pageInfo;
    private int count;
}
