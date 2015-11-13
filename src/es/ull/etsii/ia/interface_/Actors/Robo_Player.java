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

/**
 * clase encargada de representar a un agente de la colmena.
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 */
public class Robo_Player extends Actor {
	private static final int SHOT_SPEED = 2;							//	Velocidad aplicada al disparo.
	public static final String T1 = "img/playerT1.png"; 				//	Path de la primera equipacion.
	public static final String T2 = "img/playerT2.png"; 				//	Path de la segunda equipacion.
	private int team = 0;												//	codigo de equipo al que pertenece.
	private ArrayList<Point2D> points = new ArrayList<Point2D>();		//	Lista de puntos visitados durante la simulacion.
	private ArrayList<Drawable> evaluated = new ArrayList<Drawable>();	//	Lista de representacion de elementos evaluados 
	private boolean evaluationMode = true;								//	true si se encuentra en modo evaluacion.
	private Vision2D view;												//	ultima percepcion obtenida.
	private HiveMemory memory;											//	Memoria en comun con todo su equipo
	private PerceptionScan lastScanned;									//	escaneo de la ultima percepcion.
	private Evaluation<Actor> bestEvaluation;							//	mejor evaluacion del ultimo proceso de evaluacion.
	private Action<Actor> moveD = (actor) -> {							//	accion de movimiento.
		move(getBestEvaluation().getPos());
		if (getMemory().getBallOwner() == this) {
			getMemory().setBallOwner(null);
			getMemory().setAttackState(false);
		}
	};
	private Action<Actor> pushMove = (actor) -> {						//	accion de avanzar con el balon.
		Ball ball = (Ball) actor;
		pushBall(ball);
		moveD.decide(actor);
		getMemory().setAttackState(true);
		getMemory().setBallOwner(this);
	};
	private Action<Actor> shoot = (actor) -> {							//	accion de disparar.
		Ball ball = (Ball) actor;
		shootBall(ball, getBestEvaluation().getPos());
	};


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
	 * devuelve true si ha terminado de efectuar todas sus acciones realiza las acciones necesarias en este turno.
	 * @return boolean 
	 */
	public boolean tick() {
		if (evaluationMode) {
			evaluate();
		} else
			step();
		evaluationMode = !evaluationMode;
		return evaluationMode;
	}

