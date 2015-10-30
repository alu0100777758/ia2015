package es.ull.etsii.ia.interface_.geometry.drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import es.ull.etsii.ia.interface_.geometry.Point2D;

public class DrawableRectangle extends Rectangle implements Drawable {
	private Color color;
	public DrawableRectangle(Color color,double arg0, double arg1,double arg2,  double arg3) {
		setPos(new Point2D(arg0, arg1));
		setSize((int)arg2, (int)arg3);
		setColor(color);
	}
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150);
	}

	@Override
	public Point2D getPos() {
		return new Point2D(getX(), getY());
	}

	@Override
	public void setPos(Point2D newpos) {
		setLocation((int)newpos.x(), (int)newpos.y());
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(getColor());
		g2.fill(this);
	}
	

}
