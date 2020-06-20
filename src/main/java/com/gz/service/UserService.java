package com.gz.service;

import com.gz.po.User;

/**
 * @authod wu
 * @date 2020/5/14 20:59
 */
public interface UserService {

    User checkUser(String name,String password);

    User saveUser(User user);

    User getUser(Long id);
}
