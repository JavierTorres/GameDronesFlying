package com.torres.game.dronesflying;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
final class Drone implements Callable<Boolean> {

    enum Type {
        ONE(42),TWO(314);

        Integer id;

        Type(Integer id) {
           this.id = id;
        }

        public Integer getId() {
            return id;
        }
    }

    private static final long DELAY = 15L;
    private final Type type;
    private BlockingQueue<Point> points;
    private ConcurrentLinkedQueue<TrafficInformation> trafficInfo;

    public Drone(Type type, BlockingQueue<Point> points, ConcurrentLinkedQueue<TrafficInformation> trafficInfo) {
        this.type = type;
        this.points = points;
        this.trafficInfo = trafficInfo;
    }

    @Override
    public Boolean call() throws Exception {

        try{
            Point point;
            //consuming points until the endpoint
            while(!((point = points.take()).getType()).equals(Point.Type.END)) {
                calculateTrafficInformation(point);
            }
        } catch(InterruptedException e) {
            System.out.println("Drone " + type.getId() + " is shutdown");
            return false;
        }

        return true;
    }

    Type getType() {
        return type;
    }

    private void calculateTrafficInformation(Point point) throws InterruptedException {
        Instant t1 = Instant.now();
        delay();
        Instant t2 = Instant.now();
        Long speed = Duration.between(t1, t2).toNanos();

        TrafficInformation.Condition condition = TrafficInformation.Condition.values()[
                new Random().nextInt(TrafficInformation.Condition.values().length)];

        TrafficInformation pointInfo = new TrafficInformation(type.getId(), point.getLocalTime(), speed, condition);
        System.out.println(pointInfo);

        trafficInfo.add(pointInfo);
    }

    private static void delay() throws InterruptedException {

        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            throw e;
        }
    }
}
