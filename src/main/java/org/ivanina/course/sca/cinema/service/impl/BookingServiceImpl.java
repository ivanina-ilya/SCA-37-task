package org.ivanina.course.sca.cinema.service.impl;

import org.ivanina.course.sca.cinema.dao.TicketDao;
import org.ivanina.course.sca.cinema.domain.*;
import org.ivanina.course.sca.cinema.service.BookingService;
import org.ivanina.course.sca.cinema.service.DiscountService;
import org.ivanina.course.sca.cinema.service.EventService;
import org.ivanina.course.sca.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("discountService")
    private DiscountService discountService;

    @Autowired
    @Qualifier("eventService")
    private EventService eventService;

    @Autowired
    @Qualifier("ticketDao")
    private TicketDao ticketDao;


    @Override
    public BigDecimal getTicketsPrice(@Nullable User user, EventSchedule eventSchedule, Set<Long> seats) {
        BigDecimal basePrice = discountService.calculatePrice(
                eventSchedule.getEvent().getPrice(),
                discountService.getDiscount(user, eventSchedule, seats.size()).getPercent()
        );

        basePrice = basePrice.setScale(2, RoundingMode.HALF_UP);
        return basePrice;
    }


    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(EventSchedule eventSchedule) {
        return ticketDao.getTicketsByEvent(eventSchedule);
    }

    @Override
    public Set<Ticket> getPurchasedTicketsByUserForEvent(User user, EventSchedule eventSchedule) {
        return ticketDao.getTicketsByUserForEvent(user, eventSchedule);
    }

    public Set<Ticket> getTicketsByUserOnHand(User user) {
        return user.getTickets();
    }

    public Set<Ticket> getTicketsByUserOnHand(String email) {
        return getTicketsByUserOnHand(userService.getUserByEmail(email));
    }

    @Override
    public Set<Ticket> getTicketsByUser(User user) {
        return ticketDao.getTicketsByUser(user.getId());
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {
        tickets.forEach(this::purchaseValidate);
        tickets.forEach(ticket -> {
            ticket.setPrice(
                    discountService.calculatePrice(
                            ticket.getEventSchedule().getEvent().getPrice(),
                            discountService.getDiscount(ticket.getUser(), ticket.getEventSchedule(), tickets.size()).getPercent()
                    )
            );
            ticketDao.save(ticket);
        });
    }


    @Override
    public void bookTickets(@Nullable User user, EventSchedule eventSchedule, Long seat) {
        if (user == null)
            throw new IllegalArgumentException("The User does not exist");
        if (eventSchedule.getEvent() == null)
            throw new IllegalArgumentException("The event not available [ID: " + eventSchedule.getEvent() + "]");

        bookTickets(new HashSet<>(Collections.singletonList(new Ticket(user, eventSchedule, seat))));
    }

    @Override
    public void bookTickets(@Nullable String userEmail, EventSchedule eventSchedule, Long seat) {
        User user = userService.getUserByEmail(userEmail);
        if (user == null)
            throw new IllegalArgumentException("The User with email '" + userEmail + "' does not exist");
        bookTickets(user, eventSchedule, seat);
    }


    @Override
    public Ticket bookTicket(@Nullable User user, @NonNull EventSchedule eventSchedule, Long seat) {
        Ticket ticket = new Ticket(user, eventSchedule, seat);
        ticket.setPrice(
                discountService.calculatePrice(eventSchedule.getEvent().getPrice(),
                        discountService.getDiscount(user, eventSchedule, 1L).getPercent())
        );
        purchaseValidate(ticket);
        ticket.setId(ticketDao.save(ticket));
        return ticket;
    }

    @Override
    public Ticket bookTicket(@Nullable String userEmail, @NonNull EventSchedule eventSchedule, Long seat) {
        User user = userService.getUserByEmail(userEmail);
        if (user == null)
            throw new IllegalArgumentException("The User with email '" + userEmail + "' does not exist");

        return bookTicket(user, eventSchedule, seat);
    }

    @Override
    public Boolean purchaseValidate(Ticket ticket) {
        Event event = eventService.get(ticket.getEventSchedule().getEvent().getId());
        if (event == null)
            throw new IllegalArgumentException("The event not available [" + ticket.getEventSchedule().getEvent() + "]");

        if (eventService.getAvailableEventDate(event).stream()
                .noneMatch(date -> ticket.getDateTime().isEqual(date)))
            throw new IllegalArgumentException("The date [" + ticket.getDateTime() + "] for event not available [" + ticket.getEventSchedule().getEvent() + "]");

        if (ticketDao.getTicketsByEvent(ticket.getEventSchedule()).stream()
                .anyMatch(storeTicket -> storeTicket.getSeat().equals(ticket.getSeat())))
            throw new IllegalArgumentException("The seat [" + ticket.getSeat() + "] for event not available [" + ticket.getEventSchedule().getEvent() + "]");
        return true;
    }


    @Override
    public Set<Ticket> getPurchasedTicketsForUser(User user) {
        return ticketDao.getTicketsByUser(user.getId());
    }

    @Override
    public Set<Long> getAvailableSeats(EventSchedule eventSchedule) {
        Auditorium auditorium = eventSchedule.getAuditorium();
        if (auditorium == null)
            throw new IllegalArgumentException("No Auditorium assigned");
        Set<Ticket> tickets = getPurchasedTicketsForEvent(eventSchedule);
        if (tickets == null || tickets.size() == 0) return auditorium.getSeatsSet();

        List<Long> boughSeats = tickets.stream()
                .map(Ticket::getSeat)
                .collect(Collectors.toList());

        return auditorium.getSeatsSet().stream()
                .filter(seat -> !boughSeats.contains(seat))
                .collect(Collectors.toSet());
    }

    @Override
    public Boolean isAvailableSeats(EventSchedule eventSchedule, Long seat) {
        if(eventSchedule == null) throw new IllegalArgumentException("Dos not exist Schedule for Event");
        return getAvailableSeats(eventSchedule).contains(seat);
    }



    @Override
    public Set<Ticket> getPurchasedTicketsByUserForEvent(User user, Event event, LocalDateTime dateTime) {
        return null;
    }

    @Override
    public Boolean isAvailableSeats(Event event, LocalDateTime dateTime, Auditorium auditorium, Long seat) {
        return null;
    }
}
