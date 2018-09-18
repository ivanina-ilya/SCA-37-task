package org.ivanina.course.sca.cinema.service.impl;

import org.apache.log4j.Logger;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.service.Discount;
import org.ivanina.course.sca.cinema.service.DiscountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class DiscountServiceImpl implements DiscountService {
    @Value("#{discounts.discountBirthdayInterval}")
    Integer discountBirthdayInterval;
    @Value("#{discounts.discountCountLimit}")
    Integer discountCountLimit;
    @Value("#{discounts.discountByLucky}")
    byte discountByLucky;
    @Value("#{discounts.discountLuckyFrequency}")
    byte discountLuckyFrequency;
    @Value("#{discounts.discountByCount}")
    byte discountByCount;
    @Value("#{discounts.discountByBirthday}")
    byte discountByBirthday;

    private Logger log = Logger.getLogger(getClass());


    @Override
    public BigDecimal calculatePrice(BigDecimal price, byte discount) {
        return price.subtract(price.multiply(new BigDecimal(discount)).divide(new BigDecimal(100)));
    }

    @Override
    public Discount getDiscount(@Nullable User user, EventSchedule eventSchedule, long numberOfTickets) {
        Set<Discount> discountList = new HashSet<>();

        if (discountByLucky > 0 && isLuckyWinnerDiscount()) {
            log.info("<<< YOU WINN!!! >>>");
            return new Discount(discountByLucky, true, false, false);
        }

        discountList.add(getDiscountByBirthday(user, eventSchedule.getStartDateTime()));
        discountList.add(getDiscountByCount(user, numberOfTickets));

        return discountList.stream()
                .filter(Objects::nonNull)
                .max(Discount::compareTo).orElse(new Discount((byte) 0, false, false, false));
    }

    @Override
    public Discount getDiscountByBirthday(@Nullable User user, LocalDateTime airDateTime) {
        if (user != null && user.getBirthday() != null &&
                user.getBirthday().isAfter(airDateTime.toLocalDate().minusDays(discountBirthdayInterval)) &&
                user.getBirthday().isBefore(airDateTime.toLocalDate().plusDays(discountBirthdayInterval)))
            return new Discount(discountByBirthday, false, true, false);
        return null;
    }

    @Override
    public Discount getDiscountByCount(@Nullable User user, long numberOfTickets) {
        if (numberOfTickets >= discountCountLimit || (user != null && (user.getTickets().size() + 1) % discountCountLimit == 0))
            return new Discount(discountByCount, false, false, true);
        return null;
    }

    @Override
    public Boolean isLuckyWinnerDiscount() {
        return new Random().nextInt(discountLuckyFrequency) == 1;
    }


}
