package com.torres.game.dronesflying;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
public class DroneTest {
    BlockingQueue<Point> pDrone1 = new LinkedBlockingDeque<>();
    ConcurrentLinkedQueue<TrafficInformation> trafficInformation = new ConcurrentLinkedQueue<>();

    @Test
    public void testDrone() throws Exception {
        pDrone1.put(new Point("42,51.510902,-0.104483,22/03/2011 08:12"));
        pDrone1.put(new Point(Point.END_LINE));
        Drone drone = new Drone(Drone.Type.ONE, pDrone1, trafficInformation);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(drone);
        future.get();

        TrafficInformation t1 = trafficInformation.poll();
        assertEquals(42, t1.getDroneId().intValue());
        executor.shutdownNow();
    }

}
