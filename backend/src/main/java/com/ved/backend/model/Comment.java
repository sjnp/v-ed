package com.ved.backend.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

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
    @JoinTable(name = "question_board_comment",
        joinColumns = { @JoinColumn(name = "comment_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "question_board_id", referencedColumnName = "id") })
    private QuestionBoard questionBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "student_comment",
        joinColumns = { @JoinColumn(name = "comment_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id") })
    private Student student;

    @OneToMany(mappedBy = "comment")
    private List<CommentReport> commentReports;

    public Comment() {
    }

    public Comment(Long id, String comment, LocalDateTime commentDateTime, boolean visible, CommentState commentState, QuestionBoard questionBoard, Student student, List<CommentReport> commentReports) {
        this.id = id;
        this.comment = comment;
        this.commentDateTime = commentDateTime;
        this.visible = visible;
        this.commentState = commentState;
        this.questionBoard = questionBoard;
        this.student = student;
        this.commentReports = commentReports;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCommentDateTime() {
        return commentDateTime;
    }

    public void setCommentDateTime(LocalDateTime commentDateTime) {
        this.commentDateTime = commentDateTime;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public QuestionBoard getQuestionBoard() {
        return questionBoard;
    }

    public void setQuestionBoard(QuestionBoard questionBoard) {
        this.questionBoard = questionBoard;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CommentState getCommentState() {
        return commentState;
    }

    public void setCommentState(CommentState commentState) {
        this.commentState = commentState;
    }

    public List<CommentReport> getCommentReports() {
        return commentReports;
    }

    public void setCommentReports(List<CommentReport> commentReports) {
        this.commentReports = commentReports;
    }
}