package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime commentDateTime;

    @Column(nullable = false)
    private boolean visible;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "comment_state_comment",
        joinColumns = { @JoinColumn(name = "comment_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "comment_state_id", referencedColumnName = "id") })
    private CommentState commentState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "post_comment",
        joinColumns = { @JoinColumn(name = "comment_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "post_id", referencedColumnName = "id") })
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "student_comment",
        joinColumns = { @JoinColumn(name = "comment_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id") })
    private Student student;

    @OneToMany(mappedBy = "comment")
    private List<CommentReport> commentReports;

}