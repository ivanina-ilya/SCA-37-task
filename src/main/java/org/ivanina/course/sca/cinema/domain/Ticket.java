package org.ivanina.course.sca.cinema.domain;

import org.ivanina.course.sca.cinema.service.Util;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class Ticket extends DomainObject implements Comparable<Ticket> {
    final static Logger logger = Logger.getLogger(Ticket.class.getName());

    private User user;

    @NonNull
    private EventSchedule eventSchedule;

    private Long seat;

    private BigDecimal price;

    public Ticket(@NonNull User user, @NonNull EventSchedule eventSchedule, Long seat) {
        this.user = user;
        this.eventSchedule = eventSchedule;
        this.seat = seat;
    }

    public Ticket(Long id, @NonNull User user, @NonNull EventSchedule eventSchedule, Long seat, BigDecimal price) {
        this.setId(id);
        this.user = user;
        this.eventSchedule = eventSchedule;
        this.seat = seat;
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return eventSchedule.getStartDateTime().withNano(0);
    }


    public Long getSeat() {
        return seat;
    }

    public void setSeat(Long seat) {
        this.seat = seat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public EventSchedule getEventSchedule() {
        return eventSchedule;
    }

    public void setEventSchedule(EventSchedule eventSchedule) {
        this.eventSchedule = eventSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (user != null ? !user.equals(ticket.user) : ticket.user != null) return false;
        if (eventSchedule != null && !eventSchedule.equals(ticket.eventSchedule)) return false;
        return seat != null ? seat.equals(ticket.seat) : ticket.seat == null;
    }

    @Override
    public int hashCode() {

        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (eventSchedule != null ? eventSchedule.hashCode() : 0); //eventSchedule.hashCode();
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        return result;
    }


    @Override
    public int compareTo(Ticket o) {
        if (o == null) {
            return 1;
        }
        int result = getDateTime().compareTo(o.getDateTime());

        if (result == 0) {
            result = eventSchedule.getEvent().getName().compareTo(o.eventSchedule.getEvent().getName());
        }
        if (result == 0) {
            result = Long.compare(seat, o.getSeat());
        }
        return result;
    }

    @Override
    public String toString() {
        return "Ticket for data: " + Util.localDateTimeFormatterDay(eventSchedule.getStartDateTime()) + ", at time: " +
                Util.localDateTimeFormatterTime(eventSchedule.getStartDateTime()) + "\n" +
                eventSchedule.getEvent() + "\n" +
                "Auditorium: " + eventSchedule.getAuditorium() + "\n" +
                "Seat: " + seat + "\n" +
                "For user: " + user + "\n" +
                "--------------------------\n";
    }
}
