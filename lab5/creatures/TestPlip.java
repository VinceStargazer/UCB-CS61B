package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class
 *  @authr FIXME
 */

public class TestPlip {

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Plip p = new Plip(1.8);
        Plip rep = p.replicate();
        assertEquals(0.9, p.energy(), 0.01);
        assertEquals(0.9, rep.energy(), 0.01);
        assertNotEquals(p, rep);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Clorus());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Plip(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards a random empty space.
        p = new Plip(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        int[] num = new int[4];

        for (int i = 0; i < 2000; i++) {
            actual = p.chooseAction(allEmpty);
            if (actual.dir == Direction.TOP) {
                num[0]++;
            } else if (actual.dir == Direction.BOTTOM) {
                num[1]++;
            } else if (actual.dir == Direction.LEFT) {
                num[2]++;
            } else if (actual.dir == Direction.RIGHT) {
                num[3]++;
            }
        }

        for (int i : num) {
            assertEquals(i, 500, 50);
        }
        assertEquals(num[3], 2000 - num[0] - num[1] - num[2]);


        // Clorus nearby, but also empty space and energy > 1; replicate.
        p = new Plip(1.2);
        HashMap<Direction, Occupant> threeClorus = new HashMap<>();
        threeClorus.put(Direction.TOP, new Empty());
        threeClorus.put(Direction.BOTTOM, new Clorus());
        threeClorus.put(Direction.LEFT, new Clorus());
        threeClorus.put(Direction.RIGHT, new Clorus());

        actual = p.chooseAction(threeClorus);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy < 1 and clorus nearby; 50% chance move and 50% chance stay.
        p = new Plip(.99);
        int num1 = 0;
        int num2 = 0;
        Action expected1 = new Action(Action.ActionType.MOVE, Direction.TOP);
        Action expected2 = new Action(Action.ActionType.STAY);

        for (int i = 0; i < 1000; i++) {
            actual = p.chooseAction(threeClorus);
            if (actual.equals(expected1)) {
                num1++;
            } else if (actual.equals(expected2)) {
                num2++;
            }
        }

        assertEquals(500, num1, 50);
        assertEquals(1000 - num1, num2);
        

        // Energy < 1 and no threat; stay.
        p = new Plip(.99);

        actual = p.chooseAction(allEmpty);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy < 1 and no threat; stay.
        p = new Plip(.99);

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);
    }
}
