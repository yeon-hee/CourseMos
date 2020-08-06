package com.web.curation.controller.photo;

import com.web.curation.dao.photo.PhotoDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.photo.Photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    PhotoDao photoDao;

    @PostMapping()
    @ApiOperation(value = "피드 이미지 업로드하기")
    public Object writeFeeds(@RequestBody final Photo photo) {
        ResponseEntity response = null;

        try {
            photoDao.save(photo);

            response = new ResponseEntity<>(photo, HttpStatus.OK); 
        } catch(Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }
}