package es.ull.etsii.ia.interface_.Actors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

import es.ull.etsii.ia.interface_.CoordinateSystem2D;
import es.ull.etsii.ia.interface_.HiveMemory;
import es.ull.etsii.ia.interface_.geometry.Circle;
import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.etsii.ia.interface_.geometry.drawable.Drawable;
import es.ull.etsii.ia.interface_.geometry.drawable.DrawableCircle;
import es.ull.etsii.ia.interface_.geometry.drawable.DrawableRectangle;
import es.ull.utils.Array2D;
import es.ull.utils.Distance;

public class Robo_Player extends Actor {
	private static final int COLOR_MAX = 16777215;
	public static final String T1 = "img/playerT1.png"; // Path de la primera
														// equipacion
	public static final String T2 = "img/playerT2.png"; // Path de la segunda
														// equipacion
	private ArrayList<Point2D> points = new ArrayList<Point2D>();
	private ArrayList<Drawable> evaluated = new ArrayList<Drawable>();
	private boolean evaluation = false;
	private boolean mode = true;
	private SensitiveEnviroment map;
	private Vision2D view;
	private int team = 0;
	private HiveMemory memory;
	private int nextStep = -1;

	// private Point2D invisionPos;

	public boolean isEvaluation() {
		return evaluation;
	}

	public void setEvaluation(boolean evaluation) {
		this.evaluation = evaluation;
	}

	public ArrayList<Drawable> getEvaluatedPoints() {
		return evaluated;
	}

	public void setEvaluatedPoints(ArrayList<Drawable> evaluatedPoints) {
		this.evaluated = evaluatedPoints;
	}

	public Robo_Player(short team, Point2D point,
			CoordinateSystem2D coordinates, int face, SensitiveEnviroment map,
			HiveMemory memory) {
		super(coordinates, face);
		setPos(point);
		setMemory(memory);
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
			step();
		mode = !mode;
		return mode;
	}

	protected void evaluate() {
		ViewElements elements = scanView();
		getEvaluatedPoints().clear();
		int center = evaluatePoint(elements, getPos());
		int north = evaluatePoint(elements, getPos().add(Actor.MOVEMENT_NORTH));
		int east = evaluatePoint(elements, getPos().add(Actor.MOVEMENT_EAST));
		int west = evaluatePoint(elements, getPos().add(Actor.MOVEMENT_WEST));
		int south = evaluatePoint(elements, getPos().add(MOVEMENT_SOUTH));
		int max = Math.max(Math.max(center, north),
				Math.max(east, Math.max(west, south)));
		int min = Math.min(Math.min(center, north),
				Math.min(east, Math.min(west, south)));
		if (min == center)	// menos es mejor
			nextStep = -1;
		else if (min == north)
			nextStep = Actor.NORTH;
		else if (min == east)
			nextStep = Actor.EAST;
		else if (min == west)
			nextStep = Actor.WEST;
		else if (min == south)
			nextStep = Actor.SOUTH;
		System.out.println("next step = " + nextStep);
		Point2D pos = getCoordinates().getPointFor(getPos());
		Point2D diff = diffPoint(pos);
			getEvaluatedPoints().add(
					new DrawableRectangle(colorFromNum(center, max), pos.x(),
							pos.y(), diff.x(), diff.y()));
			Point2D pos1 = getCoordinates().getPointFor(
					getPos().add(MOVEMENT_NORTH));
			getEvaluatedPoints().add(
					new DrawableRectangle(colorFromNum(north, max), pos1.x(),
							pos1.y(), diff.x(), diff.y()));
			Point2D pos2 = getCoordinates().getPointFor(
					getPos().add(MOVEMENT_SOUTH));
			getEvaluatedPoints().add(
					new DrawableRectangle(colorFromNum(south, max), pos2.x(),
							pos2.y(), diff.x(), diff.y()));
			Point2D pos3 = getCoordinates().getPointFor(
					getPos().add(MOVEMENT_EAST));
			getEvaluatedPoints().add(
					new DrawableRectangle(colorFromNum(east, max), pos3.x(),
							pos3.y(), diff.x(), diff.y()));
			Point2D pos4 = getCoordinates().getPointFor(
					getPos().add(MOVEMENT_WEST));
			getEvaluatedPoints().add(
					new DrawableRectangle(colorFromNum(west, max), pos4.x(),
							pos4.y(), diff.x(), diff.y()));
	}

