package kr.co.oslob.oslob.reply.entity;

import jakarta.persistence.*;
import kr.co.oslob.oslob.post.entity.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private long replyId;

    @Column(name = "reply_content")
    private String replyContent;

    @Column(name = "reply_writer")
    private String replyWriter;

    @CreationTimestamp
    @Column(name = "reply_date",updatable = false)
    private LocalDateTime replyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
