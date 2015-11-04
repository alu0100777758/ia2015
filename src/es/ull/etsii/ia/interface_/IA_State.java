package es.ull.etsii.ia.interface_;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.text.AbstractDocument.BranchElement;

import es.ull.etsii.ia.interface_.Actors.Actor;
import es.ull.etsii.ia.interface_.Actors.SensitiveEnviroment;
import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.utils.Array2D;

/**
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 * 		   Clase encargada de almacenar y dibujar un estado en base a un objeto que presente la interfaz
 *         CoordinateSystem2D.
 */
public class IA_State extends GridPanel implements SensitiveEnviroment {
	private Array2D<Actor> mapState; // Matriz donde se almacena la visión
										// global del
	// mapa.
	private Color color;
//	private Point2D workingSize;
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private int turnPointer = 0;

	public IA_State() {
		setColor(Color.RED);
		resetMap();
	}

	private void resetMap() {
		setMapState(new Array2D<Actor>(getVBounds()-1, getHBounds()-1));
	}

	public void addActor(Actor actor){
		getActors().add(actor);
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
		if (!getActors().isEmpty() && getActors().get(getTurnPointer()).tick())
			incrementTurn();

	}

	private void incrementTurn() {
		turnPointer++;
		if (getTurnPointer() >= getActors().size())
			setTurnPointer(0);
	}
	
	@Override
	public Array2D<Actor> getVision(Actor sensor) {
		Array2D<Actor> vision;
		System.out.println(getMapState());
		switch (sensor.getFacing()) {
		case Actor.FACE_NORTH:
			System.out.println("north");
			vision = getMapState().copy(0, 0,(int)sensor.getPos().y(), getMapState().getRows()-1);
			break;
		case Actor.FACE_SOUTH: 
			System.out.println("south");
			vision = getMapState().copy( (int)sensor.getPos().y(),0, getMapState().getRows()-1, getMapState().getColumns()-1);
			break;
		case Actor.FACE_EAST:
			vision = getMapState().copy(0, 0, getMapState().getRows()-1, (int)sensor.getPos().x());
			break;
		case Actor.FACE_WEST:
			vision = getMapState().copy( (int)sensor.getPos().y(),0, getMapState().getRows()-1, getMapState().getColumns()-1);
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
		repaint();
	}
	// fromgridstatus
//	void updatePath(){
//		path.clear();
//		path.setStart(new Point2D(gethPoints()/2,getvPoints()/2));
//	}
//	public void setvPoints(int vPoints) {
//		super.setvPoints(vPoints);
//		if(pathIsBroken()){
//			updatePath();
//		}
//	}
//	public void setStart(Point2D point){
//		path.setStart(point);
//	}
//	public void sethPoints(int hPoints) {
//		super.sethPoints(hPoints);
//		if(pathIsBroken()){
//			updatePath();
//		}
//	}
//	public boolean pathIsBroken(){
//		if(state == null)
//			return false;
////		return (path.isAtBorder() || path.isOut());
//		return true;
//	}
//	public boolean atBorder(){
//		return path.isAtBorder();
//	}
//	public void pathColor(Color color){
//		setColor(color);
//	}
//	public void turnOnPath(){
////		GridPanel grid = this;
////		state = new IA_State(grid);
//		pathON = true;
////		updatePath();
//	}
//	public void paint(Graphics g){
//		super.paint(g);
//		if(pathON)
//			drawState(g);
//	}
//	public void paintComponent(Graphics g){
//		super.paintComponent(g);
//		g.setColor(Color.GREEN);
//		if(pathON)
//			path.drawPath(g);
//	}
	// ******************Getters & Setters********************
//	public IA_State getState() {
//		return state;
//	}
//	public void setState(IA_State state) {
//		this.state = state;
//	}
//	public boolean isPathON() {
//		return pathON;
//	}
//	public void setPathON(boolean pathON) {
//		this.pathON = pathON;
//	}
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

//	public Point2D getWorkingSize() {
//		return workingSize;
//	}
//
//	public void setWorkingSize(Point2D workingSize) {
//		this.workingSize = workingSize;
//	}

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
}
