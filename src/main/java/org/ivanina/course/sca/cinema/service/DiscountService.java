package org.ivanina.course.sca.cinema.service;

import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface DiscountService {
    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     *
     * @param user            User that buys tickets. Can be <code>null</code>
     * @param event           Event that tickets are bought for
     * @param startDateTime     The date and time event will be aired
     * @param numberOfTickets Number of tickets that user buys
     * @return discount value from 0 to 100
     */
    byte getDiscount(@Nullable User user, @NonNull Event event, @NonNull LocalDateTime startDateTime, long numberOfTickets);
    byte getDiscountByBirthday(@Nullable User user, LocalDateTime startDateTime);
    byte getDiscountByCount(@Nullable User user, long numberOfTickets);
    Boolean isLuckyWinnerDiscount();
    BigDecimal calculatePrice(BigDecimal price, byte discount);
}
