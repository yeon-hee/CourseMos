package com.web.curation.controller.alert;

import java.util.List;
import javax.validation.Valid;

import com.web.curation.dao.alert.AlertDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.alert.Alert;
import com.web.curation.model.feed.Feed;
import com.web.curation.service.JwtService;
import com.web.curation.util.FeedHub;
import com.web.curation.util.UserHub;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    AlertDao alertDao;
    
    @Autowired
    private JwtService jwtService;

    @GetMapping("")
    @ApiOperation(value = "알림 목록 조회")
    public Object userList() {
        
        ResponseEntity response = null;
        try {
            List<Alert> alerts = alertDao.findAllByUserId((String)jwtService.getUserId());
            response = new ResponseEntity<>(alerts, HttpStatus.OK);
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("/{feedNo}/comment")
    @ApiOperation(value = "댓글 알림 추가")
    public Object addCommentAlert(@PathVariable final int feedNo) {
        ResponseEntity response = null;

        try {
            Alert alert = new Alert();
            alert.setUserId(FeedHub.getWriter(feedNo));

            String writeUserId =  UserHub.getUserIdByEmail((String)jwtService.getUserEmail());
            alert.setMessage(writeUserId+"님이 댓글을 남겼습니다.");

            Alert savedAlert = alertDao.save(alert);
            
            response = new ResponseEntity<>(savedAlert, HttpStatus.OK);
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping
    @ApiOperation(value = "좋아요 알림 추가")
    public Object addLikeAlert(@RequestBody final Alert feed) {

        ResponseEntity response = null;

        try {
            Alert alert = new Alert();
            
            alert.setUserId(feed.getUserId());
            String writeUserId = (String) jwtService.getUserId();
            alert.setMessage(writeUserId+"님이 회원님의 게시물을 좋아합니다.");
            alertDao.save(alert);
            response = new ResponseEntity<>("success", HttpStatus.OK);
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("/{userId}/accept")
    @ApiOperation(value = "팔로우 승인 알림")
    public Object addFollowAcceptAlert(@PathVariable final String userId) {
        ResponseEntity response = null;

        try {
            Alert alert = new Alert();
            alert.setUserId(userId);

            String acceptUserId =  UserHub.getUserIdByEmail((String)jwtService.getUserEmail());
            alert.setMessage(acceptUserId+"님이 팔로우를 승인했습니다.");

            Alert savedAlert = alertDao.save(alert);
            
            response = new ResponseEntity<>(savedAlert, HttpStatus.OK);
            
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("/{userId}/deny")
    @ApiOperation(value = "팔로우 거절 알림")
    public Object addFollowDenyAlert(@PathVariable final String userId) {
        ResponseEntity response = null;

        try {
            
            response = new ResponseEntity<>("", HttpStatus.OK);
            
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }
    
    @DeleteMapping("/{alertNo}")
    @ApiOperation(value = "알림 지우기")
    public Object DeleteAlert(@PathVariable final int alertNo) {
        ResponseEntity response = null;

        try {
            Alert alert = alertDao.findByAlertNo(alertNo);
            alertDao.delete(alert);
            response = new ResponseEntity<>("success", HttpStatus.OK);
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/CloseAll")
    @ApiOperation(value = "모든 알림 지우기")
    public Object DeleteAllAlert() {

        ResponseEntity response = null;

        try {
            List<Alert> list = alertDao.findAllByUserId((String) jwtService.getUserId());
            for(Alert temp : list) alertDao.delete(temp);
            response = new ResponseEntity<>("success", HttpStatus.OK);
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