	/**
	 * realiza la evaluacion de los estados visitables.(profundidad 1)
	 */
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
		setBestEvaluation(minev);
	}

	/**
	 * devuelve true si no hay nada que impida el desplazamiento en esa direccion.
	 * @param direction 
	 * @return	boolean
	 */
	boolean canBeEvaluated(int direction) {
		return (!isOutOfBounds(direction) && ((getView().get(
				getView().getRelativePos().add(MOVEMENT[direction])) == null
				|| getView().get(getView().getRelativePos().add(MOVEMENT[direction]))
						.getClass() == Ball.class || getView().get(
				getView().getRelativePos().add(MOVEMENT[direction])) == this)));
	}

	/**
	 * devuelve true si la direccion se encuentra fuera del sistema de coordenadas.
	 * @param direction 
	 * @return boolean
	 */
	private boolean isOutOfBounds(int direction) {
		Point2D pos = getView().getRelativePos().add(MOVEMENT[direction]);
		return (pos.x() < 0 || pos.y() < 0
				|| pos.x() >= (getCoordinates().getHBounds() - 2) || pos.y() >= (getCoordinates()
				.getVBounds() - 3));
	}

	/**
	 * devuelve direccion en la que el agente no percibe informacion (y por lo tanto no puede moverse a ella)
	 * @return int 
	 */
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

	/**
	 * devuelve la evaluacion del estado representado por la direccion pos.
	 * @param elements
	 * @param pos
	 * @return Evaluation.
	 */
	private Evaluation<Actor> evaluatePoint(PerceptionScan elements, int pos) {
		if (getMemory().isAttackState())
			return evaluateAtt(elements, pos);
		return evaluateDef(elements, pos);
	}

	/**
	 * devuelve la evaluacion de un estado de defensa.
	 * @param elements
	 * @param pos
	 * @return Evaluation.
	 */
	private Evaluation<Actor> evaluateDef(PerceptionScan elements, int pos) {
		Evaluation<Actor> ev = new Evaluation<Actor>();
		ev.setPos(pos);
		ev.setDecision(moveD);
		Point2D position = getPos().add(MOVEMENT[pos]);
		int value = 1;
		if (elements.getBall() != null) {
			double manlength = elements.distanceToBall(position);
			value += manlength * getMemory().getHiveSize();			// mas cerca de la pelota es mejor
			if (manlength == 0) {
				ev.setDecision(pushMove);
			} 
		} else
		value += elements.distanceToFoes(position);					//	mas cerca de los enemigos es mejor ( entorpecer su ataque / defensa)
		for (Robo_Player ally : elements.getAlly()) {
			if (ally != this) {
				value -= Distance.manhattan((int) ally.getPos().x(), (int) ally
						.getPos().y(), (int) position.x(), (int) position.y());		//	alejarse de los aliados es mejor (cubrir mas campo)
				if (elements.getBall() != null) {
					turnFriend(ally, elements.getBall());
				}
			}
		}
		ev.setValue(value);
		return ev;
	}

	/**
	 * devuelve la evaluacion de un estado de ataque.
	 * @param elements
	 * @param pos
	 * @return evaluation.
	 */
	private Evaluation<Actor> evaluateAtt(PerceptionScan elements, int pos) {
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

	/**
	 * Rota el aliado "ally" para que mire hacia el actor "actor"
	 * @param ally
	 * @param actor
	 */
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

	/**
	 * incluye en la lista de representacion de evaluaciones un circulo sobre el aliado rotado.
	 * @param ally
	 */
	private void addTurned(Robo_Player ally) {
		getEvaluatedPoints().add(
				new DrawableCircle(getCoordinates().getHsize(),
						getCoordinates().getCellCenter(ally.getPos()), false));
	}

	/**
	 * devuelve el resultado de escanear la percepcion actual.
	 * @return PerceptionScan
	 */
	private PerceptionScan scanView() {
		setView(getMap().perceive(this));
		PerceptionScan elements = new PerceptionScan();
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

	/**
	 * devuelve un color en base a num respecto maxRange.
	 * @param num
	 * @param maxrange
	 * @return Color
	 */
	public Color colorFromNum(double num, double maxrange) {
		if (maxrange == 0) {
			maxrange = 1;
		}
		double h = 1 - (num / maxrange * 0.7); // codigo hue
		double s = 1; // saturacion
		double b = 1; // brillo
		return Color.getHSBColor((float) h, (float) s, (float) b);
	}


	/**
	 * avanza a otro estado.
	 */
	public void step() {
		getBestEvaluation().getDecision().decide(getLastScanned().getBall());
	}

	/**
	 * devuelve direccion en la que se movera el agente.
	 * @param movement 
	 */
	public void move(int movement) {
		setPos(getPos().add(MOVEMENT[movement]));
		if (movement < FACE.length)
			setFacing(FACE[movement]);
		addRelative(MOVEMENT[movement]);
	}


	/**
	 * incluye el siguiente punto visitado
	 * @param point 
	 */
	public void addRelative(Point2D point) {
		addPoint(points.get(points.size() - 1).add(point));
	}

	/**
	 * situa el inicio de la ruta.
	 * @param point 
	 */
	public void setStart(Point2D point) {
		if (points.size() < 1)
			points.add(point);
		else
			points.set(0, point);
	}

	/**
	 * borra la ruta.
	 */
	public void clear() {
		points.clear();
	}


	/**
	 * empuja el balon
	 * @param ball
	 */
	public void pushBall(Ball ball) {
		ball.push(this);
	}
	
	/**
	 * dispara el balon.
	 * @param ball
	 * @param direction
	 */
	private void shootBall(Ball ball, int direction) {
		ball.shot(SHOT_SPEED, direction);
	}
	@Override
	public void paint(Graphics goriginal) {
		loadSprite();
		Graphics2D g = (Graphics2D) goriginal.create();
		drawPath(g);
		super.paint(g);
		if (!evaluationMode) {
			for (Drawable rect : getEvaluatedPoints()) {
				rect.paint(g);
			}
		}
	}
	
	/**
	 * dibuja la ruta que ha seguido el agente durante la simulacion.
	 * @param g
	 */
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
	@Override
	public String toString() {
		return "R" + getPos();
	}
	// ******************Getters & Setters********************

	public void addPoint(Point2D point) {
		points.add(point);
	}
	
	public Point2D getLast() {
		return points.get(points.size() - 1);
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


	public PerceptionScan getLastScanned() {
		return lastScanned;
	}

	public void setLastScanned(PerceptionScan lastScanned) {
		this.lastScanned = lastScanned;
	}

	public Evaluation<Actor> getBestEvaluation() {
		return bestEvaluation;
	}

	public void setBestEvaluation(Evaluation<Actor> ev) {
		this.bestEvaluation = ev;
	}

	public boolean isEvaluationMode() {
		return evaluationMode;
	}

	public void setEvaluationMode(boolean evaluationMode) {
		this.evaluationMode = evaluationMode;
	}
	
}
