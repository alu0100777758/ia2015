package es.ull.etsii.ia.interface_;

import java.awt.Color;
import java.awt.Graphics;

import es.ull.etsii.ia.interface_.geometry.Point2D;
/**
 * @author Javier Mart�n Hern�ndez
 *	Clase que unifica GridPanel y GraphicState.
 */
public class GridStatusPanel extends GridPanel {
	private static final long serialVersionUID = 6081079377279937021L;
	IA_State path;
	boolean pathON = false;
	public GridStatusPanel(){
	}
//	void updatePath(){
//		path.clear();
//		path.setStart(new Point2D(gethPoints()/2,getvPoints()/2));
//	}
//	public void setvPoints(int vPoints) {
//		super.setvPoints(vPoints);
//		if(pathIsBroken()){
//			updatePath();
//		}
//	}
//	public void setStart(Point2D point){
//		path.setStart(point);
//	}
//	public void sethPoints(int hPoints) {
//		super.sethPoints(hPoints);
//		if(pathIsBroken()){
//			updatePath();
//		}
//	}
	public boolean pathIsBroken(){
		if(path == null)
			return false;
//		return (path.isAtBorder() || path.isOut());
		return true;
	}
//	public boolean atBorder(){
//		return path.isAtBorder();
//	}
	public void pathColor(Color color){
		path.setColor(color);
	}
	public void turnOnPath(){
		GridPanel grid = this;
		path = new IA_State(grid);
		pathON = true;
//		updatePath();
	}
	public void paint(Graphics g){
		super.paint(g);
		if(pathON)
			path.drawPath(g);
	}
//	public void paintComponent(Graphics g){
//		super.paintComponent(g);
//		g.setColor(Color.GREEN);
//		if(pathON)
//			path.drawPath(g);
//	}
}
