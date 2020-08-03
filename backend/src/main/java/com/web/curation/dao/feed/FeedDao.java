package com.web.curation.dao.feed;

import java.util.List;

import com.web.curation.model.feed.Feed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedDao extends JpaRepository<Feed, Integer> {

    Feed findByFeedNo(int feedNo);

    List<Feed> findAllByUserId(String userId);
}