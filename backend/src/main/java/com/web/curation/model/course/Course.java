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

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "road_address")
    private String roadAddress;
}