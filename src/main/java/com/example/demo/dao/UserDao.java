package com.example.demo.dao;

import com.example.demo.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDao implements Dao<User>{
    private final SessionFactory factory;

    public UserDao(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public User get(Integer id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            return session.get(User.class, id);
        }
    }

    @Override
    public void insert(User entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }
}
