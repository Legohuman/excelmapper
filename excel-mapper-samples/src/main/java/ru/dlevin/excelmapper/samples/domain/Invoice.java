package ru.dlevin.excelmapper.samples.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Dmitry Levin
 * Date: 16.05.14
 */
public class Invoice {

    private String number;

    private Date date;

    private List<InvoiceItem> items = new ArrayList<InvoiceItem>();

    public Invoice() {
    }

    public Invoice(String number, Date date, List<InvoiceItem> items) {
        this.number = number;
        this.date = date;
        this.items = items;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Invoice{");
        sb.append("number='").append(number).append('\'');
        sb.append(", date=").append(date);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}
