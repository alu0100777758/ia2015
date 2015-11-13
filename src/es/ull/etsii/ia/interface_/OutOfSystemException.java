package es.ull.etsii.ia.interface_;
/**
 *	Excepcion lanzada cuando la representacion de un objeto sobrepasa los limites dados por un sistema de coordenadas.
 * @author Javier Martin Hernandez y Tomas Rodriguez
 */
public class OutOfSystemException extends Exception {
	private static final long serialVersionUID = -3607715111861639374L;

	public OutOfSystemException(){
		super("Point out of coordinate System");
	}
}
