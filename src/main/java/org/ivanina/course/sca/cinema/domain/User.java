package org.ivanina.course.sca.cinema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.ivanina.course.sca.cinema.utils.LocalDateDeserializer;
import org.ivanina.course.sca.cinema.utils.LocalDateSerializer;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class User extends DomainObject {
    @NonNull
    @JacksonXmlProperty(localName = "firstName")
    protected String firstName;

    @JacksonXmlProperty(localName = "lastName")
    protected String lastName;

    @NonNull
    @JacksonXmlProperty(localName = "email")
    protected String email;

    @JacksonXmlProperty(localName = "birthday")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    protected LocalDate birthday;

    @JsonIgnore
    protected NavigableSet<Ticket> tickets = new TreeSet<>();

    @JsonIgnore
    protected String passwordHash;

    protected List<UserRole> roles;

    public User() {
    }

    public User(String firstName, String email) {
        this.firstName = firstName;
        this.email = email;
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NavigableSet<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(NavigableSet<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTickets(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public String rolesToString(){
        if(roles == null) return "";
        return roles.stream().map(UserRole::toString).collect(Collectors.joining(","));
    }

    public List<UserRole> rolesValueOf(String roles){
        if(roles == null || roles.length() == 0) return null;
        return Arrays.stream( roles.split(",") ).map(UserRole::valueOf).collect(Collectors.toList());
    }

    public void setRoles(String roles){
        this.setRoles( rolesValueOf(roles) );
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (!email.equals(other.email)) { //@NonNull
            return false;
        }
        if (!firstName.equals(other.firstName)) { //@NonNull
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s, ID: %d)", firstName, lastName, email, getId());
    }

}
