Project Information
--------------------

Name: Binny Lee
Last Modified Date: Feb 15 2019


Description of Project
-----------------------

Given the set of points, implement an algorithm to find the convex hull points 
which are the set of points surrounding the out side of all points.
Divide and Conquer algorithm is used. Hull points are stored in counter-clockwise.

Besides the given codes containing hull point creater and graphing tools, 
there are two big changes: modified main class and new HullHelper class. 

In main, after read the given points data, I sort the hull point array to 
asecending order by x-coordinate. And create a HullHelper object to create 
a hull points of given points data. Then visualize all points and connected
hull points by line.

HullHelper class is a class to find convex hull points of given random points.
findHull method keeps dividing the set of points into half until the base cases
where n(point array size) is less than or equal to 3. Then merge to contain
only hull points. mergeHull finds the upper tangent line and lower tangent lines
and save hull points into mergedHull array in counter-clockwise. In terms of
the data structure of keeping merged hull points, I used array with clever
indexing so that the last element of the array connects to the first one.


How to use
-----------

Given test case file is already implemented. Compile and run the main will print
out all test case points and created hull points and the grapics.