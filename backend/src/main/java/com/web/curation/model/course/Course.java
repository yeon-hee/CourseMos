package com.web.curation.model.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@IdClass(CourseId.class)
public class Course {

    @Id
    @Column(name = "feed_no")
    private int feedNo; 

    @Id
    @Column(name = "course_order")
    private int courseOrder;

    @Id
    @Column(name = "trade_name")
    private String tradeName;

    @Id
    @Column(name = "latitude")
    private String latitude;

    @Id
    @Column(name = "longitude")
    private String longitude;

    @Id
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
}