package org.ivanina.course.sca.cinema.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public class UserAccount extends DomainObject {

    @JacksonXmlProperty(localName = "money")
    private BigDecimal money;

    @NonNull
    private Long userId;

    public UserAccount(Long userId) {
        this.userId = userId;
    }

    public UserAccount(BigDecimal money, Long userId) {
        this.money = money;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
