package com.yangyang.springboot.dao;

import com.yangyang.springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by chenshunyang on 2017/5/22.
 */
@Mapper
public interface UserDao {
    List<User> findAllUser();
}
