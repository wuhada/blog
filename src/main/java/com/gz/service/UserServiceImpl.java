package com.gz.service;

import com.gz.dao.UserRepository;
import com.gz.po.User;
import com.gz.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @authod wu
 * @date 2020/5/14 21:09
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String name, String password) {
        User user = userRepository.findByUsernameAndPassword(name, MD5Utils.code(password));
        return user;
    }

    @Override
    public User saveUser(User user) {
        String password = user.getPassword();
        user.setPassword(MD5Utils.code(password));
        User u = userRepository.findByUsername(user.getUsername());
        if (u != null) {
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getOne(id);
    }
}
