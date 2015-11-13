package es.ull.etsii.ia.interface_;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 *	Clase que representa la ventana de la aplicacion.
 * @author Javier Martin Hernandez y Tomas  Rodriguez Martin
 */

public class CellRoboCup extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * crea la ventana de la aplicacion.
	 */
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
