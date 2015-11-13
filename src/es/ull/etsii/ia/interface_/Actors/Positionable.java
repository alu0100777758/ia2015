package es.ull.etsii.ia.interface_.Actors;

import es.ull.etsii.ia.interface_.geometry.Point2D;

/**
 *	Interfaz que define la comunicacion con objetos que se puedan posicionar respecto a un punto.
 * @author Javier Martin Hernandez y Tomas Rodriguez
 */
public interface Positionable {
	/**
	 * @return la posicion actual
	 */
	public Point2D getPos();
	/**
	 * @param newpos la posicion objetivo
	 */
	public void setPos(Point2D newpos);
}
