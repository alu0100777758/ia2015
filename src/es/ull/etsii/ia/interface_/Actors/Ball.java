package es.ull.etsii.ia.interface_.Actors;

import es.ull.etsii.ia.interface_.CoordinateSystem2D;
import es.ull.etsii.ia.interface_.geometry.Point2D;

public class Ball extends Actor {
	public static final String SPRITE_PATH = "img/ball.png";
	public Ball(CoordinateSystem2D coordinates, Point2D pos) {
		super(coordinates, NORTH);
		setSpritePath(SPRITE_PATH);
		loadSprite();
		setPos(pos);
	}

}
