package com.web.curation.dao.comment;

import java.util.List;

import com.web.curation.model.comment.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CommentDao extends JpaRepository<Comment, Integer> {
    List<Comment> findByFeedNo(int feedNo);
    int countByFeedNo(int feedNo);
    @Transactional
    void deleteByFeedNoAndCommentNo(int feedNo, int commentNo);
}