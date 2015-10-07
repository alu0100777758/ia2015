package es.ull.etsii.ia.interface_;

import java.awt.Graphics;

/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de representar un circulo con la capacidad de dibujarse en pantalla.
 */
public class DrawableCircle extends Circle implements Drawable {
	private boolean filled = false;
	DrawableCircle(double radius, Point2D center) {
		super(radius, center);
	}
	DrawableCircle(double radius, Point2D center, boolean filled) {
		super(radius, center);
		this.filled = filled;
	}
	public void paint(Graphics g) {
		 int xRectangle = (int)(getCenter().x()-(getRadius()));
		 int yRectangle = (int)(getCenter().y()-(getRadius()));
		if(filled){
			g.fillOval(xRectangle, yRectangle, (int)getRadius()*2, (int)getRadius()*2);
		}
		else
			g.drawOval(xRectangle, yRectangle, (int)getRadius()*2, (int)getRadius()*2);
	}
	public Point2D getPos() {
		return getCenter();
	}
	public void setPos(Point2D newpos) {
		setCenter(newpos);
//		System.out.println("pos: (" + getCenter().x() + "," + getCenter().y()+")");
	}

}
