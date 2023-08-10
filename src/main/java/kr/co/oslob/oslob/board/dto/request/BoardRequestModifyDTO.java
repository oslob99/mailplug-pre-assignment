package kr.co.oslob.oslob.board.dto.request;

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
public class BoardRequestModifyDTO {

    @NotBlank
    private long boardId;

    @NotBlank
    @Size(min = 1, max = 100)
    private String boardName;

    @NotBlank
    private String boardType;
}
