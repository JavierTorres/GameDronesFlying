package com.torres.game.dronesflying;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
final class Position {
    final Float latitude;
    final Float longitude;

    Position(Float latitude, Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    Float getLatitude() {
        return latitude;
    }

    Float getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Position{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
