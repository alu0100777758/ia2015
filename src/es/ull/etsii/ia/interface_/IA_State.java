package es.ull.etsii.ia.interface_;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de almacenar y dibujar un estado en base a un objeto que presente la interfaz CoordinateSystem2D.
 */
public class IA_State {
	private Actor mapState [][];									//	Matriz donde se almacena la visión global del mapa.
	private ArrayList<Point2D> points = new ArrayList<Point2D>();//TODO meter en roboPlayer
	private CoordinateSystem2D coordinates = null;
	private Color color;
	
	public IA_State(CoordinateSystem2D coordinates) {
		this.coordinates = coordinates;
		setMapState(new Actor[getCoordinates().getVBounds()][getCoordinates().getHBounds()]);
//		for(Actor [] actArray : getMapState())
//			for(Actor act : actArray)
//				act = new Actor();
		setColor(Color.RED);
	}

	public void setCoordinateSystem(CoordinateSystem2D coordinates) {
		this.coordinates = coordinates;
	}

	public void addPoint(Point2D point) throws OutOfSystemException {
		if (!coordinates.inSystem(point))
			throw new OutOfSystemException();
		points.add(point);
	}

	public Point2D getLast() {
		return points.get(points.size() - 1);
	}

	public void addRelative(Point2D point) throws OutOfSystemException {
		addPoint(points.get(points.size() - 1).add(point));
	}

	public void setStart(Point2D point) {
		if (points.size() < 1)
			points.add(point);
		else
			points.set(0, point);
	}

	public void clear() {
		points.clear();
	}

	public boolean isOut() {
		for (Point2D point : points) {
			if (!coordinates.inSystem(point))
				return false;
		}
		return true;
	}

	public boolean isAtBorder() {
		for (Point2D point : points) {
			if (coordinates.atBorder(point))
				return true;
		}
		return false;
	}

	public boolean hasVisited(Point2D point) {
		for (Point2D visitedPoint : points) {
			if (visitedPoint.equals(point))
				return true;
		}
		return false;
	}
	
	public void drawPath(Graphics originalG) {
		Graphics2D g = (Graphics2D) originalG.create();
		g.setColor(color);
//		new DrawableCircle(3, coordinates.getPointFor(points.get(0)), true)
//				.paint(g);
		new DrawableCircle(3, coordinates.getCellCenter(points.get(0)), true)
		.paint(g);
		Polygon polygon = new Polygon();
		for (Point2D point : points) {
//			polygon.addPoint((int) coordinates.getPointFor(point).x(),
//					(int) coordinates.getPointFor(point).y());
			polygon.addPoint((int) coordinates.getCellCenter(point).x(),
					(int) coordinates.getCellCenter(point).y());
		}
		g.drawPolyline(polygon.xpoints, polygon.ypoints, points.size());
		new Robo_Player((short)1,points.get(points.size() - 1),coordinates).paint(g);
		for ( int i = 0; i < getMapState().length;i++){
			for(int j = 0; i < getMapState().length;i++){
				if(getMapState()[i][j] != null)
					getMapState()[i][j].paint(g);
			}
		}
	}
	// ******************Getters & Setters********************
	public Actor[][] getMapState() {
		return mapState;
	}

	public void setMapState(Actor mapState[][]) {
		this.mapState = mapState;
	}
	public CoordinateSystem2D getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(CoordinateSystem2D coordinates) {
		this.coordinates = coordinates;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
