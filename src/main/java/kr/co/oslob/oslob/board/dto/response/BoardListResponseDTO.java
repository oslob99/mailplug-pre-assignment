package kr.co.oslob.oslob.board.dto.response;

import kr.co.oslob.oslob.board.entity.Board;
import kr.co.oslob.oslob.page.PageResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardListResponseDTO {

    private List<BoardResponseDTO> value;
    private PageResponseDTO<Board> pageInfo;
    private int count;
}
