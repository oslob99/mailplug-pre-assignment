package kr.co.oslob.oslob.post.dto.request;

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
public class PostRequestModifyDTO {

    @NotBlank
    private Long postId;

    @NotBlank
    @Size(min = 1, max = 100)
    private String postTitle;

    @NotBlank
    @Size(min = 1, max = 3000)
    private String postContent;

    @NotBlank
    private String postWriter;
}
