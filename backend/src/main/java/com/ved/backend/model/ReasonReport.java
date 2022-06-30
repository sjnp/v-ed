package com.ved.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "reason_report")
public class ReasonReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String description;

    @OneToMany(mappedBy = "reasonReport")
    private List<ReviewReport> reviewReports;

    @OneToMany(mappedBy = "reasonReport")
    private List<PostReport> postReports;

    @OneToMany(mappedBy = "reasonReport")
    private List<CommentReport> commentReports;

}