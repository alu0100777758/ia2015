package es.ull.etsii.ia.interface_.Actors;

import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.utils.Array2D;

public class Vision2D extends Array2D<Actor>{
	private Point2D relativePos;
	public Vision2D(Array2D<Actor> array, Point2D relativePos) {
		super(array);
		setRelativePos(relativePos);
	}
	public Point2D getRelativePos() {
		return relativePos;
	}
	public void setRelativePos(Point2D relativePos) {
		this.relativePos = relativePos;
	}
}
