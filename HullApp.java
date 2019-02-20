import java.util.Arrays;
import java.util.Comparator;

/**
 *  Convex Hull<br>
 *
 *  Given the set of points, implement an algorithm to 
 *  find the convex hull points which are the set of points
 *  surrounding the out side of all points. Divide and Conquer
 *  algorithm is used. Hull points are stored in counter-clockwise.
 *
 *  @author Binny Lee
 *  @version Feb 15 2019
 */
public class HullApp
{
    
    public static void main(String[] args) {
	PointSource ps = new PointSource();
	HullHelper hullHelper = new HullHelper();
	HullPoint[] points = ps.readPoints("TestCases/points.dat");
	System.out.println("** Given points **");
	printPoints(points);
	
	// Sort the points by x in ascending order
	// java sorting alg: thetha(n lgn)
	Arrays.sort(points, new Comparator<HullPoint>() {
		@Override
		public int compare(HullPoint p1, HullPoint p2) {
			return Double.compare(p1.x, p2.x);
		}
	});
	
	HullPoint[] hull = hullHelper.findHull(points);
	System.out.println("** Found Hull points **");
	printPoints(hull);
	
//	HullPoint[] hull = ps.readPoints("hull.dat");
//	
	HullGUI gui = new HullGUI(points, 1000);

	gui.drawPoints(points);
	gui.drawHull(hull);
//	gui.erase(points);
//	gui.erase(hull);
    }
    
    private static void printPoints(HullPoint[] points) {
    	for(HullPoint p : points) {
    		if(p != null) {
    			System.out.println(p.x + " " + p.y);
    		}
    	}
    }
}
