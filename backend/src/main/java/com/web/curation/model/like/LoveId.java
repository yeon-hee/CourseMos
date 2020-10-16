package com.web.curation.model.like;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoveId implements Serializable {
    private String userId;
    private int    feedNo;
}