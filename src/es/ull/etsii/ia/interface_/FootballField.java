package es.ull.etsii.ia.interface_;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.text.AbstractDocument.BranchElement;

import es.ull.etsii.ia.interface_.Actors.Actor;
import es.ull.etsii.ia.interface_.Actors.Ball;
import es.ull.etsii.ia.interface_.Actors.MovementListener;
import es.ull.etsii.ia.interface_.Actors.SensitiveEnviroment;
import es.ull.etsii.ia.interface_.Actors.Vision2D;
import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.utils.Array2D;

/**
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 * 		   Clase encargada de almacenar y dibujar un estado en base a un objeto que presente la interfaz
 *         CoordinateSystem2D.
 */
public class FootballField extends GridPanel implements SensitiveEnviroment, MovementListener {
	private Array2D<Actor> mapState; // Matriz donde se almacena la visión
										// global del
	// mapa.
	private Color color;
//	private Point2D workingSize;
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private int turnPointer = 0;
	private Ball ball ;
	public FootballField() {
		setColor(Color.RED);
		resetMap();
	}

	private void resetMap() {
		setMapState(new Array2D<Actor>(getVBounds()-1, getHBounds()-1));
	}

	public void addActor(Actor actor){
		getActors().add(actor);
		actor.addMovListener(this);
		getMapState().set((int)actor.getPos().y(),(int)actor.getPos().x(), actor);
	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g.create();
		g2D.setColor(color);
			drawLines(g2D);
			drawGoals((Graphics2D)g2D.create());
		for (Actor act : getActors())
			act.paint(g2D);
		if(getBall() != null)
			getBall().paint(g);
	}

	private void drawGoals(Graphics2D g) {
		int size = getVBounds()/3;
		int ypos = getVBounds()-size % 2 == 0 ? (getVBounds()-size)/2 : (getVBounds() - 1 - size )/2; 
		Point2D topLeft = getPointFor(0, ypos);
		Point2D bottomLeft = new Point2D(getCellCenter(new Point2D(1,0)).x(),getPointFor(new Point2D(0,ypos+size)).y());
		Point2D topRight = 	new Point2D(getCellCenter(new Point2D(getHBounds()-3,0)).x(),getPointFor(new Point2D(0,ypos)).y());
		Point2D bottomRight = getPointFor(getHBounds()-1, ypos + size);
		g.setColor(new Color(1, 1, 1, (float)0.7)); // TODO limpiar
		g.fillRect((int)topLeft.x(), (int)topLeft.y(), (int)(bottomLeft.x()-topLeft.x()), (int)(bottomLeft.y() - topLeft.y()));
		g.fillRect((int)topRight.x(), (int)topRight.y(), (int)(bottomRight.x()-topRight.x()), (int)(bottomRight.y() - topRight.y()));

	}

	private void drawLines(Graphics2D gr) {
		Graphics2D g = (Graphics2D)gr.create();
		g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(Color.WHITE); 
		Point2D topleft = getCellCenter(new Point2D(1, 1));
		Point2D bottonRight = getCellCenter(new Point2D(getHBounds()-3, getVBounds()-3));
		g.drawRect((int)topleft.x(), (int)topleft.y(), (int)(bottonRight.x()-topleft.x()),(int)(bottonRight.y()-topleft.y()));
	}

	/**
	 * Metodo encargado de distribuir el tiempo de ejecución a cada agente.
	 */
	public void tick() {
		if(getBall() != null)
			getBall().tick();
		if (!getActors().isEmpty() && getActors().get(getTurnPointer()).tick())
			incrementTurn();

	}

	private void incrementTurn() {
		turnPointer++;
		if (getTurnPointer() >= getActors().size())
			setTurnPointer(0);
	}
	
	@Override
	public Vision2D getVision(Actor sensor) {
		Vision2D vision;
//		System.out.println(getMapState());
		if(sensor.getClass() == Ball.class){
			return new Vision2D(getMapState(), sensor.getPos());
		}
		switch (sensor.getFacing()) {
		case Actor.FACE_NORTH:
			System.out.println("north");
			vision = new Vision2D(getMapState().copy(0, 0,(int)sensor.getPos().y(), getMapState().getColumns()-1), sensor.getPos());
			break;
		case Actor.FACE_SOUTH: 
			System.out.println("south");
			vision = new Vision2D(getMapState().copy( (int)sensor.getPos().y(),0, getMapState().getRows()-1, getMapState().getColumns()-1), new Point2D(sensor.getPos().x(), 0));
			break;
		case Actor.FACE_EAST:
			System.out.println("East");
			vision = new Vision2D(getMapState().copy(0, (int)sensor.getPos().x(), getMapState().getRows()-1,getMapState().getColumns()-1), new Point2D(0, sensor.getPos().y()));
			break;
		case Actor.FACE_WEST:
			System.out.println("West");
			vision = new Vision2D(getMapState().copy( 0,0, getMapState().getRows()-1,(int)sensor.getPos().x() ), sensor.getPos());
		break;
		default:
			vision = null;
			break;
		}
		return vision;
	}

	public void reset() {
		resetMap();
		getActors().clear();
		setTurnPointer(0);
		setBall(null);
		repaint();
	}
	public void moveBall(Point2D newpos){
		if(getBall() != null){
			getMapState().switchElements((int)getBall().getPos().y(), (int)getBall().getPos().x(), (int)newpos.y(), (int)newpos.x());
			getBall().setPos(newpos);
		}
		
	}
	// ******************Getters & Setters********************


	public void setColor(Color color) {
		this.color = color;
	}

	public int getTurnPointer() {
		return turnPointer;
	}

	public void setTurnPointer(int turnPointer) {
		this.turnPointer = turnPointer;
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
	public void update(Point2D oldPos, Point2D newPos) {
		System.out.println("switching from " + oldPos +" to "+ newPos);
		getMapState().switchElements((int)oldPos.y(), (int)oldPos.x(), (int)newPos.y(), (int)newPos.x());
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
		if(ball != null){
			ball.addMovListener(this);
			getMapState().set((int)ball.getPos().y(), (int)ball.getPos().x(), ball);
		}
	}
	
}
