package kr.co.oslob.oslob.post.dto.response;

import kr.co.oslob.oslob.page.PageResponseDTO;
import kr.co.oslob.oslob.post.entity.Post;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponseDTO {

    private List<PostResponseDTO> value;
    private PageResponseDTO<Post> pageInfo;
    private int count;
}
