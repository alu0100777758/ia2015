package es.ull.etsii.ia.interface_.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import es.ull.etsii.ia.interface_.Control;
import es.ull.etsii.ia.interface_.geometry.Point2D;

/**
 *	Clase encargada de procesar los eventos del  raton.
 * @author Javier Martin Hernandez y Tomas Rodriguez
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
