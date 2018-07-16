package org.ivanina.course.sca.cinema.domain;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class EventSchedule extends DomainObject {
    final static Logger logger = Logger.getLogger(EventSchedule.class.getName());

    private Event event;
    private Auditorium auditorium;
    private LocalDateTime startDateTime;

    public EventSchedule(Event event, Auditorium auditorium, LocalDateTime startDateTime) {
        this.event = event;
        this.auditorium = auditorium;
        this.startDateTime = startDateTime;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Override
    public int hashCode() {
        int result = event != null ? event.hashCode() : 0;
        result = 31 * result + (auditorium != null ? auditorium.hashCode() : 0);
        result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventSchedule otherEvent = (EventSchedule) o;

        if (event != null ? !event.equals(otherEvent.event) : otherEvent.event != null) return false;
        if (!auditorium.equals(otherEvent.auditorium)) return false;
        return startDateTime != null ? startDateTime.equals(otherEvent.startDateTime) : otherEvent.startDateTime == null;
    }
}
