package com.torres.game.dronesflying;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by javierbracerotorres on 16/10/2016.
 */
public class Application {

    BlockingQueue<Point> pDrone1 = new LinkedBlockingDeque<>();
    BlockingQueue<Point> pDrone2 = new LinkedBlockingDeque<>();
    ConcurrentLinkedQueue<TrafficInformation> trafficInformation = new ConcurrentLinkedQueue<>();

    public static void main(String... args) throws Exception {
        new Application().run(args);
    }

    public ConcurrentLinkedQueue<TrafficInformation> run(String... args) throws Exception {

        if (args.length < 2) {
            System.err.println("Please provide the two drones files");
            return trafficInformation;
        }

        if (!Files.isReadable(Paths.get(args[0])) || !Files.isReadable(Paths.get(args[1]))) {
            System.err.println("Please make sure the files are readable by this program");
            return trafficInformation;
        }

        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Future<Boolean>> futures = executor.invokeAll(getActors(executor, args));

        futures.stream()
                .forEach(f -> {
                    try {
                        f.get();
                    } catch (InterruptedException | ExecutionException | RuntimeException e) {
                        System.out.println("Process not finished");
                    }
                });


        if (!executor.isShutdown()) {
            executor.shutdown();
        }

        return trafficInformation;
    }

    private Set<Callable<Boolean>> getActors(ExecutorService executor, String... args) throws FileNotFoundException {
        Dispatcher dispatcher = new Dispatcher(pDrone1,
                pDrone2, new FileInputStream(args[0]), new FileInputStream(args[1]), executor);

        Drone drone1 = new Drone(Drone.Type.ONE, pDrone1, trafficInformation);
        Drone drone2 = new Drone(Drone.Type.TWO, pDrone2, trafficInformation);

        Set<Callable<Boolean>> actors = new HashSet<Callable<Boolean>>();

        actors.add(dispatcher);
        actors.add(drone1);
        actors.add(drone2);

        return actors;
    }
}