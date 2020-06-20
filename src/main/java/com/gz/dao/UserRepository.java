package com.gz.dao;

import com.gz.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @authod wu
 * @date 2020/5/14 21:00
 */
public interface UserRepository extends JpaRepository<User,Long> {

    public User findByUsernameAndPassword(String username,String password);

    public User findByUsername(String username);
}
