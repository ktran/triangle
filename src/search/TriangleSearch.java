package search;

import color.Color;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import geometry.point.ColoredPoint;
import geometry.polygon.ColoredPolygon;
import geometry.polygon.triangle.ColoredTriangle;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


/**
 * An instance of TriangleSearch searches for point-disjoint, colored triangles
 * in a 2d set of points.
 *
 * @author Kim-Anh Tran
 */
public class TriangleSearch extends RecursiveTask<List<ColoredPolygon>> {

    public static final int SPLIT_DEPTH = 3;
    public static final int MAX_POINTS = 1000;

    /**
     * The collection of 2D points that are considered for triangle creation.
     */
    private List<ColoredPoint> points;

    private List<Boolean> enclosed;

    /**
     * The collection of triangles that are found.
     */
    private List<ColoredPolygon> triangles;

    private int splitDepth;


    /**
     * Creates a new triangle search instance for searching
     * triangles within the specified collection f 2d points.
     *
     * @param points    The 2d points.
     */
    public TriangleSearch(List<ColoredPoint> points, List<Boolean> enclosed, int splitDepth) {
        this.points = points;
        this.splitDepth = splitDepth;
        this.enclosed = enclosed;
    }

    public static List<ColoredPolygon> searchForTriangles(List<ColoredPoint> points) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Collections.sort(points);
        List<Boolean> enclosed = new ArrayList<Boolean>();
        for (int i = 0; i < points.size();++i) {
            enclosed.add(false);
        }
        TriangleSearch search = new TriangleSearch(points, enclosed, 0);
        return forkJoinPool.invoke(search);
    }

    /**
     * Starts the triangle search.
     */
    private void search() {
        int nColors = Color.values().length;
        List<List<ColoredPoint>> cPoints= new LinkedList<List<ColoredPoint>>();
        for (int i = 0; i < nColors; ++i) {
            cPoints.add(new ArrayList<ColoredPoint>());
        }

        int colorIndex;
        int nPoints = this.points.size();
        for (int i = 0; i < nPoints; ++i) {
            if (!this.enclosed.get(i)) {
                ColoredPoint point = this.points.get(i);
                colorIndex = point.getColor().getIntRepresentation();
                cPoints.get(colorIndex).add(point);
            }
        }

        while (enoughPointsLeft(cPoints)) {
            Color color = nextColor(cPoints);
            ColoredPolygon triangle = nextTriangle(cPoints.get(color.getIntRepresentation()), color);

            if (triangle != null) {
                this.triangles.add(triangle);
                removeAndMark(cPoints, triangle);
            }
        }
    }

    /**
     * Searches for the next available triangle. If it returns null,
     * no triangle could be found for the specified color.
     * 
     * @param   color   The color specifying which colored triangle to search for.
     * @return          Triangle, if found for the specified color. Null, otherwise.
     */
    private ColoredPolygon nextTriangle(List<ColoredPoint> cPoints, Color color) {
        // Search as long as enough points exist for creating a triangle
        while(cPoints.size() >= 3) {
            ColoredPoint p1 = cPoints.get(0);

            /*
             * Remove p1 for further consideration: Either p1 will be part of a 
             * triangle or not compatible with other points.
             */
            cPoints.remove(0);

            // Pick second triangle point. Take closest ones first.
            List<ColoredPoint> potentialPoints = new ArrayList<ColoredPoint>(cPoints);
            Collections.sort(potentialPoints, new EuclidComparator(p1));
            Iterator<ColoredPoint> p2Iterator = potentialPoints.iterator();

            while (p2Iterator.hasNext()) {
                ColoredPoint p2 = p2Iterator.next();
                p2Iterator.remove();

                boolean validLine = true;
                for (ColoredPolygon triangle : this.triangles) {
                    if (triangle.intersectsWithLine(p1, p2)) {
                        validLine = false;
                        break;
                    }
                }
                if (validLine) {

                    // Pick third triangle point. Pick closest one to p1.
                    Iterator<ColoredPoint> p3Iterator = potentialPoints.iterator();
                    while (p3Iterator.hasNext()) {
                        ColoredPoint p3 = p3Iterator.next();

                        if (CGAlgorithms.computeOrientation(p1.getCoordinate(), p2.getCoordinate(), p3.getCoordinate())
                                != CGAlgorithms.COLLINEAR) {
                            ColoredPolygon triangle = new ColoredTriangle(p1, p2, p3);
                            if (disjoint(triangle)) {
                                markAsEnclosed(p1);
                                markAsEnclosed(p2);
                                markAsEnclosed(p3);
                                cPoints.remove(p2);
                                cPoints.remove(p3);
                                return triangle;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private void markAsEnclosed(ColoredPoint p1) {
        int index = this.points.indexOf(p1);
        this.enclosed.set(index, true);
    }

    /**
     * Removes all points from the considered point list that are
     * enclosed by the specified triangle.
     * 
     * @param triangle  The triangle that might enclose points.
     */
    private void removeAndMark(List<List<ColoredPoint>> cPoints, ColoredPolygon triangle) {
        for (List<ColoredPoint> pointList : cPoints) {
            Iterator<ColoredPoint> iterator = pointList.iterator();
            while (iterator.hasNext()) {
                ColoredPoint point = iterator.next();
                if (triangle.enclosesPoint(point)) {
                    markAsEnclosed(point);
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Checks if the specified triangle collides with the set of existing triangles.
     * 
     * @param triangle  The triangle to be checked against the existing ones.
     * @return          True, if triangle does not collide with existing triangles.
     *                  False, otherwise.
     */
    private boolean disjoint(ColoredPolygon triangle) {
        for (ColoredPolygon curTriangle : this.triangles) {
            if (curTriangle.intersectsWithPolygon(triangle)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Selects the next color to be considered for finding a new triangle.
     * 
     * @return  Color, for which a new triangle could be found.
     */
    private Color nextColor(List<List<ColoredPoint>> cPoints) {
        int[] occurrences = colorOccurence(cPoints);
        int maxOccurrence = occurrences[0];
        int colorIndex = 0;
        int nColors = Color.values().length;
        
        for (int i = 1; i < nColors; ++i) {
            if (maxOccurrence < occurrences[i]) {
                maxOccurrence = occurrences[i];
                colorIndex = i;
            }
        }
        return Color.fromInt(colorIndex);
    }

    /**
     * Returns a list of numbers indicating how many
     * points are still to be considered, ordered by their color.
     * 
     * @return  Array indicating how many points of each color are not yet
     *          considered for triangle creation.
     */
    private int[] colorOccurence(List<List<ColoredPoint>> cPoints) {
        int nColors = Color.values().length;
        int[] occurrences = new int[nColors];

        for (int i = 0; i < nColors; ++i) {
            occurrences[i] = cPoints.get(i).size();
        }
                                        
        return occurrences;
    }

    /**
     * Checks if enough points are available in order to
     * find a new triangle.
     * 
     * @return  True, if enough points are left for creating a triangle.
     */
    private boolean enoughPointsLeft(List<List<ColoredPoint>> cPoints) {
        boolean enoughPoints = false;
        for (List<ColoredPoint> pointsList : cPoints) {
            if (pointsList.size() >= ColoredTriangle.N_POINTS) {
                enoughPoints = true;
                break;
            }
        }
        return enoughPoints;
    }


    @Override
    protected List<ColoredPolygon> compute() {
        int nPoints = this.points.size();

        if (this.splitDepth < SPLIT_DEPTH && nPoints > MAX_POINTS) {
            int split = nPoints/2;

            TriangleSearch leftSearch = new TriangleSearch(this.points.subList(0, split), this.enclosed.subList(0, split), splitDepth + 1);
            TriangleSearch rightSearch = new TriangleSearch(this.points.subList(split, nPoints), this.enclosed.subList(split, nPoints), splitDepth + 1);

            leftSearch.fork();

           this.triangles = leftSearch.join();
           this.triangles.addAll(rightSearch.compute());

        } else {
            this.triangles = new LinkedList<ColoredPolygon>();
        }

        search();
        return this.triangles;
    }
}