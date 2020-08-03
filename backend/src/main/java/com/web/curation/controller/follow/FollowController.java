package com.web.curation.controller.follow;

import java.util.ArrayList;
import java.util.List;

import com.web.curation.dao.countinfo.CountinfoDao;
import com.web.curation.dao.following.FollowingDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.countinfo.Countinfo;
import com.web.curation.model.following.Following;
import com.web.curation.model.user.User;

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
import org.springframework.web.bind.annotation.RequestMapping;


@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/friends")
public class FollowController {

    @Autowired
    FollowingDao followingDao;

    @Autowired
    CountinfoDao countinfoDao;

    @Autowired
    UserDao userDao;

    @GetMapping("/followers/{email}")
    @ApiOperation(value = "팔로워 목록 조회")
    public Object getFollower(@PathVariable final String email) {

        List<Following> list;
        List<String> followerList = new ArrayList<String>();
        ResponseEntity response = null;

        try {
            list = followingDao.findAllByEmail(email); 
            for(Following temp : list) followerList.add(temp.getFollower()); // 나를 팔로우 하고 있는 사람들 목록
            response = new ResponseEntity<>(followerList, HttpStatus.OK); 
        } catch(Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/followers/{follower}/{email}")
    @ApiOperation(value = "팔로워 삭제")
    public Object deleteFollower(@PathVariable final String follower,
            @PathVariable final String email) { 
                // follower는 삭제하려는 팔로워 , email은 나의 이메일

        ResponseEntity response = null;

        try {
            followingDao.deleteByFollowerAndEmail(follower, email);

            User user = userDao.findByUserId(follower).get();
            Countinfo count = countinfoDao.findByEmail(user.getEmail());
            count.setFollowingCount(count.getFollowingCount()-1); // 팔로잉 카운트 감소
            countinfoDao.save(count);

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

    @GetMapping("/followings/{follower}")
    @ApiOperation(value = "팔로잉 목록 조회")
    public Object getFollowing(@PathVariable final String follower) {
        // userId로 내 유저아이디

        List<Following> list;
        List<String> followingList = new ArrayList<String>();
        ResponseEntity response = null;

        try {
            list = followingDao.findByFollower(follower);
            for(Following temp : list) followingList.add(temp.getUserId()); // 내가 팔로우 하고 있는 사람들 목록
            response = new ResponseEntity<>(followingList, HttpStatus.OK); 
        } catch(Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/followings/{follower}/{userId}")
    @ApiOperation(value = "팔로잉 삭제")
    public Object deleteFollowing(@PathVariable final String follower,
            @PathVariable final String userId) { // follower가 내 아이디

        ResponseEntity response = null;

        try {
            followingDao.deleteByFollowerAndUserId(follower, userId);

            User user = userDao.findByUserId(userId).get();
            Countinfo count = countinfoDao.findByEmail(user.getEmail());
            count.setFollowerCount(count.getFollowerCount()-1); // 팔로우 카운트 감소
            countinfoDao.save(count);

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


}
