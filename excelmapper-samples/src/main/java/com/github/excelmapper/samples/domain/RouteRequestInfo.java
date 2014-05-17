package com.github.excelmapper.samples.domain;

/**
 * User: Dmitry Levin
 * Date: 16.03.14
 */
public class RouteRequestInfo {

    private int trainNumber;

    private String fromStation;

    private String toStation;

    public RouteRequestInfo() {
    }

    public RouteRequestInfo(int trainNumber, String fromStation, String toStation) {
        this.trainNumber = trainNumber;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }
}
