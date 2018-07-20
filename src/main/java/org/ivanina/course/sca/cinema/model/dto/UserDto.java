package org.ivanina.course.sca.cinema.model.dto;

import org.ivanina.course.sca.cinema.domain.User;

public class UserDto extends User {

    protected String password;

    protected String passwordConfirm;

    protected String emailConfirm;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmailConfirm() {
        return emailConfirm;
    }

    public void setEmailConfirm(String emailConfirm) {
        this.emailConfirm = emailConfirm;
    }
}
