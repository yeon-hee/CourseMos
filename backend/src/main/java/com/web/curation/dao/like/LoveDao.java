package com.web.curation.dao.like;

import javax.transaction.Transactional;

import com.web.curation.model.like.Love;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoveDao extends JpaRepository<Love, String> {

    Love findByFeedNoAndUserId(int feedNo, String userId);
    Love findByUserId(String userId);
    int countByFeedNo(int feedNo);
    @Transactional
    void deleteByFeedNoAndUserId(int feedNo, String userId);
}