package util.parallel;

import geometry.point.ColoredPoint;
import geometry.polygon.Polygon;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * An instance of EnclosedPointsFinder returns a collection of points that are
 * enclosed in a specified polygon.
 *
 * @author Kim-Anh Tran
 */
public class EnclosedPointsFinder extends RecursiveTask<List<ColoredPoint>> {

    /**
     * The threshold that indicates, when to stop splitting into
     * smaller collection of points for parallel execution.
     */
    private static final int MAX_NUMBER_POINTS = 100;

    /**
     * The collection of points to check against a polygon.
     */
    private List<ColoredPoint> points;

    /**
     * The polygon to check against a collection of points.
     */
    private Polygon polygon;


    /**
     * Creates a new instance of EnclosedPointsFinder.
     *
     * @param points    The points to be checked.
     * @param polygon   The polygon to check against.
     */
    public EnclosedPointsFinder(List<ColoredPoint> points, Polygon polygon) {
        this.points = points;
        this.polygon = polygon;
    }


    @Override
    protected List<ColoredPoint> compute() {
        List<ColoredPoint> enclosed;
        int nPoints = this.points.size();

        // If the task ist too big, split
        if (nPoints > MAX_NUMBER_POINTS) {
            int slice = nPoints / 2;
            EnclosedPointsFinder left = new EnclosedPointsFinder(this.points.subList(0, slice), this.polygon);
            EnclosedPointsFinder right = new EnclosedPointsFinder(this.points.subList(slice, nPoints), this.polygon);

            left.fork();
            enclosed = left.join();
            enclosed.addAll(right.compute());

            return enclosed;
        }

        // Else collect the points that are enclosed in the polygon.
        enclosed = new LinkedList<ColoredPoint>();
        for (ColoredPoint point : this.points) {
            if (this.polygon.enclosesPoint(point)) {
                enclosed.add(point);
            }
        }

        return enclosed;
    }
}
