package com.torres.game.dronesflying;

import java.time.LocalTime;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
final class TrafficInformation {

    enum Condition {
        HEAVY, LIGHT, MODERATE
    }

    private final Integer droneId;
    private final LocalTime time;
    private final Long speed;
    private final Condition condition;


    TrafficInformation(Integer droneId, LocalTime time, Long speed, Condition condition) {
        this.droneId = droneId;
        this.time = time;
        this.speed = speed;
        this.condition = condition;
    }

    Integer getDroneId() {
        return droneId;
    }

    LocalTime getTime() {
        return time;
    }

    Long getSpeed() {
        return speed;
    }

    Condition getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "TrafficInformation{" +
                "droneId=" + droneId +
                ", time=" + time +
                ", speed=" + speed +
                ", condition=" + condition +
                '}';
    }
}
