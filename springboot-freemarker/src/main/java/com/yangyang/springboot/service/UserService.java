package com.yangyang.springboot.service;

import com.yangyang.springboot.dao.UserDao;
import com.yangyang.springboot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User> findAllUser() {
        return userDao.findAllUser();
    }
}