	private int evaluatePoint(ViewElements elements, Point2D pos) {
		if (getMemory().isAttackState())
			return evaluateAtt(elements, pos);
		return evaluateDef(elements, pos);
	}

	private int evaluateDef(ViewElements elements, Point2D pos) {
		int value = 1;
		// System.out.println("allies  " + elements.getAlly());
		// System.out.println("enemys  " + elements.getFoe());
		if (elements.getBall() != null)
			value += Distance.manhattan((int) elements.getBall().getPos().x(),
					(int) elements.getBall().getPos().y(), (int) pos.x(),
					(int) pos.y()) * 2;
		// System.out.println("valueAfterball  " + value);
		for (Robo_Player enemy : elements.getFoe()) {
			value += Distance.manhattan((int) enemy.getPos().x(), (int) enemy
					.getPos().y(), (int) pos.x(), (int) pos.y());
		}
		// System.out.println("valueAfterEnemy  " + value);
		for (Robo_Player ally : elements.getAlly()) {
			if (ally != this) {
				value -= Distance.manhattan((int) ally.getPos().x(), (int) ally
						.getPos().y(), (int) pos.x(), (int) pos.y());
				if (elements.getBall() != null) {
					turnFriend(ally, elements.getBall());
				}
			}
		}
		// System.out.println("valueAfterAlly  " + value);
		return value;
	}

	private int evaluateAtt(ViewElements elements, Point2D pos) {
		int value = 1;
		// System.out.println("allies  " + elements.getAlly());
		// System.out.println("enemys  " + elements.getFoe());
		// System.out.println("valueAfterball  " + value);
		for (Robo_Player enemy : elements.getFoe()) {
			value += Distance.manhattan((int) enemy.getPos().x(), (int) enemy
					.getPos().y(), (int) pos.x(), (int) pos.y());
		}
		// System.out.println("valueAfterEnemy  " + value);
		for (Robo_Player ally : elements.getAlly()) {
			if (ally != this) {
				value -= Distance.manhattan((int) ally.getPos().x(), (int) ally
						.getPos().y(), (int) pos.x(), (int) pos.y());
				if (elements.getBall() != null) {
					turnFriend(ally, elements.getBall());
				}
			}
		}
		if (elements.getBall() != null)
			if (pos.equals(elements.getBall().getPos()) && Distance.manhattan((int) elements.getBall().getPos().x(),
					(int) elements.getBall().getPos().y(), (int) pos.x(),
					(int) pos.y()) < 3) {
					if (getTeam() == 0) {
						if(Distance.manhattan(elements.getBall().getPos(), new Point2D(getView().getRows() ,elements.getBall().getPos().y()) ) <= Distance.manhattan(getView().getRelativePos(), new Point2D(getView().getRows(),getView().getRelativePos().y())))// TODO buscar porteria en el scan
								{
//							if (pos.equals(getPos().add(MOVEMENT[EAST]))
//								}
//								&& pos.add(MOVEMENT[EAST]) == null) {
//							value -= 3;
							value += Distance.manhattan(getView().getRelativePos(), new Point2D(getView().getRows(),getView().getRelativePos().y()));
							System.out.println("perfect to the right");
						}else {
							value += 3;
						}
					} else if (pos.equals(getPos().add(MOVEMENT[WEST]))
							&& pos.add(MOVEMENT[WEST]) == null) {
						System.out.println("perfect to the left");
						value -= 3;
					}else {
						value += 3;
					}
				}else {
					value += 4;
				}
				
		 System.out.println("valueAfterAlly  " + value);
		return value;
	}
	private void turnFriend(Robo_Player ally, Ball ball) {
		boolean turned = false;
		switch (ally.getFacing()) {
		case Actor.FACE_NORTH:
			if (!turned && ball.getPos().y() > ally.getPos().y()) {
				ally.setFacing(Actor.FACE_SOUTH);
				addTurned(ally);
				turned = true;
			}
			break;
		case Actor.FACE_EAST:
			if (!turned && ball.getPos().x() < ally.getPos().x()) {
				ally.setFacing(Actor.FACE_WEST);
				addTurned(ally);
				turned = true;
			}
			break;
		case Actor.FACE_WEST:
			if (!turned && ball.getPos().x() > ally.getPos().x()) {
				ally.setFacing(Actor.FACE_EAST);
				addTurned(ally);
				turned = true;
			}
			break;
		case Actor.FACE_SOUTH:
			if (!turned && ball.getPos().y() < ally.getPos().y()) {
				ally.setFacing(Actor.FACE_NORTH);
				addTurned(ally);
				turned = true;
			}
			break;
		}
	}

