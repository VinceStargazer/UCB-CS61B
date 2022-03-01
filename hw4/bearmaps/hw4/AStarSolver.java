package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private int numStatesExplored;
    private final Vertex goal;
    private final AStarGraph<Vertex> graph;
    private final SolverOutcome outcome;
    private final double explorationTime;
    private final ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();
    private final LinkedList<Vertex> solution = new LinkedList<>();
    private final HashMap<Vertex, Double> distTo = new HashMap<>();
    private final HashMap<Vertex, Vertex> edgeTo = new HashMap<>();

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        graph = input;
        goal = end;
        distTo.put(start, 0.0);
        fringe.add(start, h(start, end));

        while (fringe.size() > 0) {
            if (fringe.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                Vertex curr = goal;
                while (!curr.equals(start)) {
                    solution.addFirst(curr);
                    curr = edgeTo.get(curr);
                }
                solution.addFirst(start);
                explorationTime = sw.elapsedTime();
                return;
            } else if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                explorationTime = sw.elapsedTime();
                return;
            }

            Vertex curr = fringe.removeSmallest();
            numStatesExplored++;
            for (WeightedEdge<Vertex> e : input.neighbors(curr)) {
                relax(e);
            }
        }

        outcome = SolverOutcome.UNSOLVABLE;
        explorationTime = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
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
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return explorationTime;
    }

    /** Relax a certain edge by assigning/reassigning priority */
    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (!distTo.containsKey(q)) {
            distTo.put(q, Double.MAX_VALUE);
        }

        if (!edgeTo.containsKey(q)) {
            edgeTo.put(q, p);
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

    /** Heuristic to estimate the distance from v to goal */
    private double h(Vertex v, Vertex g) {
        return graph.estimatedDistanceToGoal(v, g);
    }
}
