package es.ull.etsii.ia.interface_.geometry;

/**
 * 		   Clase que representa un circulo.
 * @author Javier Martin Hernandez y Tomas Rodriguez
 */
public class Circle extends Geometry {
	private Point2D center;				// centro del circulo.
	private double radius;				// radio del circulo.


	public Circle(double radius, Point2D center) {
		this.radius = radius;
		this.center = center;
	}
	// ******************Getters & Setters********************
	public Point2D getCenter() {
		return center;
	}
	
	public void setCenter(Point2D center) {
		this.center = center;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
}
