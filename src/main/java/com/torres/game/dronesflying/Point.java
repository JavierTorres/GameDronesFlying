package com.torres.game.dronesflying;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
final class Point {
    static final String END_LINE = "END_LINE";
    static final int NO_DRONE = 0;
    static final float DEFAULT_COORDINATE = 0f;

    enum Type {
        END, PARSING_ERROR, OK
    }

    private Type type;
    private Integer droneId;
    private Position position;
    private LocalTime localTime;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    Point(String line) {
        if (line.equals(END_LINE)) {
            type = Type.END;
            generateBlankPoint();

        } else {
            try {
                String[] lSplit = line.split(",");
                droneId = Integer.valueOf(lSplit[0]);
                position = new Position(Float.valueOf(lSplit[1]), Float.valueOf(lSplit[2]));
                localTime = LocalDateTime.parse(lSplit[3], formatter).toLocalTime();
                type = Type.OK;

            } catch (NumberFormatException | DateTimeParseException e) {
                type = Type.PARSING_ERROR;
                generateBlankPoint();
            }
        }
    }

    private void generateBlankPoint() {
        droneId = NO_DRONE;
        position = new Position(DEFAULT_COORDINATE , DEFAULT_COORDINATE);
        localTime = LocalTime.now();
    }

    Type getType() {
        return type;
    }

    Integer getDroneId() {
        return droneId;
    }

    Position getPosition() {
        return position;
    }

    LocalTime getLocalTime() {
        return localTime;
    }

    @Override
    public String toString() {
        return "Point{" +
                "type=" + type +
                ", droneId=" + droneId +
                ", position=" + position +
                ", localTime=" + localTime +
                '}';
    }
}
