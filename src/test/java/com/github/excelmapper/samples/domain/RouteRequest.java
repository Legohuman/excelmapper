package com.github.excelmapper.samples.domain;

import java.util.Date;
import java.util.List;

/**
 * User: Dmitry Levin
 * Date: 16.03.14
 */
public class RouteRequest {

    private List<RouteRequestInfo> infos;

    private String note;

    private Date date;

    public RouteRequest() {
    }

    public RouteRequest(List<RouteRequestInfo> infos, String note, Date date) {
        this.infos = infos;
        this.note = note;
        this.date = date;
    }

    public List<RouteRequestInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<RouteRequestInfo> infos) {
        this.infos = infos;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
