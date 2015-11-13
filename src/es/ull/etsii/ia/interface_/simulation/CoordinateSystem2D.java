package es.ull.etsii.ia.interface_.simulation;

import es.ull.etsii.ia.interface_.geometry.Point2D;

/**
 * @author Javier Martin Hernandez y Tomas Rodriguez Martin
 *	Interfaz que define la comunicacion con un sistema de coordenadas bidimensional.
 */
public interface CoordinateSystem2D {
	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public Point2D getPointFor(int x, int y);
	/**
	 * @param point
	 * @return
	 */
	public Point2D getPointFor(Point2D point);
	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public Point2D getCellFor(int x , int y );
	/**
	 * @param point
	 * @return
	 */
	public Point2D getCellCenter(Point2D point);
	/**
	 * @param point
	 * @return
	 */
	public boolean inSystem(Point2D point);
	/**
	 * @param point
	 * @return
	 */
	public boolean atBorder(Point2D point);
	/**
	 * @param point
	 * @return
	 */
	public Point2D toSystem(Point2D point);
	/**
	 * @return
	 */
	public int getHBounds();
	/**
	 * @return
	 */
	public int getVBounds();
	/**
	 * @return
	 */
	public double getHsize();
	/**
	 * @return
	 */
	public double getVsize();
	/**
	 * @return
	 */
	public Point2D getPointBounds();
}
