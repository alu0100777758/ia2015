package es.ull.etsii.ia.interface_.Actors;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import es.ull.etsii.ia.interface_.CoordinateSystem2D;
import es.ull.etsii.ia.interface_.Positionable;
import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.etsii.ia.interface_.geometry.drawable.Drawable;

public abstract class Actor implements Drawable, Positionable {
	public static final int FACE_NORTH = 0;
	public static final int FACE_EAST = 90;
	public static final int FACE_WEST = -90;
	public static final int FACE_SOUTH = 180;
	public static final int FACE [] = { FACE_NORTH, FACE_EAST, FACE_WEST, FACE_SOUTH};
	public static final Point2D MOVEMENT_SOUTH = new Point2D(0, 1);
	public static final Point2D MOVEMENT_NORTH = new Point2D(0, -1);
	public static final Point2D MOVEMENT_EAST = new Point2D(1, 0);
	public static final Point2D MOVEMENT_WEST = new Point2D(-1, 0);
	public static final Point2D MOVEMENT [] = {MOVEMENT_NORTH, MOVEMENT_EAST, MOVEMENT_WEST, MOVEMENT_SOUTH};
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int WEST = 2;
	public static final int SOUTH = 3;
	private int facing = FACE_SOUTH;
	private Point2D position;
	private BufferedImage sprite;//<----
	private String spritePath;//<----
	private static int hcellSize;
	private static int vcellSize;
	private CoordinateSystem2D coordinates;
	private ArrayList<MovementListener> movListeners = new ArrayList<>();
	public Actor(CoordinateSystem2D coordinates, int face){
		setCoordinates(coordinates);
		setFacing(face);
	}
	public CoordinateSystem2D getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(CoordinateSystem2D coordinates) {
		this.coordinates = coordinates;
	}

	public static int getHcellSize() {
		return hcellSize;
	}

	public static void setHcellSize(int hcellSize) {
		Actor.hcellSize = hcellSize;
	}

	public static int getVcellSize() {
		return vcellSize;
	}

	public static void setVcellSize(int vcellSize) {
		Actor.vcellSize = vcellSize;
	}
	
	
	@Override
	public void paint(Graphics g) {
		Point2D point = coordinates.getPointFor(getPos()).getRounded();	
		Point2D sizeMarker = diffPoint(point);
		g.drawImage(rotate(getSprite(),facing),(int)point.x(),(int)point.y(),(int)sizeMarker.x(),(int)sizeMarker.y(),null);
	}
	public Point2D diffPoint(Point2D pos){
		Point2D destPoint = coordinates.getPointFor(getPos().add(Point2D.UNIT));
		return destPoint.substract(pos).getRounded();
	}
	public void addMovListener( MovementListener listener){
		getMovListeners().add(listener);
	}
	@Override
	public Point2D getPos() {
		return position;
	}

	@Override
	public void setPos(Point2D newpos) {
		this.position = newpos;
		for(MovementListener listener : getMovListeners())
			listener.updated();
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
	public void loadSprite(){
		try {
			setSprite(ImageIO.read(new File(getSpritePath())));
		} catch (IOException e) {
			System.out.println("failed at load");
			e.printStackTrace();
		}
	}
	public BufferedImage rotate(BufferedImage image, int grades){
		double rotationRequired = Math.toRadians (grades);
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image, null);
	}
	// ******************Getters & Setters********************
	public int getFacing() {
		return facing;
	}
	public void setFacing(int facing) {
		this.facing = facing;
	}
	public boolean tick(){
		return true;
	}
	public ArrayList<MovementListener> getMovListeners() {
		return movListeners;
	}
	public void setMovListeners(ArrayList<MovementListener> movListeners) {
		this.movListeners = movListeners;
	}
	
}
