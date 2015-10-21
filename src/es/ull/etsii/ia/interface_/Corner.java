package es.ull.etsii.ia.interface_;

public class Corner extends Obstacle {
	public static final String SPRITE_PATH = "img/corner.png";
	public Corner(CoordinateSystem2D coordinates, Point2D pos, int face) {
		super(coordinates, face);
		setSpritePath(SPRITE_PATH);
		loadSprite();
		setPos(pos);
	}

}
