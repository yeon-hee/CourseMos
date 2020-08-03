package com.web.curation.controller.countinfo;

import com.web.curation.dao.countinfo.CountinfoDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.countinfo.Countinfo;
import com.web.curation.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/counts")
public class CountinfoController {

    @Autowired
    CountinfoDao countinfoDao;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/profile")
    @ApiOperation(value = "프로필에서 카운트 가져오기") 
    public Object profile() { 

        ResponseEntity response = null;

        try {  // count 정보 (좋아요 수, 댓글 수...)
        String email = (String) jwtService.getUserEmail();
        Countinfo countinfo = countinfoDao.findByEmail(email);
        
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(countinfo, HttpStatus.OK); // countinfo 정보 담아서 리턴

        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/profile/{email}")
    @ApiOperation(value = "프로필에서 카운트 가져오기") 
    public Object getUserProfile(@PathVariable final String email) { 

        ResponseEntity response = null;

        try {  // count 정보 (좋아요 수, 댓글 수...)
        Countinfo countinfo = countinfoDao.findByEmail(email);
        
        
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(countinfo, HttpStatus.OK); // countinfo 정보 담아서 리턴

        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;
    }




    @GetMapping("/followerDown")
    @ApiOperation(value = "팔로워 차단") 
    public Object followerDown() { // 유저 이메일에 해당하는 팔로워 카운트 하나 감소

        ResponseEntity response = null;

        try { 
        String email = (String) jwtService.getUserEmail();
        Countinfo countinfo = countinfoDao.findByEmail(email);

        int followerCount = countinfo.getFollowerCount();
        countinfo.setFollowerCount(followerCount-1);
        countinfoDao.save(countinfo);
        
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(countinfo, HttpStatus.OK); 

        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/followingDown")
    @ApiOperation(value = "팔로잉 차단") 
    public Object followingDown() { // 유저 이메일에 해당하는 팔로잉 카운트 하나 감소

        ResponseEntity response = null;

        try { 
        String email = (String) jwtService.getUserEmail();
        Countinfo countinfo = countinfoDao.findByEmail(email);

        int followingCount = countinfo.getFollowingCount();
        countinfo.setFollowingCount(followingCount-1);
        countinfoDao.save(countinfo);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(countinfo, HttpStatus.OK); 

        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;
    }

}
