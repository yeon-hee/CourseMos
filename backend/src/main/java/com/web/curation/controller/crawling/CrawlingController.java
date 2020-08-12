package com.web.curation.controller.crawling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.web.curation.dao.course.CourseDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/crawling")
public class CrawlingController {

  @Autowired
  CourseDao courseDao;

  @Autowired
  JwtService jwtService;

  @GetMapping("/{tradeName}")
  @ApiOperation(value = "코스 정보 받아오기")
  public Object getCourse(@PathVariable final String tradeName) throws IOException {

    String name = "";
    for (int i = 0; i < tradeName.length(); i++) {
      if (tradeName.charAt(i) == ' ')
        name += '+';
      else
        name += tradeName.charAt(i);
    }

    String url = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="; // 크롤링 url로 바꾸기
    url += name;
    System.out.println(url);
    Document doc = Jsoup.connect(url).get();
    Elements element = doc.select("div.thumb_area a>img:nth-child(1)");
    Attributes result = element.get(0).attributes();
    System.out.println(result);
    System.out.println(result.get("src"));

    return result.get("src");
  }

  @GetMapping("/detail/{number}")
  @ApiOperation(value = "상세 정보 크롤링하기")
  public Object getDetail(@PathVariable final String number) throws IOException, ParseException {
  
     
        String url = "https://place.map.kakao.com/m/main/v/"; // 크롤링 url로 바꾸기
        url += number;
        System.out.println(url);
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String str = doc.text();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(str);

        JSONObject arr = (JSONObject) jsonObject.get("basicInfo");
        return arr;
  }

  @GetMapping("/menu/{number}")
  @ApiOperation(value = "메뉴 정보 크롤링하기")
  public Object getMenu(@PathVariable final String number) throws IOException, ParseException {
  
     
        String url = "https://place.map.kakao.com/m/main/v/"; // 크롤링 url로 바꾸기
        url += number;
        System.out.println(url);
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String str = doc.text();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(str);

        JSONObject arr = (JSONObject) jsonObject.get("menuInfo");
        return arr;
  }

  @GetMapping("/blog/{number}")
  @ApiOperation(value = "블로그 정보 크롤링하기")
  public Object getBlog(@PathVariable final String number) throws IOException, ParseException {
     
        String url = "https://place.map.kakao.com/m/main/v/"; // 크롤링 url로 바꾸기
        url += number;
        System.out.println(url);
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String str = doc.text();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(str);

        JSONObject arr = (JSONObject) jsonObject.get("blogReview");
        return arr;
  }

  @GetMapping("/review/{number}")
  @ApiOperation(value = "블로그 정보 크롤링하기")
  public Object getReview(@PathVariable final String number) throws IOException, ParseException {
     
        String url = "https://place.map.kakao.com/m/main/v/"; // 크롤링 url로 바꾸기
        url += number;
        System.out.println(url);
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String str = doc.text();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(str);

        JSONObject arr = (JSONObject) jsonObject.get("comment");
        return arr;
  }
}