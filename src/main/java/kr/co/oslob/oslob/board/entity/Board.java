package kr.co.oslob.oslob.board.entity;

import jakarta.persistence.*;
import kr.co.oslob.oslob.post.entity.Post;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private long boardId;

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "board_type")
    private String boardType;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();
}
