package es.ull.etsii.ia.interface_;

public class Sideline extends Obstacle {
	public static final String SPRITE_PATH = "img/sideLine.png";
	public Sideline(CoordinateSystem2D coordinates) {
		super(coordinates);
		setSpritePath(SPRITE_PATH);
		loadSprite();
	}

}
