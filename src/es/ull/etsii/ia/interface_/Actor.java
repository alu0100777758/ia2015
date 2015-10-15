package es.ull.etsii.ia.interface_;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Actor implements Drawable, Positionable {
	private Point2D position;
	private BufferedImage sprite;
	private String spritePath;
	private static int hcellSize;
	private CoordinateSystem2D coordinates;
	public Actor(CoordinateSystem2D coordinates){
		setCoordinates(coordinates);
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
	private static int vcellSize;
	
	@Override
	public void paint(Graphics g) {
		Point2D point = coordinates.getPointFor(getPos());	
		// TODO usar coordinates para todos los argumentos del drawImage
		g.drawImage(getSprite(),(int)point.x(),(int)point.y(),getHcellSize(),getVcellSize(),null);
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
	public Point2D getPosition() {
		return position;
	}
	public void loadSprite(){
		try {
			setSprite(ImageIO.read(new File(getSpritePath())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setPosition(Point2D position) {
		this.position = position;
	}
	public void setPosition(double x, double y) {
		this.position = new Point2D(x, y);
	}
}
