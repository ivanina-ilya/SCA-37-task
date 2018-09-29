package org.ivanina.course.sca.cinema.domain;

public enum UserRole {
    REGISTERED("REGISTERED"),
    BOOKING_MANAGER("BOOKING_MANAGER"),
    ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.role;
    }

}
