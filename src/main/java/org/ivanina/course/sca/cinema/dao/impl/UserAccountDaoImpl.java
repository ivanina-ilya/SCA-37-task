package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.DaoAbstract;
import org.ivanina.course.sca.cinema.dao.UserAccountDao;
import org.ivanina.course.sca.cinema.dao.UserDao;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.domain.UserAccount;
import org.ivanina.course.sca.cinema.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAccountDaoImpl extends DaoAbstract<UserAccount> implements UserAccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    public UserAccountDaoImpl(String table) {
        super(table);
    }

    @Override
    public User getUser(UserAccount userAccount) {
        return userDao.get(userAccount.getUserId());
    }

    @Override
    public UserAccount getByUser(User user) {
        return getEntityByQuery("SELECT * FROM " + table + " WHERE user_id=?", new Object[]{user.getId()});
    }

    @Override
    public BigDecimal getMany(User user) {
        return getByUser(user).getMoney();
    }

    @Override
    public Long save(UserAccount entity) {
        int rows = 0;
        if (entity.getId() == null) {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            rows = jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + table + " (user_id, money) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                statement.setLong(1, entity.getUserId());
                ServiceUtil.statementSetBigDecimalOrNull(statement, 2, entity.getMoney());
                return statement;
            }, holder);
            entity.setId(holder.getKey().longValue());
        } else {
            rows = jdbcTemplate.update(
                    "UPDATE " + table + " SET user_id=?, money=? WHERE id=?",
                    entity.getUserId(),
                    entity.getMoney(),
                    entity.getId());
        }
        return rows == 0 ? null : entity.getId();
    }

    public UserAccount mapRow2(ResultSet resultSet, UserAccount ua) throws SQLException {
        if (resultSet == null) return null;
        if (ua == null) ua = new UserAccount(resultSet.getBigDecimal("money"), resultSet.getLong("id"));
        else {
            ua.setMoney(resultSet.getBigDecimal("money"));
            ua.setUserId(resultSet.getLong("id"));
        }
        return ua;
    }
}
