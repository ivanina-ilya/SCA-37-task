package org.ivanina.course.sca.cinema.service;

public class Discount implements Comparable {
    private Byte percent;
    private Boolean byWinner = false;
    private Boolean byBirthday = false;
    private Boolean byCount = false;

    public Discount() {
    }

    public Discount(byte percent) {
        this.percent = percent;
    }

    public Discount(byte percent, Boolean byWinner, Boolean byBirthday, Boolean byCount) {
        this.percent = percent;
        this.byWinner = byWinner;
        this.byBirthday = byBirthday;
        this.byCount = byCount;
    }

    public byte getPercent() {
        return percent;
    }

    public void setPercent(byte percent) {
        this.percent = percent;
    }

    public Boolean getByWinner() {
        return byWinner;
    }

    public Boolean isByWinner() {
        return byWinner == null ? false : byWinner;
    }

    public void setByWinner(Boolean byWinner) {
        this.byWinner = byWinner;
    }

    public Boolean getByBirthday() {
        return byBirthday;
    }

    public Boolean isByBirthday() {
        return byBirthday == null ? false : byBirthday;
    }

    public void setByBirthday(Boolean byBirthday) {
        this.byBirthday = byBirthday;
    }

    public Boolean getByCount() {
        return byCount;
    }

    public Boolean isByCount() {
        return byCount == null ? false : byCount;
    }

    public void setByCount(Boolean byCount) {
        this.byCount = byCount;
    }


    @Override
    public int compareTo(Object o) {
        return percent.compareTo(((Discount) o).percent);
    }
}
