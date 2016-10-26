package com.torres.game.dronesflying;

import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
public class TrafficInformationTest {

    @Test
    public void testCreation() {
        TrafficInformation trafficInformation = new TrafficInformation(
                42,
                LocalTime.of(8, 10),
                22L,
                TrafficInformation.Condition.HEAVY);

        assertTrue(TrafficInformation.Condition.HEAVY.equals(trafficInformation.getCondition()));
        assertEquals(42, trafficInformation.getDroneId().intValue());
        assertTrue(LocalTime.of(8,10).equals(trafficInformation.getTime()));
        assertEquals(22, trafficInformation.getSpeed().intValue());

        assertTrue("TrafficInformation{droneId=42, time=08:10, speed=22, condition=HEAVY}"
                .equals(trafficInformation.toString()));
    }
}
