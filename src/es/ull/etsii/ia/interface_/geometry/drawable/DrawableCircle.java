package es.ull.etsii.ia.interface_.geometry.drawable;

import java.awt.Graphics;

import es.ull.etsii.ia.interface_.geometry.Circle;
import es.ull.etsii.ia.interface_.geometry.Point2D;

/**
 * @author Javier Martin Hernandez y Tomas Rodriguez
 *	Clase encargada de representar un circulo con la capacidad de dibujarse en pantalla.
 */
public class DrawableCircle extends Circle implements Drawable {
	private boolean filled = false;						// true si se rellena el circulo.		
	DrawableCircle(double radius, Point2D center) {
		super(radius, center);
	}
	/**
	 * @param radius
	 * @param center
	 * @param filled false si se desea una circunferencia.
	 */
	public DrawableCircle(double radius, Point2D center, boolean filled) {
		this(radius, center);
		this.filled = filled;
	}
	@Override
	public void paint(Graphics g) {
		 int xRectangle = (int)(getCenter().x()-(getRadius()));
		 int yRectangle = (int)(getCenter().y()-(getRadius()));
		if(filled){
			g.fillOval(xRectangle, yRectangle, (int)getRadius()*2, (int)getRadius()*2);
		}
		else
			g.drawOval(xRectangle, yRectangle, (int)getRadius()*2, (int)getRadius()*2);
	}
	// ******************Getters & Setters********************
	public Point2D getPos() {
		return getCenter();
	}
	public void setPos(Point2D newpos) {
		setCenter(newpos);
//		System.out.println("pos: (" + getCenter().x() + "," + getCenter().y()+")");
	}

}
