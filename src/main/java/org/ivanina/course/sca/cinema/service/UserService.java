package org.ivanina.course.sca.cinema.service;

import org.ivanina.course.sca.cinema.dao.Dao;
import org.ivanina.course.sca.cinema.domain.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface UserService extends Dao<User> {
    @Nullable
    User getUserByEmail(@NonNull String email);

    void updateOrInsertUsers(@NonNull List<User> users);
}
