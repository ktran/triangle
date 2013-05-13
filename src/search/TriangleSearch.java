package search;

import color.Color;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.ColoredPoint;
import geometry.polygon.ColoredPolygon;
import geometry.polygon.triangle.ColoredTriangle;

import java.util.*;


/**
 * An instance of TriangleSearch searches for point-disjoint, colored triangles
 * in a 2d set of points.
 *
 * @author Kim-Anh Tran
 */
public class TriangleSearch {

    /**
     * The collection of 2D points that are considered for triangle creation.
     */
    private List<List<ColoredPoint>> points;

    /**
     * The collection of triangles that are found.
     */
    private List<ColoredPolygon> triangles;

    /**
     * Creates a new triangle search instance for searching
     * triangles within the specified collection f 2d points.
     *
     * @param points    The 2d points.
     */
    public TriangleSearch(List<ColoredPoint> points) {
        int nColors = Color.values().length;
        this.points = new ArrayList<List<ColoredPoint>>(nColors);

        for (int i = 0; i < nColors; ++i) {
            this.points.add(new ArrayList<ColoredPoint>());
        }

        int colorIndex;
        for (ColoredPoint point : points) {
            colorIndex = point.getColor().getIntRepresentation();
            this.points.get(colorIndex).add(point);
        }

        this.triangles = new LinkedList<ColoredPolygon>();
    }

    /**
     * Returns a collection of triangles found among the 2d points.
     *
     * @return  A collection of triangles.
     */
    public List<ColoredPolygon> searchForTriangles() {
        search();
        return this.triangles;
    }

    /**
     * Starts the triangle search.
     */
    private void search() {

        while (enoughPointsLeft()) {
            Color color = nextColor();
            ColoredPolygon triangle = nextTriangle(color);

            if (triangle != null) {
                this.triangles.add(triangle);
                removeEnclosedPoints(triangle);
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
    private ColoredPolygon nextTriangle(Color color) {
        int colorIndex = color.getIntRepresentation();
        List<ColoredPoint> cPoints = this.points.get(colorIndex);

        // Search as long as enough points exist for creating a triangle
        while(this.points.get(colorIndex).size() >= 3) {
            int p1Index = firstPoint(color);
            ColoredPoint p1 = cPoints.get(p1Index);

            /*
             * Remove p1 for further consideration: Either p1 will be part of a 
             * triangle or not compatible with other points.
             */
            this.points.get(colorIndex).remove(p1Index);

            // Search for second triangle point
            List<ColoredPoint> p2Potential = new ArrayList<ColoredPoint>(cPoints);
            Collections.sort(p2Potential, new EuclidComparator(p1));

            while (!p2Potential.isEmpty()) {
                int p2Index = sndPoint(p1, p2Potential);
                ColoredPoint p2 = p2Potential.get(p2Index);
                p2Potential.remove(p2Index);

                boolean validLine = true;
                for (ColoredPolygon triangle : this.triangles) {
                    if (triangle.intersectsWithLine(p1, p2)) {
                        validLine = false;
                        break;
                    }
                }
                if (validLine) {

                    // Search for third triangle point
                    List<ColoredPoint> p3Potential = new ArrayList<ColoredPoint>(p2Potential);
                    Collections.sort(p3Potential, new EuclidComparator(p2));

                    while (!p3Potential.isEmpty()) {
                        int p3Index = thdPoint(p2, p3Potential);
                        ColoredPoint p3 = p3Potential.get(p3Index);
                        p3Potential.remove(p3Index);

                        Coordinate[] coordinates = {p1.getCoordinate(), p2.getCoordinate()};
                        if (!CGAlgorithms.isOnLine(p3.getCoordinate(), coordinates)) {
                            ColoredPolygon triangle = new ColoredTriangle(p1, p2, p3);
                            if (disjoint(triangle)) {
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

    /**
     * Removes all points from the considered point list that are
     * enclosed by the specified triangle.
     * 
     * @param triangle  The triangle that might enclose points.
     */
    private void removeEnclosedPoints(ColoredPolygon triangle) {
        for (List<ColoredPoint> pointList : this.points) {
            Iterator<ColoredPoint> iterator = pointList.iterator();
            while (iterator.hasNext()) {
                if (triangle.enclosesPoint(iterator.next())) {
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

    private int thdPoint(ColoredPoint sndPoint, List<ColoredPoint> potentialPoints) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    private int sndPoint(ColoredPoint fstPoint, List<ColoredPoint> potentialPoints) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    /**
     * Selects the next color to be considered for finding a new triangle.
     * 
     * @return  Color, for which a new triangle could be found.
     */
    private Color nextColor() {
        int[] occurrences = colorOccurence();
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
    private int[] colorOccurence() {
        int nColors = Color.values().length;
        int[] occurrences = new int[nColors];

        for (int i = 0; i < nColors; ++i) {
            occurrences[i] = this.points.get(i).size();
        }
                                        
        return occurrences;
    }

    private int firstPoint(Color color) {
        return 0;
    }

    /**
     * Checks if enough points are available in order to
     * find a new triangle.
     * 
     * @return  True, if enough points are left for creating a triangle.
     */
    private boolean enoughPointsLeft() {
        boolean enoughPoints = false;
        for (List<ColoredPoint> pointsList : this.points) {
            if (pointsList.size() >= ColoredTriangle.N_POINTS) {
                enoughPoints = true;
                break;
            }
        }
        return enoughPoints;
    }


}