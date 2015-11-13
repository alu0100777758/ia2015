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
	 * @return punto en la unidad del sistema externo (por ejemplo pixeles) mas cercano al punto (x,y)
	 */
	public Point2D getPointFor(int x, int y);
	/**
	 * @param point
	 * @return punto en la unidad del sistema externo (por ejemplo pixeles) mas cercano al punto proporcionado.
	 */
	public Point2D getPointFor(Point2D point);
	/**
	 * @param x
	 * @param y
	 * @return celda con el centro mas proximo a las coordenadas dadas.
	 */
	public Point2D getCellFor(int x , int y );
	/**
	 * @param point que representa la coordenada de una celda.
	 * @return el punto en la unidad del sistema externo (por ejemplo pixeles) hasta el centro de la celda.
	 */
	public Point2D getCellCenter(Point2D point);
	/**
	 * @param point
	 * @return true si el punto se encuentra dentro del sistema.
	 */
	public boolean inSystem(Point2D point);
	/**
	 * @param point 
	 * @return true si el punto se encuentra sobre los limites del sistema.
	 */
	public boolean atBorder(Point2D point);
	/**
	 * @param point punto en pixeles.
	 * @return	el punto del sistema mas cercano.
	 */
	public Point2D toSystem(Point2D point);
	/**
	 * @return cantidad de puntos en el eje x que posee el sistema.
	 */
	public int getHBounds();
	/**
	 * @return cantidad de puntos en el eje y que posee el sistema.
	 */
	public int getVBounds();
	/**
	 * @return distancia en el eje x entre los puntos.
	 */
	public double getHsize();
	/**
	 * @return distancia en el eje y entre los puntos.
	 */
	public double getVsize();
	/**
	 * @return Point2D formado por(getHBounds(), getVBounds())
	 */
	public Point2D getPointBounds();
}
