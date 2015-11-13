package es.ull.etsii.ia.interface_.simulation;

import es.ull.etsii.ia.interface_.geometry.Point2D;

/**
 *	Interfaz que define la comunicacion con un sistema de coordenadas bidimensional.
 * @author Javier Martin Hernandez y Tomas Rodriguez Martin
 */
public interface CoordinateSystem2D {
	/**
	 * devuelve el punto en la unidad del sistema externo (por ejemplo pixeles) mas cercano al punto (x,y)
	 * @param x
	 * @param y
	 * @return Point2D
	 */
	public Point2D getPointFor(int x, int y);
	/**
	 * devuelve el punto en la unidad del sistema externo (por ejemplo pixeles) mas cercano al punto proporcionado.
	 * @param point
	 * @return Point2D
	 */
	public Point2D getPointFor(Point2D point);
	/**
	 * devuelve celda con el centro mas proximo a las coordenadas dadas.
	 * @param x
	 * @param y
	 * @return Point2D
	 */
	public Point2D getCellFor(int x , int y );
	/**
	 * devuelve el punto en la unidad del sistema externo (por ejemplo pixeles) hasta el centro de la celda "point".
	 * @param point
	 * @return Point2D
	 */
	public Point2D getCellCenter(Point2D point);
	/**
	 * devuelve true si el punto se encuentra dentro del sistema.
	 * @param point
	 * @return true 
	 */
	public boolean inSystem(Point2D point);
	/**
	 * devuelve true  si el punto se encuentra sobre los limites del sistema.
	 * @param point 
	 * @return true
	 */
	public boolean atBorder(Point2D point);
	/**
	 * devuelve el punto del sistema mas cercano a point.
	 * @param point
	 * @return	Point2D
	 */
	public Point2D toSystem(Point2D point);
	/**
	 * devuelve la cantidad de puntos en el eje x que posee el sistema.
	 * @return int
	 */
	public int getHBounds();
	/**
	 * devuelve la cantidad de puntos en el eje y que posee el sistema.
	 * @return int
	 */
	public int getVBounds();
	/**
	 * devuelve la distancia en el eje x entre los puntos.
	 * @return int
	 */
	public double getHsize();
	/**
	 * devuelve la distancia en el eje y entre los puntos.
	 * @return int
	 */
	public double getVsize();
	/**
	 * devuelve el punto formado por(getHBounds(), getVBounds())
	 * @return Point2D 
	 */
	public Point2D getPointBounds();
}
