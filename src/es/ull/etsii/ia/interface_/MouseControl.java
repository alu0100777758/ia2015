package es.ull.etsii.ia.interface_;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de procesar los eventos del  rat�n.
 */
public class MouseControl implements MouseListener, MouseMotionListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
//		Point point = arg0.getPoint();
//		Control.getInstance().clickedIn(new Point2D(point.getX(), point.getY()));
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Point point = arg0.getPoint();
		Control.getInstance().clickPressed(new Point2D(point.getX(), point.getY()));
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point point = arg0.getPoint();
		Control.getInstance().clickReleased(new Point2D(point.getX(), point.getY()));		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point point = e.getPoint();
		Control.getInstance().dragged(new Point2D(point.getX(), point.getY()));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
