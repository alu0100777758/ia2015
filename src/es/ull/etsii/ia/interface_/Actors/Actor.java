package es.ull.etsii.ia.interface_.Actors;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.etsii.ia.interface_.geometry.drawable.Drawable;
import es.ull.etsii.ia.interface_.simulation.CoordinateSystem2D;

/**
 * clase encargada de representar a un actor (agente capaz de actuar sobre el medio).
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 */
public abstract class Actor implements Drawable, Positionable {
	public static final int FACE_NORTH = 0;										//	rotacion hacia el norte
	public static final int FACE_EAST = 90;										//	rotacion hacia el este
	public static final int FACE_WEST = -90;									//	rotacion hacia el oeste
	public static final int FACE_SOUTH = 180;									//	rotacion hacia el sur
	public static final Point2D MOVEMENT_NORTH = new Point2D(0, -1);			//	movimiento hacia el norte
	public static final Point2D MOVEMENT_EAST = new Point2D(1, 0);				//	movimiento hacia el este
	public static final Point2D MOVEMENT_WEST = new Point2D(-1, 0);				//	movimiento hacia el oeste
	public static final Point2D MOVEMENT_SOUTH = new Point2D(0, 1);				//	movimiento hacia el sur
	public static final Point2D MOVEMENT_CENTER = new Point2D(0, 0);			//	ausencia de movimiento
	public static final int[] FACE = { FACE_NORTH, FACE_EAST, FACE_WEST,
			FACE_SOUTH };														//	lista con todas las direcciones basicas hacia las que es posible orientarse
	public static final Point2D[] MOVEMENT = { MOVEMENT_NORTH, MOVEMENT_EAST,
			MOVEMENT_WEST, MOVEMENT_SOUTH, MOVEMENT_CENTER };					//	lista con todos los movimientos basicos que se pueden realizar.
	public static final int NORTH = 0;											//	abstrae en un entero el concepto "norte" (para acceder desde las listas).
	public static final int EAST = 1;											//	abstrae en un entero el concepto "este" (para acceder desde las litsas).
	public static final int WEST = 2;											//	abstrae en un entero el concepto "oeste" (para acceder desde las listas).
	public static final int SOUTH = 3;											//	abstrae en un entero el concepto "sur" (para acceder desde las listas).
	public static final int CENTER = 4;											//	abstrae en un entero el concepto "centro" (para acceder desde las listas).
	private int facing = FACE_SOUTH;											//	direccion hacia la que mira actualmente el actor.
	private Point2D position;													//	posicion actual del actor.
	private BufferedImage sprite;												//	imagen en memoria usada para representar graficamente al actor.
	private String spritePath;													//	path de la imagen
	private SensitiveEnviroment map;											//	mapa que permite obtener percepciones en el que se encuentra el actor.
	private CoordinateSystem2D coordinates;										//	sistema de coordenadas usadas por el actor.
	private ArrayList<MovementListener> movListeners = new ArrayList<>();		//	entidades que han de ser notificadas ante la realizacion de un movimiento.

	/**
	 * @param coordinates
	 * @param face
	 */
	public Actor(CoordinateSystem2D coordinates, int face) {
		setCoordinates(coordinates);
		setFacing(face);
	}


	/**
	 * @param pos
	 * @return punto con las dimensiones de la celda en el punto pos
	 */
	public Point2D diffPoint(Point2D pos) {
		Point2D destPoint = coordinates.getPointFor(pos.add(Point2D.UNIT));
		return destPoint.substract(coordinates.getPointFor(pos)).getRounded();
	}


	/**
	 * carga en memoria desde fichero la imagen del sprite
	 */
	public void loadSprite() {
		try {
			setSprite(ImageIO.read(new File(getSpritePath())));
		} catch (IOException e) {
			System.out.println("failed at load");
			e.printStackTrace();
		}
	}

	/**
	 * @param image imagen fuente
	 * @param grades grados a rotar
	 * @return	la imagen "image" rotada "grades grados
	 */
	public BufferedImage rotate(BufferedImage image, int grades) {
		double rotationRequired = Math.toRadians(grades);
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(
				rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image, null);
	}
	/**
	 * @param listener
	 *  añade un nuevo MovementListener.
	 */
	public void addMovListener(MovementListener listener) {
		getMovListeners().add(listener);
	}

	@Override
	public void paint(Graphics g) {
		Point2D point = coordinates.getPointFor(getPos()).getRounded();
		Point2D sizeMarker = diffPoint(point);
		g.drawImage(rotate(getSprite(), facing), (int) point.x(),
				(int) point.y(), (int) sizeMarker.x(), (int) sizeMarker.y(),
				null);
	}
	// ******************Getters & Setters********************
	@Override
	public Point2D getPos() {
		return position;
	}

	@Override
	public void setPos(Point2D newpos) {
		for (MovementListener listener : getMovListeners()) {
			listener.update(getPos(), newpos);
		}
		this.position = newpos;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}

	public int getFacing() {
		return facing;
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}

	public boolean tick() {
		return true;
	}

	public ArrayList<MovementListener> getMovListeners() {
		return movListeners;
	}

	public void setMovListeners(ArrayList<MovementListener> movListeners) {
		this.movListeners = movListeners;
	}

	public CoordinateSystem2D getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(CoordinateSystem2D coordinates) {
		this.coordinates = coordinates;
	}

	public SensitiveEnviroment getMap() {
		return map;
	}

	public void setMap(SensitiveEnviroment map) {
		this.map = map;
	}

}
