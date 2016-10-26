# Game Drones Flying

Our company has drones that fly around London and report traffic conditions.
We have the following actors: one dispatcher and two drones.  Each of the drones “moves” on separate threads. The dispatcher is responsible for passing the points to the drone, and for terminating the simulation. Upon receiving the new points, the drone moves and reports on the traffic conditions.

## Specifics:
The simulation end at 8:10 am, when the drones will receive a “SHUTDOWN” instruction.
There are two drones, identified by their id: 42 and 314. There are corresponding file with points along their routes. The layout of the file is: drone id, latitude, longitude, time.

## Assumptions:
Assume that the drones fly a straight line between each point, travelling at constant speed.
Disregard the fact that the start time is not in synch. The dispatcher can start pumping data as soon as it has read the files.

## Output:
The traffic information have the following format:
•	Drone Id
•	Time
•	Speed
•	Traffic conditions (HEAVY, LIGHT, MODERATE).  This is a simple random choice.

## Execution

**java -jar target/gamedronesflying-1.0.0.jar src/test/resources/42.csv src/test/resources/314.csv**