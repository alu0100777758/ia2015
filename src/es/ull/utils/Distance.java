package es.ull.utils;

import es.ull.etsii.ia.interface_.geometry.Point2D;

/**
 * Clase encargada de facilitar el calculo de diferentes distancias.
 * @author Javier Matin Hernandez
 */
public class Distance {
	/**
	 * devuelve la distancia manhattan entre el punto (x0,y0) y el (x1,y1).
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @return int
	 */
	public static int manhattan(int x0, int y0, int x1, int y1){
		return Math.abs(x1-x0) + Math.abs(y1-y0);
	}
	/**
	 * devuelve la distancia manhattan entre init y end.
	 * @param init
	 * @param end
	 * @return int
	 */
	public static int manhattan(Point2D init, Point2D end){
		return manhattan((int)init.x(), (int)init.y(),(int)end.x(), (int)end.y());
	}
}
