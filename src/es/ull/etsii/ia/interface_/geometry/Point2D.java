package es.ull.etsii.ia.interface_.geometry;
/**
 * @author Javier Martin Hernandez y Tomas Rodriguez
 *	Clase que representacion un punto en el plano con precision doble.
 */
public class Point2D {
	public static final Point2D UNIT= new Point2D(1,1);
	private double x = 0;		// componente x del punto.
	private double y = 0;		// componente y del punto.
	
	/**
	 * @param x
	 * @param y
	 */
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}


	/**
	 * @param x
	 * @param y
	 * @return suma del punto actual con el (x,y).
	 */
	public Point2D add(double x, double y) {
		return this.add(new Point2D(x, y));
	}
	
	/**
	 * @param other
	 * @return la diferencia del punto actual con other.
	 */
	public Point2D substract(Point2D other) {
		return new Point2D(this.x() - other.x(), this.y() - other.y());
	}
	
	/**
	 * @param x
	 * @param y
	 * @return la diferencia del punto actual con el (x,y)
	 */
	public Point2D substract(double x, double y) {
		return this.substract(new Point2D(x, y));
	}

	/**
	 * @param scalar por el que se multiplicará el punto.
	 * @return el punto tras aplicarle el producto escalar.
	 */
	public Point2D scalarProduct(double scalar) {
		return new Point2D(this.x() * scalar, this.y() * scalar);
	}

	/**
	 * @param other punto con el que se va a comparar.
	 * @return true si ambos puntos son iguales.
	 */
	public boolean equals(Point2D other) {
		return (this.x() == other.x()) && (this.y() == other.y());
	}
	@Override
	public String toString(){
		return new String("(" + x() +","+y()+")"); 
	}
	public Point2D add(Point2D other) {
		return new Point2D(this.x() + other.x(), this.y() + other.y());
	}
	// ******************Getters & Setters********************
	public double x() {
		return this.x;
	}
	public Point2D getRounded(){
		return new Point2D(Math.round(x()), Math.round(y()));
	}
	
	public double y() {
		return this.y;
	}
	
	public void setX(double val) {
		this.x = val;
	}
	
	public void setY(double val) {
		this.y = val;
	}
}
