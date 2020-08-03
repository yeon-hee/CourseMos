package com.web.curation.util;

import com.web.curation.dao.alert.AlertDao;
import com.web.curation.dao.feed.FeedDao;
import com.web.curation.model.alert.Alert;
import com.web.curation.model.feed.Feed;

import org.springframework.beans.factory.annotation.Autowired;

public class AlertHub {

    @Autowired
    static AlertDao alertDao;
    @Autowired
    static FeedDao feedDao;
    
    public static AlertHub alertHub;

    public static AlertHub getInstance() {
        if(alertHub == null) {
            alertHub = new AlertHub();
        }

        return alertHub;
    }

    public static void callLikeAlert(int feedNo, String from) {
        Alert alert = new Alert();
        alert.setUserId(getTarget(feedNo));
        alert.setMessage(from+"님이 좋아요를 눌렀습니다.");

        alertDao.save(alert);
    }

    public static void callCommentAlert(int feedNo, String writer) {
        Alert alert = new Alert();
        alert.setUserId(getTarget(feedNo));
        alert.setMessage(writer+"님이 댓글을 작성했습니다.");

        alertDao.save(alert);
    }

    public static String getTarget(int feedNo) {
        Feed feed = feedDao.findByFeedNo(feedNo);
        return feed.getUserId();
    }
}