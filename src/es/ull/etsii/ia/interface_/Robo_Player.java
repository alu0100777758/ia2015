package es.ull.etsii.ia.interface_;

import java.awt.Graphics;

public class Robo_Player extends Actor {
	public static final String T1 = "img/playerT1.png";
	public static final String T2 = "img/playerT2.png";
	
	public Robo_Player(short team, Point2D point, CoordinateSystem2D coordinates) {
		super(coordinates);
		setPos(point);
		System.out.println(point);
		if(team == 0)
			setSpritePath(T1);
		else if (team == 1)
			setSpritePath(T2);
		loadSprite();
	}

}
