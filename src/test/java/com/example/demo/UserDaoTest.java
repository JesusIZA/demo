package com.example.demo;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserDaoTest {

    @Test
    void test1() {
        var factory = new Configuration().configure().buildSessionFactory();
        var userDao = new UserDao(factory);
        var user = new User();
        user.setFirstName("test1");
        user.setLastName("test1");
        user.setEmail("test1@t.est");
        user.setPassword("test");
        userDao.insert(user);

        System.out.println(userDao.get(user.getId()));
    }
}
