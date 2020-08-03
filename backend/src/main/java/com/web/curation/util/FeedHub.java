package com.web.curation.util;

import com.web.curation.dao.feed.FeedDao;
import com.web.curation.model.feed.Feed;

import org.springframework.beans.factory.annotation.Autowired;

public class FeedHub {
    @Autowired
    static FeedDao feedDao;

    public static FeedHub feedHub;

    public static FeedHub getInstance() {
        if(feedHub != null) {
            feedHub = new FeedHub();
        }
        return feedHub;
    }

    public static String getWriter(int feedNo) {
        Feed feed = feedDao.findByFeedNo(feedNo);

        return feed.getUserId();
    }
    
}