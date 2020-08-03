
package com.web.curation.dao.user;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String> {
    User findByEmail(String email);
    //User findByUserId(String userId);

    List<User> findByUserIdLike(String userId);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findByUserId(String userId);
}
