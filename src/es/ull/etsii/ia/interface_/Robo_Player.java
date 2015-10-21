package es.ull.etsii.ia.interface_;

public class Robo_Player extends Actor {
	public static final String T1 = "img/playerT1.png";	//	Path de la primera equipacion
	public static final String T2 = "img/playerT2.png";	// Path de la segunda equipacion
	
	public Robo_Player(short team, Point2D point, CoordinateSystem2D coordinates, int face ) {
		super(coordinates,face);
		setPos(point);
		System.out.println(point);
		if(team == 0)
			setSpritePath(T1);
		else if (team == 1)
			setSpritePath(T2);
		loadSprite();
	}
	
	/**
	 * @return boolean true si ha terminado de efectuar todas sus acciones.
	 */
	boolean tick(){
		return false;
	}

}
