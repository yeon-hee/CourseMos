package com.web.curation.controller.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.curation.dao.comment.CommentDao;
import com.web.curation.dao.course.CourseDao;
import com.web.curation.dao.feed.FeedDao;
import com.web.curation.dao.like.LoveDao;
import com.web.curation.dao.photo.PhotoDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.course.Course;
import com.web.curation.model.feed.Feed;
import com.web.curation.model.like.Love;
import com.web.curation.model.photo.Photo;
import com.web.curation.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseDao courseDao;

    @Autowired
    JwtService jwtService;

    @GetMapping("/course/{feedNo}")
    @ApiOperation(value = "코스 정보 받아오기")
    public Object getCourse(@RequestBody final int feedNo) {

        ResponseEntity response = null;

        try {
            List<Course> course = courseDao.findAllByFeedNo(feedNo);
            response = new ResponseEntity<>(course, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "코스 정보 업로드")
    public Object getCourse(@PathVariable final Course course) {

        ResponseEntity response = null;

        try {
            courseDao.save(course);
            response = new ResponseEntity<>(course, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

}