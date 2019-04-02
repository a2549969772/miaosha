package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired(required = false)
    UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx(){
        User user=new User();
        user.setId(2);
        user.setName("jack");
        userDao.insert(user);

        User user1=new User();
        user1.setId(3);
        user1.setName("涨三");
        userDao.insert(user1);
        return true;
    }
}
