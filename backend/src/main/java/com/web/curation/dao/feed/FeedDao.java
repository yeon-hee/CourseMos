package com.web.curation.dao.feed;

import java.util.List;

import com.web.curation.model.feed.Feed;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedDao extends JpaRepository<Feed, Integer> {

    Feed findByFeedNo(int feedNo);

    List<Feed> findAllByUserId(String userId);
    
    @Query(value="select distinct(f) from Feed as f left join Course as c on f.feedNo = c.feedNo where c.tradeName like concat('%',:search,'%') or c.roadAddress like concat('%', :search, '%')")
    List<Feed> findAllBySearch(String search, Pageable request);

    @Query(value="select distinct(f) from Feed as f left join Course as c on f.feedNo = c.feedNo where c.categoryName like concat('%',:category,'%')")
    List<Feed> findAllByCategory(String category, Pageable request);
}