import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *  Convex Hull<br>
 *
 *  The <code>HullHelper</code> class provides a method for a 
 *  program that finds the hull points.
 *
 *  findHull method is dividing the point sets half until it
 *  reaches the base case where the size is less than or equal to 3,
 *  find the hull points of each divided subsets, and merge them.
 *  All founded hull points are stored in counter-clockwise.
 *
 *  @author Binny Lee
 *  @version Feb 15 2019
 */

public class HullHelper {
	public HullPoint[] findHull(HullPoint[] points) {
		int n = points.length;
		if (n > 1) {
			int median = (int) Math.floor(n / 2);
			HullPoint[] leftHull = findHull(Arrays.copyOfRange(points, 0, median));
			HullPoint[] rightHull = findHull(Arrays.copyOfRange(points, median, n));
			if(n <= 3) {
				return baseHull(points);
			}
			return mergeHull(leftHull, rightHull);
		} else {
			return points;
		}
	}

	private HullPoint[] mergeHull(HullPoint[] leftHull, HullPoint[] rightHull) {
		int n1 = leftHull.length;
		int n2 = rightHull.length;

		// find the rightmost point of leftHull
		int rightMostIndexOfLeft = 0;
		for (int i = 0; i < n1; i++) {
			if (leftHull[i].x > leftHull[rightMostIndexOfLeft].x) {
				rightMostIndexOfLeft = i;
			}
		}
		// find the leftmost point of rightHull
		int leftMostIndexOfRight = 0;
		for (int i = 0; i < n2; i++) {
			if (rightHull[i].x < rightHull[leftMostIndexOfRight].x) {
				leftMostIndexOfRight = i;
			}
		}

		// find upper tangent and save
		boolean isUpperTangentToLeft = false;
		boolean isUpperTangentToRight = false;
		int upperLeft = rightMostIndexOfLeft;
		int upperRight = leftMostIndexOfRight;
		while(!isUpperTangentToLeft || !isUpperTangentToRight) {
			// find upper tangent to leftHull
			while (!isCounterClockwise(rightHull[upperRight], leftHull[upperLeft],
					leftHull[(upperLeft + 1) % n1])
					|| !isCounterClockwise(rightHull[upperRight], leftHull[upperLeft],
							leftHull[(n1 + upperLeft - 1) % n1])) {
				upperLeft = (upperLeft + 1) % n1;
			}
			isUpperTangentToLeft = true;
			// find upper tangent to rightHull
			while(!isClockwise(leftHull[upperLeft], rightHull[upperRight],
					rightHull[(upperRight + 1) % n2])
					|| !isClockwise(leftHull[upperLeft], rightHull[upperRight],
							rightHull[(n2 + upperRight - 1) % n2])) {
				upperRight = (n2 + upperRight - 1) % n2;
			}
			isUpperTangentToRight = true;
		}

		// find lower tangent and save
		boolean isLowerTangentToLeft = false;
		boolean isLowerTangentToRight = false;
		int lowerLeft = rightMostIndexOfLeft;
		int lowerRight = leftMostIndexOfRight;
		while(!isLowerTangentToLeft || !isLowerTangentToRight) {
			// find lower tangent to rightHull
			while (!isCounterClockwise(leftHull[lowerLeft], rightHull[lowerRight],
					rightHull[(lowerRight + 1) % n2])
					|| !isCounterClockwise(leftHull[lowerLeft], rightHull[lowerRight],
							rightHull[(n2 + lowerRight - 1) % n2])) {
				lowerRight = (lowerRight + 1) % n2;
			}
			isLowerTangentToLeft = true;
			// find upper tangent to leftHull
			while(!isClockwise(rightHull[lowerRight], leftHull[lowerLeft],
					leftHull[(n1 + lowerLeft - 1) % n1])
					|| !isClockwise(rightHull[lowerRight], leftHull[lowerLeft],
							leftHull[(lowerLeft + 1) % n1])) {
				lowerLeft = (n1 + lowerLeft - 1) % n1;
			}
			isLowerTangentToRight = true;
		}
		
		// Save convex hull points from upperLeft ~ lowerLeft and lowerRight ~ lowerRight
		ArrayList<HullPoint> mergedHull = new ArrayList<>();
		int i = upperLeft;
		mergedHull.add(leftHull[upperLeft]);
		while(i != lowerLeft) {
			i = (i + 1) % n1;
			mergedHull.add(leftHull[i]);
		}
		
		i = lowerRight;
		mergedHull.add(rightHull[lowerRight]);
		while(i != upperRight) {
			i = (i + 1) % n2;
			mergedHull.add(rightHull[i]);
		}
		// ArrayList to Array
		HullPoint[] mergedHull2 = mergedHull.toArray(new HullPoint[mergedHull.size()]);
		return mergedHull2;
	}

	private boolean isCounterClockwise(HullPoint p1, HullPoint p0, HullPoint p2) {
		double res = ((p1.x - p0.x) * (p2.y - p0.y)) - ((p2.x - p0.x) * (p1.y - p0.y));
		if (res <= 0)
			return true;
		return false;
	}

	private boolean isClockwise(HullPoint p1, HullPoint p0, HullPoint p2) {
		double res = ((p1.x - p0.x) * (p2.y - p0.y)) - ((p2.x - p0.x) * (p1.y - p0.y));
		if (res > 0)
			return true;
		return false;
	}

	 private HullPoint[] baseHull(HullPoint[] points) {
		 int n = points.length;
		 HullPoint[] mergedHull = new HullPoint[n];
		 if(n == 1) {
			 return points;
		 }else if(n == 2) {
			 if(points[0].y > points[1].y){
				 mergedHull[0] = points[0];
				 mergedHull[1] = points[1];
			 }else {
				 mergedHull[0] = points[1];
				 mergedHull[1] = points[0];
			 }
			 return mergedHull;
		 }else if(n == 3) {
			 Arrays.sort(points, new Comparator<HullPoint>() {
					@Override
					public int compare(HullPoint p1, HullPoint p2) {
						return Double.compare(p2.y, p1.y);
					}
			 });
			 // if middle x is greater than high x, then middle should be the last one
			 // bc we store elements in counterclockwise
			 if(points[0].x < points[1].x) {
				 HullPoint temp = points[1];
				 points[1] = points[2];
				 points[2] = temp;
			 }
			 return points;
		 }
		 return points;
	 }
}
