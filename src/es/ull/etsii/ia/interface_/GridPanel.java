package es.ull.etsii.ia.interface_;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de representar una rejilla de densidad definida capaz de actuar como sistema de coordenadas.
 */
public class GridPanel extends JPanel implements CoordinateSystem2D {
	private static final String DEFAULT_BACKGROUND = "img/lawn.png";		//	imagen por defecto para el fondo
	private static final long serialVersionUID = -8427977059756274679L;
	private BufferedImage backgroundImage;
	private int vPoints;
	private int hPoints;
	private double vSize;
	private static int DEFAULT_SIZE = 50;
	private static int PADDING = 5;
	private Color color = Color.BLACK;
	private boolean backgroundPresent = true;
	
	public GridPanel(){
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(PADDING,PADDING,PADDING,PADDING + 2));
		setvPoints(DEFAULT_SIZE);
		sethPoints(DEFAULT_SIZE);
		try {
			setBackgroundImage(ImageIO.read(new File(DEFAULT_BACKGROUND)));
		} catch (IOException e) {
			setBackgroundPresent(false);
			System.out.println("imgloadFails");
		}
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
		Actor.setHcellSize((int)hSize);
		Actor.setVcellSize((int)vSize);
	}
	public void updateSizes(){
		vSize = (double)(getHeight()-(getInsets().top + getInsets().bottom))/(vPoints - 1);
		hSize = (double)(getWidth()-(getInsets().left + getInsets().right))/(hPoints - 1);
		
	}
	public void paint(Graphics g){
		super.paint(g);
		updateSizes();
		if(isBackgroundPresent())
			g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),null);
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
	//		Getters & setters
	public BufferedImage getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(BufferedImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public boolean isBackgroundPresent() {
		return backgroundPresent;
	}
	public void setBackgroundPresent(boolean backgroundPresent) {
		this.backgroundPresent = backgroundPresent;
	}
	public double getvSize() {
		return vSize;
	}
	public void setvSize(double vSize) {
		this.vSize = vSize;
	}
	public double gethSize() {
		return hSize;
	}
	public void sethSize(double hSize) {
		this.hSize = hSize;
	}
	private double hSize;

	@Override
	public int getHBounds() {
		return gethPoints();
	}
	@Override
	public int getVBounds() {
		return getvPoints();
	}
}
