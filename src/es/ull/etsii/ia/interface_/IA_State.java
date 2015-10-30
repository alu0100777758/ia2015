package es.ull.etsii.ia.interface_;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import es.ull.etsii.ia.interface_.Actors.Actor;
import es.ull.etsii.ia.interface_.Actors.SensitiveEnviroment;
import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.utils.Array2D;

/**
 * @author Javier Mart�n Hern�ndez Clase encargada de almacenar y dibujar un
 *         estado en base a un objeto que presente la interfaz
 *         CoordinateSystem2D.
 */
public class IA_State implements SensitiveEnviroment {
	private Array2D<Actor> mapState; // Matriz donde se almacena la visión
										// global del
	// mapa.
	private CoordinateSystem2D coordinates = null;
	private Color color;
	private Point2D workingSize;
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private int turnPointer = 0;

	public IA_State(CoordinateSystem2D coordinates) {
		this.coordinates = coordinates;
		setWorkingSize(getCoordinates().getPointBounds());
		setColor(Color.RED);
		setMapState(new Array2D<Actor>((int) getWorkingSize().x(),
				(int) getWorkingSize().y()));
	}

	public void setCoordinateSystem(CoordinateSystem2D coordinates) {
		this.coordinates = coordinates;
	}

	// public void addPoint(Point2D point) throws OutOfSystemException {
	// if (!coordinates.inSystem(point))
	// throw new OutOfSystemException();
	// points.add(point);
	// }
	//
	// public Point2D getLast() {
	// return points.get(points.size() - 1);
	// }
	//
	// public void addRelative(Point2D point) throws OutOfSystemException {
	// addPoint(points.get(points.size() - 1).add(point));
	// }
	//
	// public void setStart(Point2D point) {
	// if (points.size() < 1)
	// points.add(point);
	// else
	// points.set(0, point);
	// }
	//
	// public void clear() {
	// points.clear();
	// }
	//
	// public boolean isOut() {
	// for (Point2D point : points) {
	// if (!coordinates.inSystem(point))
	// return false;
	// }
	// return true;
	// }
	//
	// public boolean isAtBorder() {
	// for (Point2D point : points) {
	// if (coordinates.atBorder(point))
	// return true;
	// }
	// return false;
	// }
	//
	public void addActor(Actor actor){
		getActors().add(actor);
		getMapState().set((int)actor.getPos().x(),(int)actor.getPos().y(), actor);
	}
	public void drawPath(Graphics originalG) {
		Graphics2D g = (Graphics2D) originalG.create();
		g.setColor(color);
			drawLines(g);
		for (Actor act : getActors())
			act.paint(g);
	}

	private void drawLines(Graphics2D gr) {
		Graphics2D g = (Graphics2D)gr.create();
		g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(Color.WHITE); 
		Point2D topleft = coordinates.getCellCenter(new Point2D(1, 1));
		Point2D bottonRight = coordinates.getCellCenter(new Point2D(getCoordinates().getHBounds()-3, getCoordinates().getVBounds()-3));
		g.drawRect((int)topleft.x(), (int)topleft.y(), (int)(bottonRight.x()-topleft.x()),(int)(bottonRight.y()-topleft.y()));
	}

	public void tick() {
		if (getActors().get(getTurnPointer()).tick())
			incrementTurn();

	}

	private void incrementTurn() {
		turnPointer++;
		if (getTurnPointer() >= getActors().size())
			setTurnPointer(0);
	}
	// ******************Getters & Setters********************

	public CoordinateSystem2D getCoordinates() {
		return coordinates;
	}


	public void setCoordinates(CoordinateSystem2D coordinates) {
		this.coordinates = coordinates;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getTurnPointer() {
		return turnPointer;
	}

	public void setTurnPointer(int turnPointer) {
		this.turnPointer = turnPointer;
	}

	public Point2D getWorkingSize() {
		return workingSize;
	}

	public void setWorkingSize(Point2D workingSize) {
		this.workingSize = workingSize;
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

	public Array2D<Actor> getMapState() {
		return mapState;
	}

	public void setMapState(Array2D<Actor> mapState) {
		this.mapState = mapState;
	}

	@Override
	public Array2D<Actor> getVision(Actor sensor) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
