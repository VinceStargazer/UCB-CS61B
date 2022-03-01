package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private final AStarGraph<Vertex> input;
    private final ArrayHeapMinPQ<Vertex> fringe;
    private final Vertex goal;
    private final SolverOutcome outcome;
    private final List<Vertex> solution = new ArrayList<>();
    private int numNodes;
    private final double timeSpent;
    private final HashMap<Vertex, Double> distTo = new HashMap<>();

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        this.input = input;
        goal = end;
        fringe = new ArrayHeapMinPQ<>();
        distTo.put(start, 0.0);
        fringe.add(start, h(start, end));
        while (!fringe.isEmpty()) {
            if (fringe.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                solution.add(end);
                numNodes++;
                timeSpent = sw.elapsedTime();
                return;
            } else if (false) {
                outcome = SolverOutcome.TIMEOUT;
                timeSpent = sw.elapsedTime();
                return;
            }
            Vertex p = fringe.removeSmallest();
            numNodes++;
            solution.add(p);
            for (WeightedEdge<Vertex> e : input.neighbors(p)) {
                relax(e);
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        if (outcome.equals(SolverOutcome.UNSOLVABLE) || outcome.equals(SolverOutcome.TIMEOUT)) {
            return new ArrayList<>();
        }
        return solution;
    }

    @Override
    public double solutionWeight() {
        if (outcome.equals(SolverOutcome.UNSOLVABLE) || outcome.equals(SolverOutcome.TIMEOUT)) {
            return 0;
        }
        return distTo.get(goal);
    }

    @Override
    public int numStatesExplored() {
        return numNodes;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (!distTo.containsKey(q)) {
            distTo.put(q, Double.MAX_VALUE);
        }
        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.replace(q, distTo.get(p) + w);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo.get(q) + h(q, goal));
            } else {
                fringe.add(q, distTo.get(q) + h(q, goal));
            }
        }
    }

    private double h(Vertex v, Vertex goal) {
        return input.estimatedDistanceToGoal(v, goal);
    }
}
