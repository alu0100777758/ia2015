package es.ull.etsii.ia.interface_.Actors;

import java.util.Random;

import es.ull.etsii.ia.interface_.CoordinateSystem2D;
import es.ull.etsii.ia.interface_.geometry.Point2D;

public class Robo_Player extends Actor {
	public static final String T1 = "img/playerT1.png";	//	Path de la primera equipacion
	public static final String T2 = "img/playerT2.png";	// Path de la segunda equipacion
	
	public Robo_Player(short team, Point2D point, CoordinateSystem2D coordinates, int face ) {
		super(coordinates,face);
		setPos(point);
//		System.out.println(point);
		if(team == 0)
			setSpritePath(T1);
		else if (team == 1)
			setSpritePath(T2);
		loadSprite();
	}
	
	/**
	 * @return boolean true si ha terminado de efectuar todas sus acciones.
	 */
	public boolean tick(){
		randomMove();
		return true;
	}
	public void randomMove(){
		int random = new Random().nextInt(4);
		setPos(getPos().add(MOVEMENT[random]));
		setFacing(FACE[random]);
	}

}
