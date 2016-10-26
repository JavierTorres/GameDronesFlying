package com.torres.game.dronesflying;

import java.io.*;
import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static java.util.Comparator.comparing;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
final class Dispatcher implements Callable<Boolean> {

    private BlockingQueue<Point> pDrone1;
    private BlockingQueue<Point> pDrone2;
    private final InputStream droneFile1;
    private final InputStream droneFile2;
    private ExecutorService executor;

    private static final LocalTime SHUTDOWN = LocalTime.of(8, 10);
    private static final long VELOCITY_DISPACHING = 15L;

    Dispatcher(BlockingQueue<Point> pDrone1, BlockingQueue<Point> pDrone2, InputStream droneFile1, InputStream droneFile2, ExecutorService executor) {
        this.pDrone1 = pDrone1;
        this.pDrone2 = pDrone2;
        this.droneFile1 = droneFile1;
        this.droneFile2 = droneFile2;
        this.executor = executor;
    }

    @Override
    public Boolean call() {

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(mergeStreams(droneFile1, droneFile2)))) {

            buffer.lines()
                    .map(l -> new Point(l))
                    .sorted(comparing(Point::getLocalTime))
                    .forEach(p -> {
                        try {
                            Thread.sleep(VELOCITY_DISPACHING);
                            if (p.getLocalTime().compareTo(SHUTDOWN)==0) {
                                executor.shutdownNow();
                            }

                            if (p.getDroneId().equals(Drone.Type.ONE.getId())) {
                                pDrone1.put(p);

                            } else if (p.getDroneId().equals(Drone.Type.TWO.getId())) {
                                pDrone2.put(p);

                            } else if (p.getType().equals(Point.Type.END)) {
                                pDrone1.put(p);
                                pDrone2.put(p);
                            }

                            // Skipping points with parsing errors

                        } catch (InterruptedException e) {
                            System.out.println("Dispatcher sent a SHUTDOWN at " + SHUTDOWN.toString());
                            throw new RuntimeException(e);
                        }
                    });

        } catch (IOException e) {
            System.err.println("Error merging the drones files");
            throw new RuntimeException(e);
        }

        return true;
    }

    private SequenceInputStream mergeStreams(InputStream droneFile1, InputStream droneFile2) {
        return new SequenceInputStream(droneFile1,
                new SequenceInputStream(
                        new ByteArrayInputStream("\n".getBytes()), // gives an end line between the provided files
                        new SequenceInputStream(
                                droneFile2,
                                new ByteArrayInputStream(("\n" + Point.END_LINE).getBytes())))); // gives an finished mark
    }
}