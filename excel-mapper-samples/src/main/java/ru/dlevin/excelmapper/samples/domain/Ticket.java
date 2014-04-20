package ru.dlevin.excelmapper.samples.domain;

/**
 * User: Dmitry Levin
 * Date: 10.03.14
 */
public class Ticket {
    private int number;

    private String title;

    public Ticket() {
    }

    public Ticket(int number, String title) {
        this.number = number;
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
