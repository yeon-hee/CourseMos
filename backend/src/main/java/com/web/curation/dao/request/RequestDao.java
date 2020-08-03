package com.web.curation.dao.request;

import java.util.List;

import com.web.curation.model.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RequestDao extends JpaRepository<Request, String> {
    
    List<Request> findAllByEmail(String email);

    @Transactional
    void deleteByFollowerAndEmail(String follower, String email);
}