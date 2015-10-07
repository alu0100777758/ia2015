package es.ull.etsii.ia.interface_;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de procesar los eventos del  rat�n.
 */
public class MouseControl implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Point point = arg0.getPoint();
		Control.getInstance().clickedIn(new Point2D(point.getX(), point.getY()));
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
