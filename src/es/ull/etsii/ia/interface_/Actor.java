package es.ull.etsii.ia.interface_;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Actor implements Drawable, Positionable {
	public static final int NORTH = 0;
	public static final int EAST = 90;
	public static final int WEST = -90;
	public static final int SOUTH = 180;
	private int facing = SOUTH;
	private Point2D position;
	private BufferedImage sprite;//<----
	private String spritePath;//<----
	private static int hcellSize;
	private static int vcellSize;
	private CoordinateSystem2D coordinates;
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
		// TODO usar coordinates para todos los argumentos del drawImage
		System.out.println("pintando en: " + point + "desde :" + getPos());
		Point2D destPoint = coordinates.getPointFor(getPos().add(Point2D.UNIT));
		Point2D sizeMarker = destPoint.substract(point).getRounded();
		g.drawImage(rotate(getSprite(),facing),(int)point.x(),(int)point.y(),(int)sizeMarker.x(),(int)sizeMarker.y(),null);
	}

	@Override
	public Point2D getPos() {
		return position;
	}

	@Override
	public void setPos(Point2D newpos) {
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
	public int getFacing() {
		return facing;
	}
	public void setFacing(int facing) {
		this.facing = facing;
	}
}
