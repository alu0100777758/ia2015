package es.ull.etsii.ia.interface_.geometry.drawable;

import java.awt.Graphics;

import es.ull.etsii.ia.interface_.Actors.Positionable;
/**
 *	interfaz que define la comunicacion con cualquier objeto dibujable en pantalla.
 * @author Javier Martin Hernandez
 */

public interface Drawable extends Positionable {
	public void paint(Graphics g);
}
