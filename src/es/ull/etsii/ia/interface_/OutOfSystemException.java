package es.ull.etsii.ia.interface_;
/**
 * @author Javier Mart�n Hern�ndez
 *	Excepci�n lanzada cuando la representacion de un objeto sobrepasa los limites dados por un sistema de coordenadas.
 */
public class OutOfSystemException extends Exception {
	private static final long serialVersionUID = -3607715111861639374L;

	public OutOfSystemException(){
		super("Point out of coordinate System");
	}
}
