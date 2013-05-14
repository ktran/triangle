package search.parallel;

import geometry.point.ColoredPoint;
import geometry.point.Point;
import geometry.polygon.Polygon;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author Kim-Anh Tran
 */
public class EnclosePoint extends RecursiveTask<List<ColoredPoint>> {

    private static final int MAX_NUMBER_POINTS = 100;

    private List<ColoredPoint> points;
    private Polygon polygon;


    public EnclosePoint(List<ColoredPoint> points, Polygon polygon) {
        this.points = points;
        this.polygon = polygon;
    }


    @Override
    protected List<ColoredPoint> compute() {
        List<ColoredPoint> enclosed;
        int nPoints = this.points.size();
        if (nPoints > MAX_NUMBER_POINTS) {
            int slice = nPoints / 2;
            EnclosePoint left = new EnclosePoint(this.points.subList(0, slice), this.polygon);
            EnclosePoint right = new EnclosePoint(this.points.subList(slice, nPoints), this.polygon);

            left.fork();
            enclosed = left.join();
            enclosed.addAll(right.compute());

            return enclosed;
        }

        enclosed = new LinkedList<ColoredPoint>();
        for (ColoredPoint point : this.points) {
            if (this.polygon.enclosesPoint(point)) {
                enclosed.add(point);
            }
        }

        return enclosed;
    }
}
