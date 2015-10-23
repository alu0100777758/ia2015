package es.ull.etsii.ia.interface_;

import es.ull.etsii.ia.interface_.geometry.Point2D;

/**
 * @author Javier Mart�n Hern�ndez
 *	Interfaz que define la comunicaci�n con objetos que se puedan posicionar respecto a un punto..
 */
public interface Positionable {
	public Point2D getPos();
	public void setPos(Point2D newpos);
}
