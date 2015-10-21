package es.ull.etsii.ia.interface_;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 * @author Javier Mart�n Hern�ndez Clase encargada de almacenar y dibujar un
 *         estado en base a un objeto que presente la interfaz
 *         CoordinateSystem2D.
 */
public class IA_State {
	private Actor mapState[][]; // Matriz donde se almacena la visión global del
								// mapa.
	private ArrayList<Point2D> points = new ArrayList<Point2D>();// TODO meter
																	// en
																	// roboPlayer
	private CoordinateSystem2D coordinates = null;
	private Color color;
	private boolean delineated = false;
	private Point2D workingSize;
	public Point2D getWorkingSize() {
		return workingSize;
	}

	public void setWorkingSize(Point2D workingSize) {
		this.workingSize = workingSize;
	}

	public boolean isDelineated() {
		return delineated;
	}

	public void setDelineated(boolean delineated) {
		this.delineated = delineated;
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}

	public void setActors(ArrayList<Actor> actors) {
		this.actors = actors;
	}

	public Color getColor() {
		return color;
	}

	private ArrayList<Actor> actors = new ArrayList<Actor>();

	public IA_State(CoordinateSystem2D coordinates) {
		this.coordinates = coordinates;
		
		// setMapState(new Actor[getCoordinates().getVBounds()][getCoordinates()
		// .getHBounds()]);
		// for (int i = 0; i < getMapState().length; i++) {
		// for (int j = 0; j < getMapState()[i].length; j++) {
		// if (i == 0)
		// getMapState()[i][j] = new Sideline(getCoordinates(),
		// new Point2D(i, j));
		// System.out.println("analizando celda (" + i + "," + j + ")");
		// }
		// }
		// getMapState()[0][5] = new Sideline(getCoordinates(),
		// new Point2D(0, 6));
		// System.out.println("" + getMapState()[0].length);
		// for(Actor [] actArray : getMapState())
		// for(Actor act : actArray)
		// act = new Actor();
		setWorkingSize(getCoordinates().getPointBounds());
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
		if(!isDelineated())
			drawLines(g);
		// new DrawableCircle(3, coordinates.getPointFor(points.get(0)), true)
		// .paint(g);
		new DrawableCircle(3, coordinates.getCellCenter(points.get(0)), true)
				.paint(g);
		Polygon polygon = new Polygon();
		for (Point2D point : points) {
			// polygon.addPoint((int) coordinates.getPointFor(point).x(),
			// (int) coordinates.getPointFor(point).y());
			polygon.addPoint((int) coordinates.getCellCenter(point).x(),
					(int) coordinates.getCellCenter(point).y());
		}
		g.drawPolyline(polygon.xpoints, polygon.ypoints, points.size());
//		new Robo_Player((short) 1, points.get(points.size() - 1), coordinates, Actor.NORTH)
//				.paint(g);
		for(Actor act : getActors())
			act.paint(g);
	}

	private void drawLines(Graphics2D g) {
		setMapState(new Actor[getCoordinates().getVBounds()][getCoordinates()
				.getHBounds()]);
		new Corner(getCoordinates(),new Point2D(1,1),Actor.NORTH).paint(g);
		new Corner(getCoordinates(),new Point2D(getCoordinates().getHBounds()-3,1),Actor.EAST).paint(g);
		new Corner(getCoordinates(),new Point2D(1,getCoordinates().getVBounds()-3),Actor.WEST).paint(g);
		new Corner(getCoordinates(),new Point2D(getCoordinates().getHBounds()-3,getCoordinates().getVBounds()-3),Actor.SOUTH).paint(g);
		for (int i = 2; i < getCoordinates().getVBounds() - 3; i++) {
			new Sideline(getCoordinates(), new Point2D(i, 1),Actor.NORTH).paint(g);
			new Sideline(getCoordinates(), new Point2D(i,getCoordinates().getVBounds()-3),Actor.NORTH).paint(g);
		}
		for (int j = 2; j < getCoordinates().getHBounds() -3 ; j++) {
			new Sideline(getCoordinates(), new Point2D(1, j),Actor.WEST).paint(g);
			new Sideline(getCoordinates(), new Point2D(getCoordinates().getHBounds()-3,j),Actor.WEST).paint(g);
		}
		setDelineated(true);
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
