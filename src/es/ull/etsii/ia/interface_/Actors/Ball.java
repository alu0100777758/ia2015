package es.ull.etsii.ia.interface_.Actors;

import java.awt.Point;
import java.util.Random;

import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.etsii.ia.interface_.simulation.CoordinateSystem2D;

public class Ball extends Actor {
	private int speed = 0;
	private int direction;
	public static final String SPRITE_PATH = "img/ball.png";
	public Ball(CoordinateSystem2D coordinates, Point2D pos) {
		super(coordinates, NORTH);
		setSpritePath(SPRITE_PATH);
		loadSprite();
		setPos(pos);
	}
	public void push(Actor actor){
		Point2D pushVect = getPos().substract(actor.getPos());
		Point2D dest = getPos().add(pushVect);
		Vision2D vision = getMap().perceive(this);
		while(vision.get((int)dest.y(), (int)dest.x()) != null){
			dest = dest.add(MOVEMENT[new Random().nextInt(MOVEMENT.length - 1)]);
		}
		setPos(dest);
	}
	public void shot(int speed, int direction){
		setSpeed(speed);
		setDirection(direction);
		tick();
	}
	@Override
	public String toString() {
		return "B";
	}
	@Override
	public boolean tick() {
		Point2D dest = getPos();
		if(getSpeed() > 0){
			for(int i = 0 ; i < getSpeed(); i++){
				try{
				if(getMap().perceive(this).get(dest.add(MOVEMENT[getDirection()])) == null || getMap().perceive(this).get(dest.add(MOVEMENT[getDirection()])).getClass() == Goal.class ){
					dest = dest.add(MOVEMENT[getDirection()]);
				}else{
					i = getSpeed();
					setSpeed(0);
				}}catch (ArrayIndexOutOfBoundsException e) {
					i = getSpeed();
					setSpeed(0);
				}
			}
			if(getPos().equals(dest)){
				Vision2D vision = getMap().perceive(this);
				while(vision.get((int)dest.y(), (int)dest.x()) != null){
					dest = dest.add(MOVEMENT[new Random().nextInt(MOVEMENT.length - 1)]);
				}
			}
			setPos(dest);
		}
			if(getSpeed() > 0 && new Random().nextInt(10) > 5){
				setSpeed(getSpeed() - 1);
			}
		return true;
		
//		Point2D dest = getPos().add(MOVEMENT[getDirection()].scalarProduct(getSpeed()));
//		if(getSpeed() > 0 && (getMap().perceive(this).get(dest) == null )){
//			setPos(dest);
//			if(new Random().nextInt(10) > 5){
//				setSpeed(getSpeed() - 1);
//			}
//		}else
//			setSpeed(0);
//		return true;
	}
	
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	

}
