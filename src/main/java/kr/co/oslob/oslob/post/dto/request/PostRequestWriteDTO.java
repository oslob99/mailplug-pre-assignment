package kr.co.oslob.oslob.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kr.co.oslob.oslob.post.entity.Post;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestWriteDTO {

    @NotBlank
    @Size(min = 1 ,max = 100)
    private String postTitle;

    @NotBlank
    @Size(min = 1 ,max = 3000)
    private String postContent;

    @NotBlank
    private String postWriter;

    public Post toEntity(){
        return Post.builder()
                .postTitle(this.postTitle)
                .postContent(this.postContent)
                .postWriter(this.postWriter)
                .build();
    }
}
