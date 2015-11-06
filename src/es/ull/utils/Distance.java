package es.ull.utils;

public class Distance {
	public static int manhattan(int x0, int y0, int x1, int y1){
		return Math.abs(x1-x0) + Math.abs(y1-y0);
	}
}
