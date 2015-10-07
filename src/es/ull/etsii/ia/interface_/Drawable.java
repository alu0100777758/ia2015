package es.ull.etsii.ia.interface_;

import java.awt.Graphics;
/**
 * @author Javier Mart�n Hern�ndez
 *	interfaz que define la comunicaci�n con cualquier objeto dibujable en pantalla.
 */

public interface Drawable extends Positionable {
	public void paint(Graphics g);
}
