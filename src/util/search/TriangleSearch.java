package util.search;

import color.Color;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import geometry.point.ColoredPoint;
import geometry.point.Point;
import geometry.polygon.triangle.ColoredPolygon;
import geometry.polygon.triangle.ColoredTriangle;

import java.util.*;

/**
 * @author Kim-Anh Tran
 */
public class TriangleSearch {

    private List<List<ColoredPoint> > points;
    private List<ColoredPolygon> triangles;

    public TriangleSearch(List<ColoredPoint> points) {
        int nColors = Color.values().length;
        this.points = new ArrayList<List<ColoredPoint>>(nColors);

        for (int i = 0; i < nColors; ++i) {
            this.points.add(new ArrayList<ColoredPoint>());
        }

        int mask;
        for (ColoredPoint point : points) {
            mask = point.getColor().getMask();
            this.points.get(mask).add(point);
        }

        this.triangles = new LinkedList<ColoredPolygon>();
    }

    public List<ColoredPolygon> searchForTriangles() {
        search();
        return this.triangles;
    }

    private void search() {

        while (!tooFewPoints()) {
            ColoredPolygon triangle = nextTriangle();
            if (triangle != null) {
                this.triangles.add(triangle);
                removeEnclosedPoints(triangle);
            }
        }
    }

    private ColoredPolygon nextTriangle() {
        Color color = nextColor();
        int colorIndex = color.getMask();

        while(this.points.get(colorIndex).size() >= 3) {
            int p1Index = firstPoint(color);
            ColoredPoint p1 = this.points.get(colorIndex).get(p1Index);

            // Either p1 will be part of a triangle or not compatible with other points.
            this.points.get(colorIndex).remove(p1Index);

            List<ColoredPoint> p2points = new ArrayList<ColoredPoint>(this.points.get(colorIndex));
            Collections.sort(p2points, new EuclidComparator(p1));

            while (!p2points.isEmpty()) {
                int p2Index = sndPoint(p1, p2points);
                ColoredPoint p2 = p2points.get(p2Index);
                p2points.remove(p2Index);

                boolean validLine = true;
                for (ColoredPolygon triangle : this.triangles) {
                    if (triangle.intersectsWithLine(p1, p2)) {
                        validLine = false;
                        break;
                    }
                }
                if (validLine) {

                    List<ColoredPoint> p3points = new ArrayList<ColoredPoint>(p2points);
                    Collections.sort(p3points, new EuclidComparator(p2));

                    while (!p3points.isEmpty()) {
                        int p3Index = thdPoint(p2, p3points);
                        ColoredPoint p3 = p3points.get(p3Index);
                        p3points.remove(p3Index);

                        Coordinate[] coords = {p1.getCoordinate(), p2.getCoordinate()};
                        if (!CGAlgorithms.isOnLine(p3.getCoordinate(), coords)) {
                            ColoredPolygon triangle = new ColoredTriangle(p1, p2, p3);
                            if (validTriangle(triangle)) {
                                this.points.get(colorIndex).remove(p2);
                                this.points.get(colorIndex).remove(p3);
                                return triangle;
                            }
                        }
                    }
                }

            }
        }

        return null;
    }

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

    private boolean validTriangle(ColoredPolygon triangle) {
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
        return Color.fromMask(colorIndex);
    }

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

    private boolean tooFewPoints() {
        boolean tooFew = true;
        for (List<ColoredPoint> pointsList : this.points) {
            if (pointsList.size() >= ColoredTriangle.N_POINTS) {
                tooFew = false;
                break;
            }
        }
        return tooFew;
    }

    public class EuclidComparator implements Comparator<Point> {

        private Point point;

        public EuclidComparator(Point point) {
            this.point = point;
        }

        @Override
        public int compare(Point p1, Point p2) {
            Coordinate p1coord = p1.getCoordinate();
            Coordinate p2coord = p2.getCoordinate();

            double distP1 = pointDistances(this.point, p1);
            double distP2 = pointDistances(this.point, p2);

            if (distP1 < distP2)    return -1;
            if (distP2 > distP1)    return 1;

            return 0;
        }


        private double pointDistances(Point p1, Point p2) {
            Coordinate p1coord = p1.getCoordinate();
            Coordinate p2coord = p2.getCoordinate();

            double powX = Math.pow(p1coord.x - p2coord.x, 2);
            double powY = Math.pow(p1coord.y - p2coord.y, 2);

            return (powX + powY);
        }

    }
}