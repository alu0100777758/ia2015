package es.ull.etsii.ia.interface_;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de representar una rejilla de densidad definida capaz de actuar como sistema de coordenadas.
 */
public class GridPanel extends JPanel implements CoordinateSystem2D {
	private static final long serialVersionUID = -8427977059756274679L;
	private int vPoints;
	private int hPoints;
	private double vSize;
	private double hSize;
	private static int DEFAULT_SIZE = 50;
	private static int PADDING = 1;
	private Color color = Color.BLACK;
	
	public GridPanel(){
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(PADDING,PADDING,PADDING,PADDING + 2));
		setvPoints(DEFAULT_SIZE);
		sethPoints(DEFAULT_SIZE);
	}
	public int getvPoints() {
		return vPoints;
	}

	public void setvPoints(int vPoints) {
		this.vPoints = vPoints;
	}

	public int gethPoints() {
		return hPoints;
	}

	public void sethPoints(int hPoints) {
		this.hPoints = hPoints;
	}
	public void drawGrid(Graphics g){
		 int x = getInsets().left;
         int y = getInsets().top;
         double w = hSize*(hPoints-1)+x;
         double h = vSize*(vPoints-1)+y;
        g.setColor(color);
		for(int i = 0 ; i < vPoints; i++){
			g.drawLine(x,(int)(i*vSize+y), (int)w,(int)(i*vSize+y));
		}
		for(int i = 0 ; i < hPoints; i++){
			g.drawLine((int)(i*hSize+x),y, (int)(i*hSize+x),(int)h);
		}
	}
	public void updateSizes(){
		vSize = (double)(getHeight()-(getInsets().top + getInsets().bottom))/(vPoints - 1);
		hSize = (double)(getWidth()-(getInsets().left + getInsets().right))/(hPoints - 1);
		
	}
	public void paint(Graphics g){
		super.paint(g);
		updateSizes();
		drawGrid(g);
	}
	public Point2D getPointFor(int x, int y){
		return new Point2D(hSize*x+getInsets().left,vSize*y+getInsets().top);
	}
	@Override
	public Point2D getPointFor(Point2D point) {
		return new Point2D(hSize*point.x()+getInsets().left,vSize*point.y()+getInsets().top);
	}
	public boolean inSystem(Point2D point){
		return (point.x()<gethPoints() && point.y()< getvPoints()) && (point.x()>=0 && point.y()>=0);
	}
	@Override
	public boolean atBorder(Point2D point) {
		return inSystem(point) && ((point.x() == 0 || point.x()==(gethPoints()-1))||(point.y() == 0 || point.y()==(getvPoints()-1)));
	}
	@Override
	public Point2D toSystem(Point2D point) {
        int y = (int) ((point.y()+getInsets().top)/(vSize));
		int x = (int) ((point.x()+getInsets().left)/(hSize));
		return new Point2D(x, y);
		
	}
	@Override
	public Point2D getCellFor(int x, int y) {
		Point2D point = toSystem(new Point2D(x, y));
		return point.add(1, 1);
	}
	@Override
	public Point2D getCellCenter(Point2D point) {
		return getPointFor(point).add(hSize/2,vSize/2 );
	}
	
}
