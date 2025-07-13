package ru.itis.vhsroni.comment.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.dentist.data.entity.Dentist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @UuidGenerator
    @Column(name = "comment_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserData author;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "dentist_id")
    private Dentist dentist;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false,  updatable = false)
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children;
}

