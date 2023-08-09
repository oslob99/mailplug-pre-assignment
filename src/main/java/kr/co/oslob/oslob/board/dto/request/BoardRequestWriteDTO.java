package kr.co.oslob.oslob.board.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRequestWriteDTO {

    @NonNull
    private String boardName;

    @NonNull
    private String boardType;
}
