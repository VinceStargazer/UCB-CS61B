package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public Color color() {
        return color(34, 0, 231);
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy = energy / 2;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        boolean hasMovable = false;
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> anyPlips = new ArrayDeque<>();
        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            Direction key = entry.getKey();
            Occupant value = entry.getValue();
            if (value instanceof Empty) {
                hasMovable = true;
                emptyNeighbors.add(key);
            } else if (value instanceof Plip) {
                hasMovable = true;
                anyPlips.add(key);
            }
        }

        if (!hasMovable) {
            return new Action(Action.ActionType.STAY);
        } else if (!anyPlips.isEmpty()) {
            Direction dir = HugLifeUtils.randomEntry(anyPlips);
            return new Action(Action.ActionType.ATTACK, dir);
        } else if (energy >= 1.0) {
            Direction dir = HugLifeUtils.randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.REPLICATE, dir);
        } else {
            Direction dir = HugLifeUtils.randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.MOVE, dir);
        }
    }
}
