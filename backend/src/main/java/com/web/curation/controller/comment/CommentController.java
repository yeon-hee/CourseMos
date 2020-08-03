package com.web.curation.controller.comment;

import java.util.List;

import com.web.curation.dao.comment.CommentDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.comment.Comment;
import com.web.curation.util.AlertHub;
import com.web.curation.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentDao commentDao;

    @Autowired
    private JwtService jwtService;
    
    @PostMapping
    @ApiOperation(value = "댓글 작성하기")
    public Object writeFeeds(@RequestBody final Comment comment) {
        ResponseEntity response = null;

        try {
            String userId = (String) jwtService.getUserId();

            comment.setWriter(userId);

            commentDao.save(comment);
            response = new ResponseEntity<>(comment, HttpStatus.OK);
            
            
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/{feedNo}/{commentNo}")
    @ApiOperation(value = "댓글 삭제하기")
    public Object loadFeedDetail(@PathVariable final int feedNo, @PathVariable final int commentNo) {
        ResponseEntity response = null;

        try {
            commentDao.deleteByFeedNoAndCommentNo(feedNo, commentNo);

            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK); 
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/{feedNo}")
    @ApiOperation(value = "피드 댓글 보기")
    public Object loadFeedComments(@PathVariable final int feedNo) {
        List<Comment> comments;
        ResponseEntity response = null;

        try {
            comments = commentDao.findByFeedNo(feedNo);

            response = new ResponseEntity<>(comments, HttpStatus.OK); 
        } catch(Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }
}