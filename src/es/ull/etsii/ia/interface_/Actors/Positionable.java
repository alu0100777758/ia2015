package es.ull.etsii.ia.interface_.Actors;

import es.ull.etsii.ia.interface_.geometry.Point2D;

/**
 * @author Javier Martin Hernandez y Tomas Rodriguez
 *	Interfaz que define la comunicacion con objetos que se puedan posicionar respecto a un punto..
 */
public interface Positionable {
	public Point2D getPos();
	public void setPos(Point2D newpos);
}
