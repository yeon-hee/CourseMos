package com.web.curation.dao.countinfo;

import com.web.curation.model.countinfo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountinfoDao extends JpaRepository<Countinfo, String> {

    Countinfo findByEmail(String email);
}
