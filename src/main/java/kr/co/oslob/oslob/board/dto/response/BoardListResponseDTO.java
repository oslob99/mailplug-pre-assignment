package kr.co.oslob.oslob.board.dto.response;

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
    private int count;
    private int offset;
    private int limit;
    private int total;
}
