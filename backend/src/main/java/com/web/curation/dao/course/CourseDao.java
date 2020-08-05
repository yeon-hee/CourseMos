package com.web.curation.dao.course;

import java.util.List;

import javax.transaction.Transactional;

import com.web.curation.model.course.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {

    List<Course> findAllByFeedNo(int feedNo);

    @Transactional
    void deleteByFeedNoAndCourseOrder(int feedNo, int courseOrder);
}