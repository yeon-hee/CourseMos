package com.web.curation.dao.following;

import java.util.List;

import javax.transaction.Transactional;
import com.web.curation.model.following.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingDao extends JpaRepository<Following, String> {
    
    List<Following> findAllByEmail(String email);
    List<Following> findByFollower(String follower);
    Following findByEmail(String email);
    Following findByUserId(String userId);

    @Transactional
    void deleteByFollowerAndEmail(String follower, String email);
    @Transactional
    void deleteByFollowerAndUserId(String follower, String userId);

}