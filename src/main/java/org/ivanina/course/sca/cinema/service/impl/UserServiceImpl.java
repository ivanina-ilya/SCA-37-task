package org.ivanina.course.sca.cinema.service.impl;

import org.apache.log4j.Logger;
import org.ivanina.course.sca.cinema.dao.UserDao;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private Logger log = Logger.getLogger(getClass());

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;


    @Nullable
    @Override
    public User getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public Long save(User user) {
        return userDao.save(user);
    }

    @Override
    public Set<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public Long getCount() {
        return userDao.getCount();
    }


    @Override
    public User get(Long id) {
        return userDao.get(id);
    }

    @Override
    public Boolean remove(User entity) {
        return userDao.remove(entity);
    }

    @Override
    public Boolean remove(Long id) {
        return userDao.remove(id);
    }

    @Override
    public Long getNextIncrement() {
        return null;
    }

    @Override
    public void updateOrInsertUsers(List<User> users) {
        users.forEach(user -> {
            if (user.getId() != null && userDao.get(user.getId()) == null) {
                user.setId(null);
            }
            userDao.save(user);
        });
    }
}
