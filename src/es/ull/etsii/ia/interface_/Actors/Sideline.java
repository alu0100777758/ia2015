package es.ull.etsii.ia.interface_.Actors;

import java.awt.Graphics;

import es.ull.etsii.ia.interface_.CoordinateSystem2D;
import es.ull.etsii.ia.interface_.geometry.Point2D;

public class Sideline extends Obstacle {
	public static final String SPRITE_PATH = "img/sideLine.png";
	public Sideline(CoordinateSystem2D coordinates, Point2D pos, int face) {
		super(coordinates, face);
		setSpritePath(SPRITE_PATH);
		loadSprite();
		setPos(pos);
//		System.out.println("creando raya en  " + getPos() + "con pos = " + pos);
	}
//	@Override
//	public void paint(Graphics g) {
//		System.out.println("pintando SidelIne");
//		g.drawImage(getSprite(),0,0,getHcellSize(),getVcellSize(),null);
//	}

}
