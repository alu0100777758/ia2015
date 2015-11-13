package es.ull.etsii.ia.interface_.Actors;

import java.util.Random;

import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.etsii.ia.interface_.simulation.CoordinateSystem2D;

/**
 * clase encargada de representar a un balon en el campo (con cierta ia para imitar de forma limitad las fisicas y reubicaciones)
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 */
public class Ball extends Actor {
	private int speed = 0;										//	velocidad actual del balon.
	private int direction;										//	direccion actual del balon.
	public static final String SPRITE_PATH = "img/ball.png";	//	path del sprite.

	/**
	 * @param coordinates 
	 * @param pos
	 */
	public Ball(CoordinateSystem2D coordinates, Point2D pos) {
		super(coordinates, NORTH);
		setSpritePath(SPRITE_PATH);
		loadSprite();
		setPos(pos);
	}

	/**
	 *  empuja el balon en la direccion opuesta al actor.
	 * @param actor
	 * 
	 */
	public void push(Actor actor) {
		Point2D pushVect = getPos().substract(actor.getPos());
		Point2D dest = getPos().add(pushVect);
		Vision2D vision = getMap().perceive(this);
		try {
			while (vision.get((int) dest.y(), (int) dest.x()) != null) {
				dest = dest.add(MOVEMENT[new Random()
						.nextInt(MOVEMENT.length - 1)]);
			}
			if(dest.x() < 2 || dest.y() < 2 || dest.x() >(vision.getColumns() - 2) || dest.y() >(vision.getRows()- 2)){
				dest = randomPosition(vision);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			dest = randomPosition(vision);
		}
		setPos(dest);
	}

	/**
	 * devuelve un punto aleatorio en el que se puede reubicar el balon.
	 * @param vision 
	 * @return	Point2D
	 */
	private Point2D randomPosition(Vision2D vision) {
		Point2D dest;
		Random rand = new Random();
		dest = new Point2D(rand.nextInt(vision.getColumns() -2 )+1, rand.nextInt(vision.getRows() -2 )+1);
		while (vision.get((int) dest.y(), (int) dest.x()) != null) {
			dest = new Point2D(rand.nextInt(vision.getColumns() -2 )+1, rand.nextInt(vision.getRows() -2 )+1);
		}
		return dest;
	}

	/**
	 * realiza la accion de disparar el balon a "speed" velocidad en la direccion "direction".
	 * @param speed 
	 * @param direction	
	 * 
	 */
	public void shot(int speed, int direction) {
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
		if (getSpeed() > 0) {
			for (int i = 0; i < getSpeed(); i++) {
				try {
					if (getMap().perceive(this).get(
							dest.add(MOVEMENT[getDirection()])) == null
							|| getMap().perceive(this)
									.get(dest.add(MOVEMENT[getDirection()]))
									.getClass() == Goal.class) {
						dest = dest.add(MOVEMENT[getDirection()]);
					} else {
						i = getSpeed();
						setSpeed(0);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					i = getSpeed();
					setSpeed(0);
				}
			}
			Vision2D vision = getMap().perceive(this);
			if (getPos().equals(dest)) {
				while (vision.get((int) dest.y(), (int) dest.x()) != null) {
					dest = dest.add(MOVEMENT[new Random()
							.nextInt(MOVEMENT.length - 1)]);
				}
			}
			if(dest.x() < 2 || dest.y() < 2 || dest.x() >(vision.getColumns() - 2) || dest.y() >(vision.getRows()- 2)){
				dest = randomPosition(vision);
			}
			setPos(dest);
		}
		if (getSpeed() > 0 && new Random().nextInt(10) > 5) {
			setSpeed(getSpeed() - 1);
		}
		return true;
	}
	// ******************Getters & Setters********************

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
