package com.web.curation.controller.feed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.curation.dao.comment.CommentDao;
import com.web.curation.dao.course.CourseDao;
import com.web.curation.dao.feed.FeedDao;
import com.web.curation.dao.like.LoveDao;
import com.web.curation.dao.photo.PhotoDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.feed.Feed;
import com.web.curation.model.like.Love;
import com.web.curation.model.photo.Photo;
import com.web.curation.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/feeds")
public class FeedController {

    @Autowired
    FeedDao feedDao;
    @Autowired
    PhotoDao photoDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    LoveDao likeDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    JwtService jwtService;

    @GetMapping
    @ApiOperation(value = "피드 전체보기")
    public Object loadFeeds(@RequestParam int page) {

        String userId = (String) jwtService.getUserId();
        Page<Feed> feeds;
        ResponseEntity response = null;
        System.out.println(page);
        try {
            PageRequest request = PageRequest.of(page, 3, Sort.by(Direction.DESC, "writeDate"));

            feeds = feedDao.findAll(request);
            for (Feed feed : feeds) {
                System.out.println(feed);
                int feedNo = feed.getFeedNo();
                feed.setCommentCount(commentDao.countByFeedNo(feedNo));
                feed.setLikeCount(likeDao.countByFeedNo(feedNo));
                Love love = likeDao.findByFeedNoAndUserId(feedNo, userId);
                if (love != null) {
                    feed.setMine(true);
                } else {
                    feed.setMine(false);
                }
            }

            response = new ResponseEntity<>(feeds, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "유저 피드 보기")
    public Object loadUserFeeds(@PathVariable final String userId) {

        List<Feed> feeds;
        ResponseEntity response = null;

        try {
            feeds = feedDao.findAllByUserId(userId);

            response = new ResponseEntity<>(feeds, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping
    @ApiOperation(value = "피드 작성하기")
    public Object writeFeeds(@RequestBody final Feed feed) {
        ResponseEntity response = null;

        try {
            feedDao.save(feed);

            response = new ResponseEntity<>(feed, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/{feedNo}")
    @ApiOperation(value = "피드 상세보기")
    public Object loadFeedDetail(@PathVariable final int feedNo) {
        Feed feed;
        ResponseEntity response = null;
        String userId = (String) jwtService.getUserId();

        try {
            feed = feedDao.findByFeedNo(feedNo);
            feed.setCommentCount(commentDao.countByFeedNo(feedNo));
            feed.setLikeCount(likeDao.countByFeedNo(feedNo));
            Love love = likeDao.findByFeedNoAndUserId(feedNo, userId);
            if (love != null) {
                feed.setMine(true);
            } else {
                feed.setMine(false);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("feed", feed);
            map.put("userId", userId);
            response = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/{feedNo}/photos")
    @ApiOperation(value = "피드 이미지 보기")
    public Object loadFeedPhotos(@PathVariable final int feedNo) {
        List<Photo> photos;
        ResponseEntity response = null;

        try {
            photos = photoDao.findByFeedNo(feedNo);

            response = new ResponseEntity<>(photos, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    // @GetMapping("/profile/{userId}")
    @GetMapping("/profile/{feedNo}")
    @ApiOperation(value = "피드 정보 조회")
    public Object FeedInfo(@PathVariable final int feedNo) {

        ResponseEntity response = null;
        try {
            Feed feed = feedDao.findByFeedNo(feedNo);
            response = new ResponseEntity<>(feed, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/{feedNo}/like")
    @ApiOperation(value = "좋아요 누르기")
    public Object likeFeed(@PathVariable final int feedNo) {
        String userId = (String) jwtService.getUserId();
        Love love = likeDao.findByFeedNoAndUserId(feedNo, userId);
        System.out.println(love);
        ResponseEntity response = null;

        try {
            if (love != null) {
                likeDao.deleteByFeedNoAndUserId(feedNo, userId);
                response = new ResponseEntity<>("좋아요 삭제", HttpStatus.OK);
            } else {
                Love newLike = new Love();
                newLike.setFeedNo(feedNo);
                newLike.setUserId(userId);
                likeDao.save(newLike);
                response = new ResponseEntity<>("좋아요 누름", HttpStatus.OK);
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/search/{search}")
    @ApiOperation(value = "키워드로 피드 검색")
    public Object searchFeeds(@PathVariable final String search, @RequestParam int page) {
        String userId = (String) jwtService.getUserId();

        ResponseEntity response = null;
        try {
            PageRequest request = PageRequest.of(page, 3, Sort.by(Direction.DESC, "writeDate"));
            List<Feed> feeds = feedDao.findAllBySearch(search, request);
            response = new ResponseEntity<>(feeds, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }

}