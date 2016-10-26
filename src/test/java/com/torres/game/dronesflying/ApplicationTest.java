package com.torres.game.dronesflying;

import com.torres.game.dronesflying.Drone.Type;
import org.junit.Test;

import java.time.LocalTime;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
public class ApplicationTest {
    Application application = new Application();
    private static final String droneFile1 = "/42.csv";
    private static final String droneFile2 = "/314.csv";
    ConcurrentLinkedQueue<TrafficInformation> trafficInformation;

    @Test
    public void testAll() throws Exception {
        trafficInformation = application.run(
                this.getClass().getResource(droneFile1).getFile(),
                this.getClass().getResource(droneFile2).getFile());

        // Test multiple actions just to reused the existing data and avoid re-run
        testThereIsTrafficInformation();
        testThereIsNoTrafficInformationAfterShutdown();
        testDroneHasWorkedToday(Type.ONE);
        testDroneHasWorkedToday(Type.TWO);
    }

    private void testThereIsTrafficInformation() {
        assertTrue(trafficInformation.size() > 0);
    }

    private void testThereIsNoTrafficInformationAfterShutdown() {
        long result = trafficInformation.stream()
                .filter(t -> t.getTime().isAfter(LocalTime.of(8,10)))
                .count();

        assertEquals(0, result);
    }

    private void testDroneHasWorkedToday(Type type) {
        long result = trafficInformation.stream()
                .filter(t -> t.getDroneId().equals(type.getId()))
                .count();

        assertTrue(result > 0);
    }
}
