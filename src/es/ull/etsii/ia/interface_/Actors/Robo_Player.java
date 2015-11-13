package es.ull.etsii.ia.interface_.Actors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.etsii.ia.interface_.geometry.drawable.Drawable;
import es.ull.etsii.ia.interface_.geometry.drawable.DrawableCircle;
import es.ull.etsii.ia.interface_.geometry.drawable.DrawableRectangle;
import es.ull.etsii.ia.interface_.simulation.CoordinateSystem2D;
import es.ull.etsii.ia.interface_.simulation.HiveMemory;
import es.ull.utils.Distance;

public class Robo_Player extends Actor {
	public static final String T1 = "img/playerT1.png"; // Path de la primera
														// equipacion.
	public static final String T2 = "img/playerT2.png"; // Path de la segunda
														// equipacion.
	private ArrayList<Point2D> points = new ArrayList<Point2D>();
	private ArrayList<Drawable> evaluated = new ArrayList<Drawable>();
	private boolean evaluation = false;
	private boolean mode = true;
	private Vision2D view;
	private int team = 0;
	private HiveMemory memory;
	private int nextStep = -1;
	private Perception lastScanned;
	private Decision<Actor> moveD = (actor) -> {
		move(getEv().getPos());
		if (getMemory().getBallOwner() == this) {
			getMemory().setBallOwner(null);
			getMemory().setAttackState(false);
		}
	};
	private Decision<Actor> pushMove = (actor) -> {
		Ball ball = (Ball) actor;
		pushBall(ball);
		moveD.decide(actor);
		getMemory().setAttackState(true);
		getMemory().setBallOwner(this);
	};
	private Decision<Actor> shoot = (actor) -> {
		Ball ball = (Ball) actor;
		shootBall(ball, getNextStep());
	};
	private Decision<Actor> decision;
	private Evaluation<Actor> bestEvaluation;

	public void pushBall(Ball ball) {
		ball.push(this);
	}

	private void shootBall(Ball ball, int direction) {
		ball.shot(2, direction);
	}

	public Robo_Player(short team, Point2D point,
			CoordinateSystem2D coordinates, int face, SensitiveEnviroment map,
			HiveMemory memory) {
		super(coordinates, face);
		setPos(point);
		setMemory(memory);
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
		setLastScanned(scanView());
		getEvaluatedPoints().clear();
		ArrayList<Evaluation<Actor>> evPoints = new ArrayList<>();
		int excluded = getHiddenSide();
		for (int i = 0; i < MOVEMENT.length; i++) {
			if (i != excluded) {
				if (canBeEvaluated(i)) {
					evPoints.add(evaluatePoint(getLastScanned(), i));
				}
			}
		}
		Collections.sort(evPoints);
		Evaluation<Actor> minev = evPoints.get(0);
		double maxneg = minev.getValue();
		if (minev.getValue() < 0) {
			for (Evaluation<Actor> ev : evPoints)
				ev.setValue(ev.getValue() - maxneg);
		}
		getEvaluatedPoints().add(
				new DrawableCircle(2, getCoordinates().getCellCenter(
						getPos().add(Actor.MOVEMENT[minev.getPos()])), true));
		for (Evaluation<Actor> evaluation : evPoints) {
			Point2D pos = getCoordinates().getPointFor(
					getPos().add(MOVEMENT[evaluation.getPos()]));
			Point2D diff = diffPoint(pos);
			getEvaluatedPoints().add(
					new DrawableRectangle(colorFromNum(evaluation.getValue(),
							evPoints.get(evPoints.size() - 1).getValue()), pos
							.x(), pos.y(), diff.x(), diff.y()));
		}
		setEv(minev);
		setNextStep(minev.getPos());

	}

	boolean canBeEvaluated(int i) {
		return (!isOutOfBounds(i) && (getView().get(
				getView().getRelativePos().add(MOVEMENT[i])) == null
				|| getView().get(getView().getRelativePos().add(MOVEMENT[i]))
						.getClass() == Ball.class || getView().get(
				getView().getRelativePos().add(MOVEMENT[i])) == this));
	}

	private boolean isOutOfBounds(int i) {
		Point2D pos = getPos().add(MOVEMENT[i]);
		return (pos.x() < 0 || pos.y() < 0
				|| pos.x() > getCoordinates().getHBounds() - 3 || pos.y() > getCoordinates()
				.getVBounds() - 3);
	}

	private int getHiddenSide() {
		switch (getFacing()) {
		case Actor.FACE_NORTH:
			return SOUTH;
		case Actor.FACE_EAST:
			return WEST;
		case Actor.FACE_WEST:
			return EAST;
		case Actor.FACE_SOUTH:
			return NORTH;
		}
		return -1;
	}

	private Evaluation<Actor> evaluatePoint(Perception elements, int pos) {
		if (getMemory().isAttackState())
			return evaluateAtt(elements, pos);
		return evaluateDef(elements, pos);
	}

	private Evaluation<Actor> evaluateDef(Perception elements, int pos) {
		Evaluation<Actor> ev = new Evaluation<Actor>();
		ev.setPos(pos);
		ev.setDecision(moveD);
		Point2D position = getPos().add(MOVEMENT[pos]);
		int value = 1;
		if (elements.getBall() != null) {
			double manlength = elements.distanceToBall(position);
			value += manlength * getMemory().getHiveSize();
			if (manlength == 0) {
				ev.setDecision(pushMove);
			} 
		} else
		value += elements.distanceToFoes(position);
		for (Robo_Player ally : elements.getAlly()) {
			if (ally != this) {
				value -= Distance.manhattan((int) ally.getPos().x(), (int) ally
						.getPos().y(), (int) position.x(), (int) position.y());
				if (elements.getBall() != null) {
					turnFriend(ally, elements.getBall());
				}
			}
		}
		ev.setValue(value);
		return ev;
	}