	private void addTurned(Robo_Player ally) {
		getEvaluatedPoints().add(
				new DrawableCircle(getCoordinates().getHsize(),
						getCoordinates().getCellCenter(ally.getPos()), false));
	}


	private ViewElements scanView() {
		setView(getMap().getVision(this));
//		System.out.println(getView());
		ViewElements elements = new ViewElements();
		for (Actor element : getView()) {
			if ((element != null)) {
				if ((element.getClass() == Robo_Player.class)) {
					if (((Robo_Player) element).getTeam() == getTeam()) {
						elements.addAlly((Robo_Player) element);
					} else {
						elements.addFoe((Robo_Player) element);
					}
				} else if ((element.getClass() == Ball.class)) {
					elements.setBall((Ball) element);
				}
			}
		}
		if(elements.getBall() == null){
			getMemory().setBallSeen(getMemory().getBallSeen()+1);
			if(getMemory().getBallSeen()>= getMemory().getHiveSize())
				setFacing(FACE[new Random().nextInt(4)]);
		}else
			getMemory().setBallSeen(0);
		return elements;
	}

	public Color getRandomColor() {
		return new Color(new Random().nextInt(16581375));
	}

	public Color colorFromNum(int num, int maxrange) {
		if (maxrange < 1)
			maxrange = 1;
		int val = (((COLOR_MAX * num) / maxrange));
		// System.out.println("Colorvalue:" + val);
		return new Color(val);
	}

	@Override
	public void paint(Graphics goriginal) {
		loadSprite();
		Graphics2D g = (Graphics2D) goriginal.create();
		drawPath(g);
		super.paint(g);
		if (!mode) {
			for (Drawable rect : getEvaluatedPoints())
				rect.paint(g);
		}
	}

	private void drawPath(Graphics2D g) {
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g.setColor(getSpritePath() == T1 ? Color.RED : Color.BLUE);
		new DrawableCircle(6, getCoordinates().getCellCenter(points.get(0)),
				true).paint(g);
		Polygon polygon = new Polygon();
		for (Point2D point : points) {
			polygon.addPoint((int) getCoordinates().getCellCenter(point).x(),
					(int) getCoordinates().getCellCenter(point).y());
		}
		g.drawPolyline(polygon.xpoints, polygon.ypoints, points.size());
	}

	public void randomMove() {
		int random = new Random().nextInt(4);
		// setPos(getPos().add(MOVEMENT[random]));
		// setFacing(FACE[random]);
		// addRelative(MOVEMENT[random]);
		move(random);
	}

	public void step() {
		if (nextStep >= 0) {
			Point2D dest = getView().getRelativePos().add(MOVEMENT[nextStep]);
//			System.out.println("dest: " + dest);
			try {
				Actor destEl = getView().get((int) dest.y(), (int) dest.x());
				if (destEl != null && destEl.getClass() == Ball.class) {
					System.out.println("movingBall");
					destEl.setPos(destEl.getPos().add(MOVEMENT[nextStep]));
					getMemory().setAttackState(true);
				} else{
					getMemory().setAttackState(false);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			move(nextStep);
		}
	}

	public void move(int movement) {
		setPos(getPos().add(MOVEMENT[movement]));
		setFacing(FACE[movement]);
		addRelative(MOVEMENT[movement]);
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
		return "R" + getPos();
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

	public Vision2D getView() {
		return view;
	}

	public void setView(Vision2D view) {
		this.view = view;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
		if (getTeam() == 0)
			setSpritePath(T1);
		else if (getTeam() == 1)
			setSpritePath(T2);
	}

	public HiveMemory getMemory() {
		return memory;
	}

	public void setMemory(HiveMemory memory) {
		this.memory = memory;
	}

	public int getNextStep() {
		return nextStep;
	}

	public void setNextStep(int nextStep) {
		this.nextStep = nextStep;
	}

	// public Point2D getInvisionPos() {
	// return invisionPos;
	// }
	//
	// public void setInvisionPos(Point2D invisionPos) {
	// this.invisionPos = invisionPos;
	// }

}
