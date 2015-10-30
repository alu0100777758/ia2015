package es.ull.etsii.ia.interface_.geometry;

/**
 * @author Javier Mart�n Hern�ndez Clase que representa un c�rculo.
 */
public class Circle extends Geometry {
	private Point2D center;
	private double radius;


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
