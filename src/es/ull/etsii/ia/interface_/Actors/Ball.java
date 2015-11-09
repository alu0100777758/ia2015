package es.ull.etsii.ia.interface_.Actors;

import es.ull.etsii.ia.interface_.CoordinateSystem2D;
import es.ull.etsii.ia.interface_.geometry.Point2D;

public class Ball extends Actor {
	private int speed = 0;
	public static final String SPRITE_PATH = "img/ball.png";
	public Ball(CoordinateSystem2D coordinates, Point2D pos) {
		super(coordinates, NORTH);
		setSpritePath(SPRITE_PATH);
		loadSprite();
		setPos(pos);
	}
	public void push(Actor actor){
		Point2D pushVect = getPos().substract(actor.getPos());
		Point2D dest = actor.getPos().add(pushVect);
		Vision2D vision = getMap().getVision(this);
		System.out.println("entra al push");
		if(vision.get((int)dest.y(), (int)dest.x()) == null){
			setPos(dest);
			System.out.println( " DEST = " + dest);
		}
	}
	@Override
	public String toString() {
		return "B";
	}
	@Override
	public boolean tick() {
		// TODO Auto-generated method stub
		return super.tick();
	}
	

}
