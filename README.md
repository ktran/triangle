triangle
========

Provides functionality to search point-disjoint triangles in
a given set of 2D points. The found solution is complete, i.e.
no triangle can be inserted without breaking one of the following rules:
- Two triangles are not allowed to touch or intersect each other
- Each triangle consists of three points of the same color

Modules
-------------
geometrics: Contains basic geometric shapes.
triangle:   Contains the main start up class, the search functionality and basic utils.
jts:        Library for handling geometric calculations, see http://tsusiatsoftware.net/jts/main.html


Implementation
-------------
The search is divided into smaller tasks by vertically separating the search space.
If a task is small enough (i.e. a certain depth is reached, a threshold is reached),
it is performed directly.

A triangle can be found as follows:

1. Pick a color c (-> the color that is most present in the collection of potential points)
2. Pick the first possible point p1 of color c
3. Pick p2, the closest point to p1 of color c
4. If the line segment defined by p1, p2 does not intersect any existing triangle:
5. Pick p3, the next closest point to p1 of color c
6. If the triangle defined by p1, p2, p3 does not intersect any existing triangle:
7. Triangle is found. Add to existing list of triangles.
8. Mark triangle points and enclosed points as enclosed. (no need to consider them again)

Calculating both whether a point is enclosed or if two line segments
intersect are very expensive. The first problem could be slightly improved
by using a ForkJoinPool.
