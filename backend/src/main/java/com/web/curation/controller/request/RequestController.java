package com.web.curation.controller.request;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.web.curation.dao.alert.AlertDao;
import com.web.curation.dao.countinfo.CountinfoDao;
import com.web.curation.dao.following.FollowingDao;
import com.web.curation.dao.request.RequestDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.alert.Alert;
import com.web.curation.model.countinfo.Countinfo;
import com.web.curation.model.following.Following;
import com.web.curation.model.request.Request;
import com.web.curation.model.user.User;
import com.web.curation.service.JwtService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    RequestDao requestDao;

    @Autowired
    FollowingDao followingDao;

    @Autowired
    CountinfoDao countinfoDao;

    @Autowired
    UserDao userDao;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/{email}")
    @ApiOperation(value = "요청 목록 조회")
    public Object userList(@PathVariable final String email) {

        List<Request> request = requestDao.findAllByEmail(email);

        ResponseEntity response = null;
        response = new ResponseEntity<>(request, HttpStatus.OK);
        return response;
    }

    @PostMapping("")
    @ApiOperation(value = "팔로우 요청")
    public Object followRequest(@Valid @RequestBody final Request request) {

        requestDao.save(request);

        ResponseEntity response = null;
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/allow/{follower}/{following_target}")
    @ApiOperation(value = "팔로우 요청 승인") 
    public Object followAllow(@PathVariable final String follower, @PathVariable final String following_target) { // follower가 팔로워한 사람


        Following follow = new Following();
        follow.setFollower(follower);
        follow.setEmail(following_target);
        follow.setUserId((String) jwtService.getUserId());
        followingDao.save(follow); // following table에 추가

        Countinfo count = countinfoDao.findByEmail((String) jwtService.getUserEmail());
        if(count == null){ // 기존 카운트 테이블 없는 경우 - 팔로워 카운트 증가
            Countinfo countinfo = new Countinfo();
            countinfo.setFollowerCount(1);
            countinfo.setEmail((String) jwtService.getUserEmail());
            countinfoDao.save(countinfo);
        }
        else {
            count.setFollowerCount(count.getFollowerCount()+1);
            countinfoDao.save(count); // update
        }

        User user = userDao.findByUserId(follower).get();
        Countinfo following = countinfoDao.findByEmail(user.getEmail());
        if(following == null){ // 팔로잉 수 증가
            Countinfo countinfo = new Countinfo();
            countinfo.setFollowingCount(1);
            countinfo.setEmail(user.getEmail());
            countinfoDao.save(countinfo);
        }
        else {
            following.setFollowingCount(following.getFollowingCount()+1);
            countinfoDao.save(following); // update
        }

        // request table에서 삭제
        requestDao.deleteByFollowerAndEmail(follower, following_target);

        ResponseEntity response = null;
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/{follower}/{following_target}")
    @ApiOperation(value = "팔로우 요청 거절") 
    public Object followReject(@PathVariable final String follower, @PathVariable final String following_target) { 

        // request table에서 삭제
        requestDao.deleteByFollowerAndEmail(follower, following_target);

        ResponseEntity response = null;
        final BasicResponse result = new BasicResponse();            
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/allowAll")
    @ApiOperation(value = "팔로우 요청 모두 승인") 
    public Object followAllowAll() { // follower가 팔로워한 사람

        String following_target = (String) jwtService.getUserEmail();
        List<Request> list = requestDao.findAllByEmail(following_target);

        for(Request temp : list){
            Following follow = new Following();
            follow.setEmail(temp.getEmail());
            follow.setFollower(temp.getFollower());  
            follow.setUserId((String) jwtService.getUserId());
            followingDao.save(follow); 

            Countinfo count = countinfoDao.findByEmail(temp.getEmail()); // 팔로우 수 증가
            if(count == null){ // 기존 카운트 테이블 없는 경우 - 팔로워 카운트 증가
                Countinfo countinfo = new Countinfo();
                countinfo.setFollowerCount(1);
                countinfo.setEmail(temp.getEmail());
                countinfoDao.save(countinfo);
            }
            else {
                count.setFollowerCount(count.getFollowerCount()+1);
                countinfoDao.save(count); // update
            }

            User user = userDao.findByUserId(temp.getFollower()).get();
            Countinfo following = countinfoDao.findByEmail(user.getEmail());
            if(following == null){ // 팔로잉 수 증가
                Countinfo countinfo = new Countinfo();
                countinfo.setFollowingCount(1);
                countinfo.setEmail(user.getEmail());
                countinfoDao.save(countinfo);
            }
            else {
                following.setFollowingCount(following.getFollowingCount()+1);
                countinfoDao.save(following); // update
            }

            requestDao.deleteByFollowerAndEmail(temp.getFollower(), following_target);
        }
        
        ResponseEntity response = null;
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/rejectAll")
    @ApiOperation(value = "팔로우 요청 모두 거절") 
    public Object followRejectAll() { 

        String following_target = (String) jwtService.getUserEmail();
        List<Request> list = requestDao.findAllByEmail(following_target);
        for(Request temp : list) requestDao.deleteByFollowerAndEmail(temp.getFollower(), following_target);

        ResponseEntity response = null;
        final BasicResponse result = new BasicResponse();            
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

}
