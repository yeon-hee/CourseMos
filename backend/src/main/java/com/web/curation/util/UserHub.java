package com.web.curation.util;

import java.util.Optional;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.user.User;

import org.springframework.beans.factory.annotation.Autowired;

public class UserHub {
    @Autowired
    static UserDao userDao;

    public static UserHub userHub;

    public static UserHub getInstance() {
        if(userHub == null) {
            userHub = new UserHub();
        }

        return userHub;
    }

    public static String getUserIdByEmail(String email) {
        User user = userDao.findByEmail(email);

        return user.getUserId();
    }

    public static String getEmailByUserId(String userId) {
        User user = userDao.findByUserId(userId).get();

        return user.getEmail();
    }

}