package es.ull.etsii.ia.interface_;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 * @author Javier Martin Hernandez y Tomas  Rodriguez Martin
 *	Clase que representa la ventana de la aplicacion.
 */

public class CellRoboCup extends JFrame {
	private static final long serialVersionUID = 1L;

	CellRoboCup(){
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setTitle("CellRoboCup");
		setSize(750, 750);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void validate() {
		super.validate();
	}
}
