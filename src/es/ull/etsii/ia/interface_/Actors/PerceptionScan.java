package es.ull.etsii.ia.interface_.Actors;

import java.util.ArrayList;

import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.utils.Distance;

/**
 * clase encargada de representar el resultado del scaneo de una percepcion.
 * @author Javier Martin Hernandez y Tomas Rodriguez.
 *
 */
public class PerceptionScan {
	private ArrayList<Robo_Player> ally = new ArrayList<>();			//	lista de aliados encontrados
	private ArrayList<Robo_Player> foe = new ArrayList<>();				//	lista de enemigos encontrados
	private Ball ball;													//	pelota encontrada
	private ArrayList<Goal> goal = new ArrayList<>();					//	porterias encontradas
	
	/**
	 * devuelve el minimo de las distancias manhattan entre la posicion "pos" y la porteria enemiga del equipo "team"
	 * @param pos
	 * @param team
	 * @return int
	 */
	public int distanceToEnemyGoal(Point2D pos, int team){
		int minDist = Integer.MAX_VALUE;
		for(Goal goal : getGoal()){
			if(goal.getTeam() != team ){
				int dist = Distance.manhattan(pos, goal.getPos());
				if(dist < minDist)
					minDist = dist;
			}	
		}
		return minDist;
	}
	/**
	 * devuelve la suma de todas las distancias desde el punto "pos" a cada aliado.
	 * @param pos
	 * @return
	 */
	public int distanceToAllies(Point2D pos){
		int distance = 0;
		for(Robo_Player ally : getAlly())
			distance += Distance.manhattan(ally.getPos(), pos);
		return distance;
	}
	/**
	 * devuelve la suma de todas las distancias desde el punto "pos" a cada enemigo.
	 * @param pos
	 * @return
	 */
	public int distanceToFoes(Point2D pos){
		int distance = 0;
		for(Robo_Player foe : getFoe())
			distance += Distance.manhattan(foe.getPos(), pos);
		return distance;
	}
	/**
	 * Distancia del balon al punto "pos".
	 * @param pos
	 * @return
	 */
	public int distanceToBall(Point2D pos){
		if(getBall() != null)
			return Distance.manhattan(getBall().getPos(), pos);
		return -1;
	}
	// ******************Getters & Setters********************
	public ArrayList<Goal> getFoeGoal(int team){
		ArrayList<Goal> foes = new ArrayList<>();
		for(Goal goal : getGoal()){
			if(goal.getTeam() != team)
				foes.add(goal);
		}
		return foes;
	}
	public void addAlly(Robo_Player ally){
		getAlly().add(ally);
	}
	public void addFoe(Robo_Player foe){
		getFoe().add(foe);
	}
	public void addGoal(Goal goal){
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
	public ArrayList<Goal> getGoal() {
		return goal;
	}
	public void setGoal(ArrayList<Goal> goal) {
		this.goal = goal;
	}
	
}
