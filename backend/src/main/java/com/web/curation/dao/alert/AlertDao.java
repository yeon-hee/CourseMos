package com.web.curation.dao.alert;

import java.util.List;

import com.web.curation.model.alert.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertDao extends JpaRepository<Alert, Integer> {

    List<Alert> findAllByUserId(String userId);
    Alert findByAlertNo(int alertNo);
    
}