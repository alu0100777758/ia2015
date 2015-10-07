package es.ull.etsii.ia.interface_;
/**
 * @author Javier Mart�n Hern�ndez
 *	Interfaz que define la comunicacion con un sistema de coordenadas bidimensional.
 */
public interface CoordinateSystem2D {
	public Point2D getPointFor(int x, int y);
	public Point2D getPointFor(Point2D point);
	public Point2D getCellFor(int x , int y );
	public Point2D getCellCenter(Point2D point);
	public boolean inSystem(Point2D point);
	public boolean atBorder(Point2D point);
	public Point2D toSystem(Point2D point);
}
