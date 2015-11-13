package es.ull.etsii.ia.interface_.Actors;

import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.utils.Array2D;

/**
 * Clase que representa la posicion y percepcion relativas del actor en el mapa.
 * @author Javier Martin Hernandez y Tomas Rodriguez
 */
public class Vision2D extends Array2D<Actor>{
	private Point2D relativePos;	//	Posicion del actor respecto a su percepcion.
	/**
	 * @param array mapa con los objetivos observables.
	 * @param relativePos posicion relativa del actor.
	 */
	public Vision2D(Array2D<Actor> array, Point2D relativePos) {
		super(array);
		setRelativePos(relativePos);
	}
	// ******************Getters & Setters********************
	/**
	 * @return posicion relativa del actor.
	 */
	public Point2D getRelativePos() {
		return relativePos;
	}
	/**
	 * @param relativePos posicion relativa del actor.
	 */
	public void setRelativePos(Point2D relativePos) {
		this.relativePos = relativePos;
	}
}
