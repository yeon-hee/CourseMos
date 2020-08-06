package com.web.curation.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;

import com.web.curation.dao.countinfo.CountinfoDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.countinfo.Countinfo;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import com.web.curation.service.JwtService;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    CountinfoDao countinfoDao;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public Object login(@Valid @RequestBody final User user){
        ResponseEntity response = null;
        User findUser = userDao.findUserByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        findUser.setPassword(null);
        findUser.setCreateDate(null);
        String token = jwtService.create("user", findUser, "user");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("user", findUser);
        
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = map;
        response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

    @GetMapping("/profile/checkpassword")
    @ApiOperation(value = "비밀번호 확인") // 비밀번호 확인
    public Object checkPassword(@RequestParam(required = true) final String password) {
        
        String email = (String) jwtService.getUserEmail();
        Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
        ResponseEntity response = null;

        if (userOpt.isPresent()) { // 비밀번호 맞을 시 
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;
    }
    
    @PutMapping("/profile/password")
    @ApiOperation(value = "비밀번호 변경") // 비밀번호 변경
    public Object password(@Valid @RequestBody final User user) {
        String email = (String) jwtService.getUserEmail();
        System.out.println(user);
        User findUser = userDao.findByEmail(email);
        findUser.setPassword(user.getPassword());
        userDao.save(findUser);
        ResponseEntity response = null;

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        response = new ResponseEntity<>(result, HttpStatus.OK);

        return response;
    }

    @PostMapping("/signup")
    @ApiOperation(value = "가입하기") // 회원가입하기
    public Object signup(@Valid @RequestBody final SignupRequest request) {

        ResponseEntity response = null;

        try { 

            User user = new User();
            user.setUserId(request.getUserId());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setProfilePhoto(request.getProfilePhoto());
            user.setProfileComment(request.getProfileComment());
            
            userDao.save(user); // 회원가입

            Countinfo count = new Countinfo();
            count.setEmail(request.getEmail());
            count.setFollowingCount(0);
            count.setFollowerCount(0);
            count.setFeedCount(0);
            count.setTotalLikeCount(0);
            countinfoDao.save(count);

            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(user, HttpStatus.OK); // user에 대한 정보 리턴

        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/profile")
    @ApiOperation(value = "프로필 유저 정보 가져오기") // 내 프로필 화면 -> countController에서 하기
    public Object profile() { // 동시에 하는 비동기 -> get 두번 요청

        ResponseEntity response = null;

        try { 
            String email = (String) jwtService.getUserEmail();
            User user = userDao.findByEmail(email);
            
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(user, HttpStatus.OK); // user 정보 담아서 리턴

        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/profile/{userId}")
    @ApiOperation(value = "프로필 타 유저 정보 가져오기") // 내 프로필 화면 -> countController에서 하기
    public Object getUserProfile(@PathVariable final String userId) { // 동시에 하는 비동기 -> get 두번 요청

        ResponseEntity response = null;

        try { 
            User user = userDao.findByUserId(userId).get();
            
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(user, HttpStatus.OK); // user 정보 담아서 리턴

        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;
    }


    @PutMapping("/profile")
    @ApiOperation(value = "프로필 수정") // 계정 정보 수정 - 유저아이디 중복 될 경우 알려주기!!!
    public Object profileEdit(@Valid @RequestBody  final User user) {
        // Put 방식 -> RequestBody로 넘기기 (객체로 받기)

        Optional<User> use = userDao.findByUserId(user.getUserId());
        ResponseEntity response = null;

        if(use.isPresent()){ // 있으면 안됨 - 중복 체크
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            System.out.println("유저아이디 중복");
        }
        else{ // 기존에 없는 유저 아이디일 경우 추가
            try {
                User people = userDao.findByEmail(user.getEmail());
                people.setUserId(user.getUserId()); // 바뀌는 내용
                people.setProfileComment(user.getProfileComment());
                userDao.save(people);

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
        }

        return response;
    }

    @PutMapping("/profile/image")
    @ApiOperation(value = "프로필 이미지 수정") // 계정 정보 수정 - 유저아이디 중복 될 경우 알려주기!!!
    public Object profileImageEdit(@Valid @RequestBody  final User user) {
        // Put 방식 -> RequestBody로 넘기기 (객체로 받기)

        ResponseEntity response = null;

        try {
            User people = userDao.findByEmail(user.getEmail());
            people.setProfilePhoto(user.getProfilePhoto());
            userDao.save(people);

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

    @GetMapping("/{keyword}")
    @ApiOperation(value = "유저목록 조회") // 유저 목록 조회
    public Object userList(@PathVariable final String keyword) { // 이 단어 포함된 유저 목록 리턴

        ResponseEntity response = null;

        try {  
            List<User> list = userDao.findByUserIdLike("%"+keyword+"%");
            response = new ResponseEntity<>(list, HttpStatus.OK);
        } catch(Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = e.toString();
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/find/password/{userId}/{email}")
    @ApiOperation(value = "비밀번호 찾기") 
    public Object findPassword(@PathVariable final String userId, @PathVariable final String email) { 

        ResponseEntity response = null;
        Optional<User> userOpt = userDao.findByUserId(userId);

        if (userOpt.isPresent()) { // 아이디 존재할 시 
            User user = userOpt.get();
            if(email.equals(user.getEmail())){
                String password = user.getPassword();
                final BasicResponse result = new BasicResponse();
                result.status = true;
                result.data = "success";
                response = new ResponseEntity<>(password, HttpStatus.OK);
            }
            else {
                final BasicResponse result = new BasicResponse();
                result.status = false;
                response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            
        } else {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
