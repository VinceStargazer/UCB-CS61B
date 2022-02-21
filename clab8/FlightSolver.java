import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are <= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    private final PriorityQueue<Flight> rankStart;
    private final PriorityQueue<Flight> rankEnd;

    public FlightSolver(ArrayList<Flight> flights) {
        rankStart = new PriorityQueue<>(flights.size(), Comparator.comparingInt(Flight::startTime));
        rankEnd = new PriorityQueue<>(flights.size(), Comparator.comparingInt(Flight::endTime));
        rankStart.addAll(flights);
        rankEnd.addAll(flights);
    }

    public int solve() {
        int currentPassengers = 0;
        int maxPassengers = 0;

        while (rankStart.size() > 0) {
            if (rankStart.peek().startTime() <= rankEnd.peek().endTime()) {
                currentPassengers += rankStart.poll().passengers();
                if (currentPassengers > maxPassengers) {
                    maxPassengers = currentPassengers;
                }
            } else {
                currentPassengers -= rankEnd.poll().passengers();
            }
        }

        return maxPassengers;
    }
}
