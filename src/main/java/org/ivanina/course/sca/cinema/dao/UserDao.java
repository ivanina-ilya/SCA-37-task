package org.ivanina.course.sca.cinema.dao;

import org.ivanina.course.sca.cinema.domain.User;

public interface UserDao extends Dao<User> {
    User getByEmail(String email);
}
