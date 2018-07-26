package org.ivanina.course.sca.cinema.service.impl;

import org.ivanina.course.sca.cinema.dao.UserAccountDao;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.domain.UserAccount;
import org.ivanina.course.sca.cinema.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Set;

public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    UserAccountDao userAccountDao;

    @Override
    public User getUser(UserAccount userAccount) {
        return userAccountDao.getUser(userAccount);
    }

    @Override
    public UserAccount getByUser(User user) {
        return userAccountDao.getByUser(user);
    }

    @Override
    public BigDecimal getMany(User user) {
        return userAccountDao.getMany(user);
    }

    @Override
    public Set<UserAccount> getAll() {
        return userAccountDao.getAll();
    }

    @Override
    public Long getCount() {
        return userAccountDao.getCount();
    }

    @Override
    public UserAccount get(Long id) {
        return userAccountDao.get(id);
    }

    @Override
    public Long save(UserAccount entity) {
        return userAccountDao.save(entity);
    }

    @Override
    public Boolean remove(UserAccount entity) {
        return userAccountDao.remove(entity);
    }

    @Override
    public Boolean remove(Long id) {
        return userAccountDao.remove(id);
    }

    @Override
    public Long getNextIncrement() {
        return null;
    }
}
