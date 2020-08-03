package com.web.curation.model.comment;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

@Data
public class CommentId implements Serializable{
    @Id
    private int feedNo;
    @Id
    private int commentNo;
}