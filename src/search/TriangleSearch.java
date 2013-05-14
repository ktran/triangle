package search;

import color.Color;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import geometry.point.ColoredPoint;
import geometry.polygon.ColoredPolygon;
import geometry.polygon.triangle.ColoredTriangle;
import util.parallel.EnclosedPointsFinder;
import util.comparator.EuclidComparator;

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

    /**
     * The maximum depth to split the search task into.
     * The decision whether or not to split into smaller tasks is also dependent on
     * {@link TriangleSearch#MAX_POINTS}.
     *
     * @see search.TriangleSearch#compute()
     */
    public static final int SPLIT_DEPTH = 3;

    /**
     * The maximum number of points that can be considered for one thread.
     * The decision whether or not to split into smaller tasks is also dependent on
     * {@link TriangleSearch#SPLIT_DEPTH}.
     *
     * @see search.TriangleSearch#compute()
     */
    public static final int MAX_POINTS = 1000;

    /**
     * The collection of 2D points that are considered for triangle creation.
     */
    private List<ColoredPoint> points;

    /**
     * A collection of boolean indicating whether a point at the same index
     * in {@link TriangleSearch#points} is enclosed by a triangle.
     */
    private List<Boolean> enclosed;

    /**
     * The collection of triangles that are found.
     */
    private List<ColoredPolygon> triangles;

    /**
     * The current splitting depth.
     */
    private int splitDepth;


    /**
     * Creates a new triangle search instance for searching
     * triangles within the specified collection f 2d points.
     *
     * @param points    The 2d points.
     */
    private TriangleSearch(List<ColoredPoint> points, List<Boolean> enclosed, int splitDepth) {
        this.points = points;
        this.splitDepth = splitDepth;
        this.enclosed = enclosed;
    }

    public static List<ColoredPolygon> searchForTriangles(List<ColoredPoint> points) {
        List<Boolean> enclosed = new ArrayList<Boolean>();
        for (int i = 0; i < points.size();++i) {
            enclosed.add(false);
        }
        int currentDepth = 0;

        // Sorting points facilitates splitting into smaller tasks.
        Collections.sort(points);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        TriangleSearch search = new TriangleSearch(points, enclosed, currentDepth);
        return forkJoinPool.invoke(search);
    }

    /**
     * Starts the triangle search.
     */
    private void search() {
        // Extract points that are not yet enclosed in other triangles.
        List<ColoredPoint> availablePoints = new LinkedList<ColoredPoint>();
        int nPoints = this.points.size();
        for (int i = 0; i < nPoints; ++i) {
            if (!this.enclosed.get(i)) {
                ColoredPoint point = this.points.get(i);
                availablePoints.add(point);
            }
        }

        /*
         * As long as at least 3 points of any color exist, a point might
         * be hiding.
         */
        while (enoughPointsLeft(availablePoints)) {
            Color color = nextColor(availablePoints);
            ColoredPolygon triangle = nextTriangle(availablePoints, color);

            if (triangle != null) {
                this.triangles.add(triangle);
                markAndRemoveEnclosedPoints(availablePoints, triangle);
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
        int[] colorOccurrence = colorOccurrence(cPoints);
        int numberOfColoredPoints = colorOccurrence[color.getIntRepresentation()];
        while(numberOfColoredPoints >= 3) {
            ListIterator<ColoredPoint> iterator = cPoints.listIterator();
            ColoredPoint p1 = getNextWithColor(iterator, color);

            /*
             * Remove p1 for further consideration: Either p1 will be part of a 
             * triangle or not compatible with other points.
             */
            iterator.remove();
            markAsEnclosed(p1);
            --numberOfColoredPoints;

            // Pick second triangle point. Take closest ones first.
            List<ColoredPoint> potentialPoints = new ArrayList<ColoredPoint>(cPoints);
            potentialPoints.remove(p1);
            Collections.sort(potentialPoints, new EuclidComparator(p1));
            ListIterator<ColoredPoint> p2Iterator = potentialPoints.listIterator();

            while (p2Iterator.hasNext()) {
                ColoredPoint p2 = getNextWithColor(p2Iterator, color);
                if (p2 == null) {
                    break;
                }
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
                    ListIterator<ColoredPoint> p3Iterator = potentialPoints.listIterator();
                    while (p3Iterator.hasNext()) {
                        ColoredPoint p3 = getNextWithColor(p3Iterator, color);
                        if (p3 == null) {
                            break;
                        }

                        if (CGAlgorithms.computeOrientation(p1.getCoordinate(), p2.getCoordinate(), p3.getCoordinate())
                                != CGAlgorithms.COLLINEAR) {
                            ColoredPolygon triangle = new ColoredTriangle(p1, p2, p3);
                            if (disjoint(triangle)) {
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

    private ColoredPoint getNextWithColor(ListIterator<ColoredPoint> iterator, Color color) {
        ColoredPoint point = null;
        while(iterator.hasNext()) {
            ColoredPoint currentPoint = iterator.next();
            if (currentPoint.getColor() == color ) {
                point = currentPoint;
                break;
            }
        }
        return point;
    }

    private void markAsEnclosed(ColoredPoint p1) {
        int index = this.points.indexOf(p1);
        this.enclosed.set(index, true);
    }

    /**
     * Removes all points from the currently considered point list that are
     * enclosed by the specified triangle. The enclosed fields are updated
     * accordingly.
     *
     * @param cPoints   The currently considered points for triangle creation.
     * @param triangle  The triangle that might enclose points.
     */
    private void markAndRemoveEnclosedPoints(List<ColoredPoint> cPoints, ColoredPolygon triangle) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        EnclosedPointsFinder findEnclosePoints = new EnclosedPointsFinder(cPoints, triangle);
        List<ColoredPoint> enclosedPoints = forkJoinPool.invoke(findEnclosePoints);
        forkJoinPool.shutdown();

        Iterator<ColoredPoint> iterator = enclosedPoints.iterator();
        while (iterator.hasNext()) {
            ColoredPoint point = iterator.next();
            markAsEnclosed(point);
            iterator.remove();
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
    private Color nextColor(List<ColoredPoint> cPoints) {
        int[] occurrences = colorOccurrence(cPoints);
        int maxOccurrence = occurrences[0];
        int colorIndex = 0;
        int nColors = Color.values().length;

        // Select color that is contained the most
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
     * @param  cPoints The points currently considered for triangle creation.
     * @return  Array indicating how many points of each color are not yet
     *          considered for triangle creation.
     */
    private int[] colorOccurrence(List<ColoredPoint> cPoints) {
        int nColors = Color.values().length;
        int[] occurrences = new int[nColors];

        int nPoints = this.points.size();
        for (int i = 0; i < nPoints; ++i) {
            if (!this.enclosed.get(i)) {
                int colorIndex = this.points.get(i).getColor().getIntRepresentation();
                ++occurrences[colorIndex];
            }
        }
                                        
        return occurrences;
    }

    /**
     * Checks if enough points are available in order to
     * find a new triangle.
     *
     * @param  cPoints  The points that are currently considered for triangle creation.
     * @return  True, if enough points are left for creating a triangle.
     */
    private boolean enoughPointsLeft(List<ColoredPoint> cPoints) {
        boolean enoughPoints = false;
        int[] colorOccurrence = colorOccurrence(cPoints);

        // As long as 3 points of one color remain, we might find another triangle.
        for (int occurrence : colorOccurrence) {
            if (occurrence >= ColoredTriangle.N_POINTS) {
                enoughPoints = true;
                break;
            }
        }
        return enoughPoints;
    }


    @Override
    protected List<ColoredPolygon> compute() {
        int nPoints = this.points.size();

        // Split task if too big.
        if (this.splitDepth < SPLIT_DEPTH && nPoints > MAX_POINTS) {
            int split = nPoints/2;

            /* Split points vertically. Creating triangles in isolation will not
             * interfere with each other, as points are sorted.
             */
            TriangleSearch leftSearch = new TriangleSearch(this.points.subList(0, split), this.enclosed.subList(0, split), splitDepth + 1);
            TriangleSearch rightSearch = new TriangleSearch(this.points.subList(split, nPoints), this.enclosed.subList(split, nPoints), splitDepth + 1);

            leftSearch.fork();

            this.triangles = leftSearch.join();
            this.triangles.addAll(rightSearch.compute());

        } else {
            // If task can be computed without splitting, create a new list.
            this.triangles = new LinkedList<ColoredPolygon>();
        }

        // Search for new triangles.
        search();
        return this.triangles;
    }
}