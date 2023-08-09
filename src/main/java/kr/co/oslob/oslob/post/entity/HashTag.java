package kr.co.oslob.oslob.post.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString(exclude = "post")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_hashtag")
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long tagId;

    @Column(name = "tag_content")
    private String tagContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
