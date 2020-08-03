package com.web.curation.dao.photo;

import java.util.List;

import com.web.curation.model.photo.Photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoDao extends JpaRepository<Photo, Integer> {
    List<Photo> findByFeedNo(int feedNo);
}