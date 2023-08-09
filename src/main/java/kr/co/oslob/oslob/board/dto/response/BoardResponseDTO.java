package kr.co.oslob.oslob.board.dto.response;

import kr.co.oslob.oslob.board.entity.Board;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDTO {

    private long boardId;
    private String boardName;
    private String boardType;

    public BoardResponseDTO toEntity(Board board){
        return BoardResponseDTO.builder()
                .boardId(board.getBoardId())
                .boardName(board.getBoardName())
                .boardType(board.getBoardType())
                .build();
    }
}
