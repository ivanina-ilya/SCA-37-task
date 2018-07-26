package org.ivanina.course.sca.cinema.dao;


import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.domain.UserAccount;

import java.math.BigDecimal;

public interface UserAccountDao extends Dao<UserAccount> {
    User getUser(UserAccount userAccount);

    UserAccount getByUser(User user);

    BigDecimal getMany(User user);
}
