package es.ull.etsii.ia.interface_.Actors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

import es.ull.etsii.ia.interface_.CoordinateSystem2D;
import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.etsii.ia.interface_.geometry.drawable.DrawableCircle;
import es.ull.etsii.ia.interface_.geometry.drawable.DrawableRectangle;
import es.ull.utils.Array2D;

public class Robo_Player extends Actor {
	public static final String T1 = "img/playerT1.png"; // Path de la primera
														// equipacion
	public static final String T2 = "img/playerT2.png"; // Path de la segunda
														// equipacion
	private ArrayList<Point2D> points = new ArrayList<Point2D>();
	private ArrayList<DrawableRectangle> evaluated = new ArrayList<DrawableRectangle>();
	private boolean evaluation = false;
	private boolean mode = true;
	private SensitiveEnviroment map;
	private Array2D<Actor> view;
	private int team = 0;

	public boolean isEvaluation() {
		return evaluation;
	}

	public void setEvaluation(boolean evaluation) {
		this.evaluation = evaluation;
	}

	public ArrayList<DrawableRectangle> getEvaluatedPoints() {
		return evaluated;
	}

	public void setEvaluatedPoints(ArrayList<DrawableRectangle> evaluatedPoints) {
		this.evaluated = evaluatedPoints;
	}

	public Robo_Player(short team, Point2D point,
			CoordinateSystem2D coordinates, int face, SensitiveEnviroment map) {
		super(coordinates, face);
		setPos(point);
		// System.out.println(point);
		setTeam(team);
		setStart(point);
		setMap(map);
	}

	/**
	 * @return boolean true si ha terminado de efectuar todas sus acciones.
	 */
	public boolean tick() {
		if (mode) {
			evaluate();
		} else
			randomMove();
		mode = !mode;
		return mode;
	}
	protected void evaluate() {
		setView(getMap().getVision(this));
		System.out.println(getView());
		getEvaluatedPoints().clear();
		Point2D pos = getCoordinates().getPointFor(getPos());
		Point2D diff = diffPoint(pos);
		for (int i = 0; i < 3; i++) {
			getEvaluatedPoints().add(
					new DrawableRectangle(getRandomColor(), pos.x(), pos.y(),
							diff.x(), diff.y()));
			Point2D pos1 = getCoordinates().getPointFor(
					getPos().add(MOVEMENT_NORTH));
			getEvaluatedPoints().add(
					new DrawableRectangle(getRandomColor(), pos1.x(), pos1.y(),
							diff.x(), diff.y()));
			Point2D pos2 = getCoordinates().getPointFor(
					getPos().add(MOVEMENT_SOUTH));
			getEvaluatedPoints().add(
					new DrawableRectangle(getRandomColor(), pos2.x(), pos2.y(),
							diff.x(), diff.y()));
			Point2D pos3 = getCoordinates().getPointFor(
					getPos().add(MOVEMENT_EAST));
			getEvaluatedPoints().add(
					new DrawableRectangle(getRandomColor(), pos3.x(), pos3.y(),
							diff.x(), diff.y()));
			Point2D pos4 = getCoordinates().getPointFor(
					getPos().add(MOVEMENT_WEST));
			getEvaluatedPoints().add(
					new DrawableRectangle(getRandomColor(), pos4.x(), pos4.y(),
							diff.x(), diff.y()));

		}
	}

	public Color getRandomColor() {
		return new Color(new Random().nextInt(16581375));
	}

	@Override
	public void paint(Graphics goriginal) {
		if (getTeam() == 0)
			setSpritePath(T1);
		else if (getTeam() == 1)
			setSpritePath(T2);
		loadSprite();
		Graphics2D g = (Graphics2D) goriginal.create();
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g.setColor(getSpritePath() == T1 ? Color.RED : Color.BLUE);
		new DrawableCircle(6, getCoordinates().getCellCenter(points.get(0)),
				true).paint(g);
		Polygon polygon = new Polygon();
		for (Point2D point : points) {
			// polygon.addPoint((int) coordinates.getPointFor(point).x(),
			// (int) coordinates.getPointFor(point).y());
			polygon.addPoint((int) getCoordinates().getCellCenter(point).x(),
					(int) getCoordinates().getCellCenter(point).y());
		}
		g.drawPolyline(polygon.xpoints, polygon.ypoints, points.size());
		super.paint(g);
		if (!mode) {
			for (DrawableRectangle rect : getEvaluatedPoints())
				rect.paint(g);
		}
	}

	public void randomMove() {
		int random = new Random().nextInt(4);
		move(getPos().add(MOVEMENT[random]));
		setFacing(FACE[random]);
		addRelative(MOVEMENT[random]);
	}

	private void move(Point2D point2d) {
//		getView().switchElements((int)getPos().y(), (int)getPos().x(),(int) point2d.y(), (int)point2d.x());
		setPos(point2d);
	}

	public void addPoint(Point2D point) {
		// if (!getCoordinates().inSystem(point))
		// throw new OutOfSystemException();
		points.add(point);
	}

	public Point2D getLast() {
		return points.get(points.size() - 1);
	}

	public void addRelative(Point2D point) {
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
			if (!getCoordinates().inSystem(point))
				return false;
		}
		return true;
	}

	public boolean isAtBorder() {
		for (Point2D point : points) {
			if (getCoordinates().atBorder(point))
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
	@Override
	public String toString() {
		return "R"+getPos();
	}
	// ******************Getters & Setters********************
	public ArrayList<Point2D> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point2D> points) {
		this.points = points;
	}

	public SensitiveEnviroment getMap() {
		return map;
	}

	public void setMap(SensitiveEnviroment map) {
		this.map = map;
	}

	public Array2D<Actor> getView() {
		return view;
	}

	public void setView(Array2D<Actor> view) {
		this.view = view;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}
	
}
