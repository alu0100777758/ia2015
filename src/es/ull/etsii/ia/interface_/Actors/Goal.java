package es.ull.etsii.ia.interface_.Actors;

import java.awt.Color;
import java.awt.Graphics;

import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.etsii.ia.interface_.geometry.drawable.DrawableCircle;
import es.ull.etsii.ia.interface_.simulation.CoordinateSystem2D;

/**
 * @author 
 *	Clase encargada de representar las celda de la porteria.
 */
public class Goal extends Actor {
	int team; 			//	equipo al que pertenece la porteria.
	public Goal(CoordinateSystem2D coordinates, int team, Point2D pos) {
		super(coordinates, Actor.NORTH);
		setPos(pos);
		setTeam(team);
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		new DrawableCircle(3, getCoordinates().getCellCenter(getPos()), false);
	}
	@Override
	public String toString() {
		return "G"+getTeam();
	}
	
	public int getTeam() {
		return team;
	}
	public void setTeam(int team) {
		this.team = team;
	}
	

}
