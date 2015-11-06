package es.ull.utils;

import es.ull.etsii.ia.interface_.geometry.Point2D;

public class Distance {
	public static int manhattan(int x0, int y0, int x1, int y1){
		return Math.abs(x1-x0) + Math.abs(y1-y0);
	}
	public static int manhattan(Point2D init, Point2D end){
		return manhattan((int)init.x(), (int)init.y(),(int)end.x(), (int)end.y());
	}
}
