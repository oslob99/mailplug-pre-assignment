package kr.co.oslob.oslob.board.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRequestModifyDTO {

    @NonNull
    private String boardName;

    @NonNull
    private String boardType;
}
