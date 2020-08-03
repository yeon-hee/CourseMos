package com.web.curation.model.feed;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedNo;

    private String userId;
    private String contents;
    @ColumnDefault("0")
    private int likeCount;
    @ColumnDefault("0")
    private int commentCount;
    private String thumbnail;
    @Transient
    private boolean isMine;

    @Column(insertable = false, updatable = false)
    private LocalDateTime writeDate;
}