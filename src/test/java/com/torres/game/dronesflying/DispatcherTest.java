package com.torres.game.dronesflying;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
public class DispatcherTest {
    BlockingQueue<Point> pDrone1;
    BlockingQueue<Point> pDrone2;
    ExecutorService executor;
    Dispatcher instance;

    @Before
    public void setUp() {
        executor = Executors.newFixedThreadPool(2);
    }

    @Test
    public void testNoShutdown() throws UnsupportedEncodingException {
        pDrone1 = new LinkedBlockingDeque<>();
        pDrone2 = new LinkedBlockingDeque<>();

        String drone1Data =
                "42,51.476105,-0.100224,22/03/2011 07:55\n" +
                "42,51.475967,-0.100368,22/03/2011 07:55";

        String drone2Data =
                "314,51.474579,-0.171834,22/03/2011 07:47\n" +
                        "314,51.479015,-0.172361,22/03/2011 07:48";

        InputStream i1 = new ByteArrayInputStream(drone1Data.getBytes("UTF-8"));
        InputStream i2 = new ByteArrayInputStream(drone2Data.getBytes("UTF-8"));

        instance = new Dispatcher(pDrone1, pDrone2, i1, i2, executor);
        instance.call();

        // one extra line as including the end line
        assertEquals(3, pDrone1.size());
        assertEquals(3, pDrone2.size());
    }

    @Test
    public void testShutdown() throws UnsupportedEncodingException {
        pDrone1 = new LinkedBlockingDeque<>();
        pDrone2 = new LinkedBlockingDeque<>();

        // Times at 08:10 and after 08:10
        String drone1Data =
                "42,51.476105,-0.100224,22/03/2011 08:10\n" +
                        "42,51.475967,-0.100368,22/03/2011 08:11";

        String drone2Data =
                "314,51.474579,-0.171834,22/03/2011 08:10\n";

        InputStream i1 = new ByteArrayInputStream(drone1Data.getBytes("UTF-8"));
        InputStream i2 = new ByteArrayInputStream(drone2Data.getBytes("UTF-8"));

        instance = new Dispatcher(pDrone1, pDrone2, i1, i2, executor);
        instance.call();

        assertTrue(executor.isShutdown());
    }

    @After
    public void finish() {
        executor.shutdown();
    }
}
