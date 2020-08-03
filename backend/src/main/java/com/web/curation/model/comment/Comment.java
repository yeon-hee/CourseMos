package com.web.curation.model.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@IdClass(CommentId.class)
public class Comment{
    @Id
    @Column(name = "feed_no")
    private int feedNo;

    @Id
    @Column(name = "comment_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @ManyToOne
    // @JoinColumn(name="feed_no")
    private int commentNo;

    private String writer;

    private String content;

    @Column(insertable = false, updatable = false)
    private LocalDateTime writeDate;
}