package com.torres.game.dronesflying;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
public class PointTest {

    @Test
    public void testOk() {
        String line = "42,51.476105,-0.100224,22/03/2011 07:55";
        Point point = new Point(line);
        assertTrue(point.getDroneId().equals(42));
        assertTrue(point.getPosition().getLatitude().toString().equals("51.476105"));
        assertTrue(point.getPosition().getLongitude().toString().equals("-0.100224"));
        assertTrue(point.getLocalTime().toString().equals("07:55"));
        assertTrue(point.getType().equals(Point.Type.OK));
    }

    @Test
    public void testParsingDateError() {
        String line = "42,51.476105,-0.100224,ERROR/03/2011 07:55";
        Point point = new Point(line);
        assertTrue(point.getType().equals(Point.Type.PARSING_ERROR));
    }

    @Test
    public void testParsingTimeError() {
        String line = "42,51.476105,-0.100224,13/03/2011 ERROR:55";
        Point point = new Point(line);
        assertTrue(point.getType().equals(Point.Type.PARSING_ERROR));
    }

    @Test
    public void testParsingDroneError() {
        String line = "ERROR,51.476105,-0.100224,13/03/2011 07:55";
        Point point = new Point(line);
        assertTrue(point.getType().equals(Point.Type.PARSING_ERROR));
    }

    @Test
    public void testParsingLatitudeError() {
        String line = "42,ERROR,-0.100224,13/03/2011 07:55";
        Point point = new Point(line);
        assertTrue(point.getType().equals(Point.Type.PARSING_ERROR));
    }

    @Test
    public void testParsingLongitudeError() {
        String line = "42,51.476105,ERROR,13/03/2011 07:55";
        Point point = new Point(line);
        assertTrue(point.getType().equals(Point.Type.PARSING_ERROR));
    }

}
