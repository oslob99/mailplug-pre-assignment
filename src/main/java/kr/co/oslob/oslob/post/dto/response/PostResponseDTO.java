package kr.co.oslob.oslob.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.oslob.oslob.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO {

    private long postId;
    private String postTitle;
    private String postContent;
    private String postWriter;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime postDate;

    public PostResponseDTO toEntity(Post post){
        return PostResponseDTO.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postWriter(post.getPostWriter())
                .postDate(post.getPostDate())
                .build();
    }

}
