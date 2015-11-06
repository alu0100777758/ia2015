package es.ull.etsii.ia.interface_.Actors;

import java.lang.reflect.Array;
import java.util.ArrayList;

import es.ull.etsii.ia.interface_.geometry.Point2D;

public class ViewElements {
	private ArrayList<Robo_Player> ally = new ArrayList<>();
	private ArrayList<Robo_Player> foe = new ArrayList<>();
	private Ball ball;
	private ArrayList<Point2D> goal = new ArrayList<>();
	public void addAlly(Robo_Player ally){
		getAlly().add(ally);
	}
	public void addFoe(Robo_Player foe){
		getFoe().add(foe);
	}
	public void addGoal(Point2D goal){
		getGoal().add(goal);
	}
	public ArrayList<Robo_Player> getAlly() {
		return ally;
	}
	public void setAlly(ArrayList<Robo_Player> ally) {
		this.ally = ally;
	}
	public ArrayList<Robo_Player> getFoe() {
		return foe;
	}
	public void setFoe(ArrayList<Robo_Player> foe) {
		this.foe = foe;
	}
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	public ArrayList<Point2D> getGoal() {
		return goal;
	}
	public void setGoal(ArrayList<Point2D> goal) {
		this.goal = goal;
	}
	
}
