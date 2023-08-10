package kr.co.oslob.oslob.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kr.co.oslob.oslob.board.entity.Board;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRequestWriteDTO {

    @NotBlank
    @Size(min = 1 ,max = 100)
    private String boardName;

    @NotBlank
    private String boardType;

    public Board toEntity(){
        return Board.builder()
                .boardName(this.boardName)
                .boardType(this.boardType)
                .build();
    }

}
