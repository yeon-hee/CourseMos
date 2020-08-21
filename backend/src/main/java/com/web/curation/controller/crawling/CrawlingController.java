package com.web.curation.controller.crawling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
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
  static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36";
  // static String referer = "https://place.map.kakao.com/";
  static String referer = "https://t1.daumcdn.net/kakaomapweb/place";
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
  public Object getDetail(@PathVariable final String number) throws Exception {
  
        String url = "https://place.map.kakao.com/m/main/v/"; // 크롤링 url로 바꾸기
        url += number;
        System.out.println(url);

        try{
          Document doc = Jsoup.connect(url)
                              .userAgent(userAgent)
                              .referrer(referer)
                              .method(Method.GET)
                              .header("cookie", "webid=c2c93af0b35e410f9be8edbafe6984d1; webid_ts=1594622017569; _TI_NID=iok9mjEFfnWoNqMjDA5hhak447NtYgPjswSxS6f9Ywq05E4Y1jEio1jap6MzK/w2Lr1c+/yuPRFFy64yQxwL5w==; kd_lang=ko; _ga=GA1.2.317939268.1595862216; _kadu=omwGp6Ih5w7LXn1u_1596439423573; _kdt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZlbG9wZXJJZCI6MjI2OTA0LCJhY2NvdW50SWQiOjU1MDE4MjcwLCJlbWFpbCI6Inhvcm9seDI5N0BuYXZlci5jb20iLCJ0b2tlbiI6IjhjNGI1MWRmZmE0MGFhZWI0MjUwMDA4NGI2Nzc4MTk2MmUzNmQ2MTU5MDEzOTU5NTE0ZGIwOTRjZmJjYTZiYTcifQ.Jf1J3r9AQ9WMeJ74VPVHHGKoHNDF-zl874X2VmhWyT0; _gid=GA1.2.235908630.1597946992; TIARA=_sZHB3eTGoqUbsegv9nRsDj.en_g-v6pJBVhpLXkDhCWq9wTwvf4o8cjcwrn4YrugyGFSktO9DqlB9FtNAghbbpef4PG7lEW")
                              .header("scheme", "https")
                              .header("cache-control", "max-age=7200")
                              .header("Server", "nginx")
                              .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                              .header("Accept-Encoding", "gzip, deflate, br")
                              .ignoreContentType(true)
                              .followRedirects(true)
                              .execute().parse();
          String str = doc.text();
          System.out.println(str);

          JSONParser jsonParser = new JSONParser();
          JSONObject jsonObject = (JSONObject) jsonParser.parse(str);
          JSONObject arr = (JSONObject) jsonObject.get("basicInfo");
          return arr;
        }
        catch (Exception e) {
          e.printStackTrace();
          return e;
        }
  }

  @GetMapping("/menu/{number}")
  @ApiOperation(value = "메뉴 정보 크롤링하기")
  public Object getMenu(@PathVariable final String number) throws IOException, ParseException {
  
     
        String url = "https://place.map.kakao.com/m/main/v/"; // 크롤링 url로 바꾸기
        url += number;
        System.out.println(url);
        Document doc = Jsoup.connect(url)
                            .userAgent(userAgent)
                            .referrer(referer)
                            .method(Method.GET)
                            .header("cookie", "webid=c2c93af0b35e410f9be8edbafe6984d1; webid_ts=1594622017569; _TI_NID=iok9mjEFfnWoNqMjDA5hhak447NtYgPjswSxS6f9Ywq05E4Y1jEio1jap6MzK/w2Lr1c+/yuPRFFy64yQxwL5w==; kd_lang=ko; _ga=GA1.2.317939268.1595862216; _kadu=omwGp6Ih5w7LXn1u_1596439423573; _kdt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZlbG9wZXJJZCI6MjI2OTA0LCJhY2NvdW50SWQiOjU1MDE4MjcwLCJlbWFpbCI6Inhvcm9seDI5N0BuYXZlci5jb20iLCJ0b2tlbiI6IjhjNGI1MWRmZmE0MGFhZWI0MjUwMDA4NGI2Nzc4MTk2MmUzNmQ2MTU5MDEzOTU5NTE0ZGIwOTRjZmJjYTZiYTcifQ.Jf1J3r9AQ9WMeJ74VPVHHGKoHNDF-zl874X2VmhWyT0; _gid=GA1.2.235908630.1597946992; TIARA=_sZHB3eTGoqUbsegv9nRsDj.en_g-v6pJBVhpLXkDhCWq9wTwvf4o8cjcwrn4YrugyGFSktO9DqlB9FtNAghbbpef4PG7lEW")
                            .header("scheme", "https")
                            .header("cache-control", "max-age=7200")
                            .header("Server", "nginx")
                            .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                            .header("Accept-Encoding", "gzip, deflate, br")
                            .ignoreContentType(true)
                            .followRedirects(true)
                            .execute().parse();
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
        Document doc = Jsoup.connect(url)
                            .userAgent(userAgent)
                            .referrer(referer)
                            .method(Method.GET)
                            .header("scheme", "https")
                            .header("cookie", "webid=c2c93af0b35e410f9be8edbafe6984d1; webid_ts=1594622017569; _TI_NID=iok9mjEFfnWoNqMjDA5hhak447NtYgPjswSxS6f9Ywq05E4Y1jEio1jap6MzK/w2Lr1c+/yuPRFFy64yQxwL5w==; kd_lang=ko; _ga=GA1.2.317939268.1595862216; _kadu=omwGp6Ih5w7LXn1u_1596439423573; _kdt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZlbG9wZXJJZCI6MjI2OTA0LCJhY2NvdW50SWQiOjU1MDE4MjcwLCJlbWFpbCI6Inhvcm9seDI5N0BuYXZlci5jb20iLCJ0b2tlbiI6IjhjNGI1MWRmZmE0MGFhZWI0MjUwMDA4NGI2Nzc4MTk2MmUzNmQ2MTU5MDEzOTU5NTE0ZGIwOTRjZmJjYTZiYTcifQ.Jf1J3r9AQ9WMeJ74VPVHHGKoHNDF-zl874X2VmhWyT0; _gid=GA1.2.235908630.1597946992; TIARA=_sZHB3eTGoqUbsegv9nRsDj.en_g-v6pJBVhpLXkDhCWq9wTwvf4o8cjcwrn4YrugyGFSktO9DqlB9FtNAghbbpef4PG7lEW")
                            .header("cache-control", "max-age=7200")
                            .header("Server", "nginx")
                            .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                            .header("Accept-Encoding", "gzip, deflate, br")
                            .ignoreContentType(true)
                            .followRedirects(true)
                            .execute().parse();
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
        Document doc = Jsoup.connect(url)
                            .userAgent(userAgent)
                            .referrer(referer)
                            .method(Method.GET)
                            .header("cookie", "webid=c2c93af0b35e410f9be8edbafe6984d1; webid_ts=1594622017569; _TI_NID=iok9mjEFfnWoNqMjDA5hhak447NtYgPjswSxS6f9Ywq05E4Y1jEio1jap6MzK/w2Lr1c+/yuPRFFy64yQxwL5w==; kd_lang=ko; _ga=GA1.2.317939268.1595862216; _kadu=omwGp6Ih5w7LXn1u_1596439423573; _kdt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkZXZlbG9wZXJJZCI6MjI2OTA0LCJhY2NvdW50SWQiOjU1MDE4MjcwLCJlbWFpbCI6Inhvcm9seDI5N0BuYXZlci5jb20iLCJ0b2tlbiI6IjhjNGI1MWRmZmE0MGFhZWI0MjUwMDA4NGI2Nzc4MTk2MmUzNmQ2MTU5MDEzOTU5NTE0ZGIwOTRjZmJjYTZiYTcifQ.Jf1J3r9AQ9WMeJ74VPVHHGKoHNDF-zl874X2VmhWyT0; _gid=GA1.2.235908630.1597946992; TIARA=_sZHB3eTGoqUbsegv9nRsDj.en_g-v6pJBVhpLXkDhCWq9wTwvf4o8cjcwrn4YrugyGFSktO9DqlB9FtNAghbbpef4PG7lEW")
                            .header("scheme", "https")
                            .header("cache-control", "max-age=7200")
                            .header("Server", "nginx")
                            .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                            .header("Accept-Encoding", "gzip, deflate, br")
                            .ignoreContentType(true)
                            .followRedirects(true)
                            .execute().parse();
        String str = doc.text();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(str);

        JSONObject arr = (JSONObject) jsonObject.get("comment");
        return arr;
  }
}