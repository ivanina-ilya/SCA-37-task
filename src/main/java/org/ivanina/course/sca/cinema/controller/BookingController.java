package org.ivanina.course.sca.cinema.controller;

import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.domain.Ticket;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private DiscountService discountService;

    @GetMapping("/{scheduleId}")
    public ModelAndView bookingEvent(
            @PathVariable Long scheduleId
    ){
        ModelAndView model = new ModelAndView("booking/book");
        EventSchedule eventSchedule = eventService.getEventSchedule(scheduleId);
        model.addObject("users", userService.getAll());
        model.addObject("eventSchedule", eventSchedule);
        model.addObject("seats",
                LongStream.rangeClosed(1, eventSchedule.getAuditorium().getSeats() ).boxed()
                        .collect(
                                Collectors.toMap(
                                        Long::new,
                                        p-> eventSchedule.getAuditorium().getVipSeats() != null && eventSchedule.getAuditorium().getVipSeats().contains(p),
                                        (p,q)-> p)
                        )
        );
        model.addObject("availableSeats", bookingService.getAvailableSeats(eventSchedule));
        model.addObject("price", eventSchedule.getEvent().getPrice());
        return model;
    }

    @PostMapping("/{scheduleId}")
    public ModelAndView bookEvent(
            @PathVariable Long scheduleId,
            @RequestParam("user") Long userId,
            @RequestParam("seat") Long seat
    ){
        ModelAndView model = new ModelAndView("booking/bookConfirmation");

        EventSchedule eventSchedule = eventService.getEventSchedule(scheduleId);
        User user = userService.get(userId);
        Discount discount = discountService.getDiscount(user, eventSchedule, 1L);
        BigDecimal newPrice = discountService.calculatePrice(eventSchedule.getEvent().getPrice(),discount.getPercent());

        Ticket ticket = bookingService.preBookTicket(user, eventSchedule, seat, newPrice);
        model.addObject("ticket", ticket);
        model.addObject("discount", discount);
        return model;
    }



}
