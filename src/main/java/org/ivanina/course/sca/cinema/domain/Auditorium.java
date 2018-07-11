package org.ivanina.course.sca.cinema.domain;

import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Auditorium extends DomainObject {
    @NonNull
    private String name;

    private Long numberOfSeats;

    private Set<Long> vipSeats = Collections.emptySet();

    public Auditorium(String name) {
        this.name = name;
    }

    public Auditorium(Long id, String name, Long numberOfSeats, Set<Long> vipSeats) {
        this.setId(id);
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Set<Long> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(Set<Long> vipSeats) {
        this.vipSeats = vipSeats;
    }

    public String vipSeatsToString() {
        return vipSeats == null ?
                "" :
                vipSeats.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(","));
    }

    public static Set<Long> vipSeatsParse(String seats) {
        return seats == null || seats.length() == 0 ?
                null :
                Arrays.stream(seats.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toSet());
    }

    public Set<Long> getSeats() {
        return LongStream.rangeClosed(1L, numberOfSeats)
                .boxed()
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auditorium that = (Auditorium) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Auditorium '" + name + "' with " + numberOfSeats + " seats" + (getId() != null ? " (ID: " + getId() + ")" : "");
    }
}
