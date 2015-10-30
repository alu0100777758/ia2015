package es.ull.etsii.ia.interface_.geometry.drawable;

import java.awt.Graphics;

import es.ull.etsii.ia.interface_.Positionable;
/**
 * @author Javier Martin Hernandez
 *	interfaz que define la comunicacion con cualquier objeto dibujable en pantalla.
 */

public interface Drawable extends Positionable {
	public void paint(Graphics g);
}
