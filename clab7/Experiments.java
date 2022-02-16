import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        Random r = new Random();
        BST<Integer> test = new BST<>();
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        List<Double> y2Values = new ArrayList<>();
        while (test.size() < 5000) {
            test.add(r.nextInt());
            double thisY = test.averageDepth();
            xValues.add(test.size());
            yValues.add(thisY);
            double thisY2 = ExperimentHelper.optimalAverageDepth(test.size());
            y2Values.add(thisY2);
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).
                xAxisTitle("total nodes").yAxisTitle("average depth").build();
        chart.addSeries("random BST", xValues, yValues);
        chart.addSeries("optimal BST", xValues, y2Values);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        Random r = new Random();
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        BST<Integer> test = new BST<>();
        while (test.size() < 5000) {
            test.add(r.nextInt());
        }
        for (int i = 1; i <= 1000; i++) {
            ExperimentHelper.knottOperation(test);
            xValues.add(i);
            yValues.add(test.averageDepth());
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).
                xAxisTitle("operation times").yAxisTitle("average depth").build();
        chart.addSeries("Knott operation", xValues, yValues);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        Random r = new Random();
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        BST<Integer> test = new BST<>();
        while (test.size() < 5000) {
            test.add(r.nextInt());
        }
        for (int i = 1; i <= 1000; i++) {
            ExperimentHelper.epplingerOperation(test);
            xValues.add(i);
            yValues.add(test.averageDepth());
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).
                xAxisTitle("operation times").yAxisTitle("average depth").build();
        chart.addSeries("Epplinger operation", xValues, yValues);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        Experiments.experiment1();
        Experiments.experiment2();
        Experiments.experiment3();
    }
}
