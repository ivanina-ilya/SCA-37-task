package org.ivanina.course.sca.cinema.domain;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public class Event extends DomainObject {
    @NonNull
    private String name;

    private BigDecimal price;

    private Long duration;

    private EventRating rating;



    public Event(String name) {
        this.name = name;
        this.rating = EventRating.MID;
    }

    public Event(Long id, String name, BigDecimal price, Long duration, EventRating rating) {
        this.setId(id);
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.rating = rating;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return name.equals(event.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Event '%s' with %s rating; (ID: %d)",
                name,
                rating.name(),
                getId());
    }
}