	private Evaluation<Actor> evaluateAtt(Perception elements, int pos) {
		Evaluation<Actor> ev = new Evaluation<Actor>();
		ev.setPos(pos);
		ev.setDecision(moveD);
		Point2D position = getPos().add(MOVEMENT[pos]);
		int value = 1;
		if (elements.getBall() != null) {
			int manlength = elements.distanceToBall(position);
			if(!elements.getGoal().isEmpty()){
			int ballGoal = elements.distanceToEnemyGoal(elements.getBall()
					.getPos(), getTeam());
			int posGoal = elements.distanceToEnemyGoal(getPos(), getTeam());
			System.out.println("ballgoal= " + ballGoal + "   pos goal: "
					+ posGoal);
			if (ballGoal < posGoal) {
				if (manlength == 0) {
					ev.setDecision(shoot);
				} else {
					value += manlength;
				}
			} else if (manlength == 0) {
				value += manlength + (ballGoal - posGoal);
				ev.setDecision(pushMove);
			} else {
				value += manlength + (ballGoal - posGoal);
			}
			}else if(pos == NORTH || pos == SOUTH)
				value += 10;
		} else
			ev.setDecision(moveD);
		value += elements.distanceToFoes(position);
		for (Robo_Player ally : elements.getAlly()) {
			if (ally != this) {
				value -= Distance.manhattan((int) ally.getPos().x(), (int) ally
						.getPos().y(), (int) position.x(), (int) position.y());
				if (!elements.getFoeGoal(getTeam()).isEmpty()) {
					turnFriend(ally,elements.getFoeGoal(getTeam()).get(0));
				}
			}
		}
		ev.setValue(value);
		return ev;
	}

	private void turnFriend(Robo_Player ally, Actor actor) {
		boolean turned = false;
		switch (ally.getFacing()) {
		case Actor.FACE_NORTH:
			if (!turned && actor.getPos().y() > ally.getPos().y()) {
				ally.setFacing(Actor.FACE_SOUTH);
				addTurned(ally);
				turned = true;
			}
			break;
		case Actor.FACE_EAST:
			if (!turned && actor.getPos().x() < ally.getPos().x()) {
				ally.setFacing(Actor.FACE_WEST);
				addTurned(ally);
				turned = true;
			}
			break;
		case Actor.FACE_WEST:
			if (!turned && actor.getPos().x() > ally.getPos().x()) {
				ally.setFacing(Actor.FACE_EAST);
				addTurned(ally);
				turned = true;
			}
			break;
		case Actor.FACE_SOUTH:
			if (!turned && actor.getPos().y() < ally.getPos().y()) {
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

	private Perception scanView() {
		setView(getMap().perceive(this));
		Perception elements = new Perception();
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
				} else if ((element.getClass() == Goal.class)) {
					elements.addGoal((Goal) element);
				}
			}
		}
		if (elements.getBall() == null) {
			getMemory().setBallSeen(getMemory().getBallSeen() + 1);
			if (getMemory().getBallSeen() > getMemory().getHiveSize()) {
				setFacing(FACE[new Random().nextInt(4)]);
			}
		} else
			getMemory().setBallSeen(0);
		return elements;
	}

	public Color colorFromNum(double num, double maxrange) {
		if (maxrange == 0) {
			maxrange = 1;
		}
		double h = 1 - (num / maxrange * 0.7); // Hue
		double s = 1; // Saturation
		double b = 1; // Brightness
		return Color.getHSBColor((float) h, (float) s, (float) b);
	}

	@Override
	public void paint(Graphics goriginal) {
		loadSprite();
		Graphics2D g = (Graphics2D) goriginal.create();
		drawPath(g);
		super.paint(g);
		if (!mode) {
			for (Drawable rect : getEvaluatedPoints()) {
				rect.paint(g);
			}
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

	public void step() {
		// System.out.println(getView());
		// System.out.println("ev: " + getEv());
		// System.out.println("ev.decision: " + getEv().getDecision());
		// System.out.println("lastscaned: " + getLastScanned());
		// System.out.println("lastscanned.ball" + getLastScanned().getBall());

		getEv().getDecision().decide(getLastScanned().getBall());
	}

	public void move(int movement) {
		setPos(getPos().add(MOVEMENT[movement]));
		if (movement < FACE.length)
			setFacing(FACE[movement]);
		addRelative(MOVEMENT[movement]);
	}

	public void addPoint(Point2D point) {
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

	@Override
	public String toString() {
		return "R" + getPos();
	}

	// ******************Getters & Setters********************
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

	public ArrayList<Point2D> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point2D> points) {
		this.points = points;
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

	public Perception getLastScanned() {
		return lastScanned;
	}

	public void setLastScanned(Perception lastScanned) {
		this.lastScanned = lastScanned;
	}

	public Decision<Actor> getDecision() {
		return decision;
	}

	public void setDecision(Decision<Actor> decision) {
		this.decision = decision;
	}

	public Evaluation<Actor> getEv() {
		return bestEvaluation;
	}

	public void setEv(Evaluation<Actor> ev) {
		this.bestEvaluation = ev;
	}
}
