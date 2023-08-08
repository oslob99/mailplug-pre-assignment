package kr.co.oslob.oslob.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "board")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private long boardId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "board_type")
    private String boardType;

    @Column(name = "is_favorite")
    private boolean isFavorite;

    @Column(name = "order_no")
    private long orderNo;

}
