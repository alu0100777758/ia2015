package es.ull.etsii.ia.interface_.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 		   Define la clase encargada de representar la interfaz de usuario 
 * 		   referente al estado actual de la simulacion.
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 *
 */
public class StateControls extends JPanel implements ActionListener {
	public static final long serialVersionUID = 1L;
	public static final String TEAM1_TEXT = "Rojos";				// nombre del primer equipo.
	public static final String TEAM2_TEXT = "Azules";				// nombre del segundo equipo.
	public static final String BALL_TEXT = "Balon";					// nombre del balon.
	private JButton team1 = new JButton(TEAM1_TEXT);				// boton para seleccionar el primer equipo.
	private JButton team2 = new JButton(TEAM2_TEXT);				// boton para seleccionar el segundo equipo
	private JButton ball = new JButton(BALL_TEXT);					// boton para seleccionar el balon.
	private JButton[] buttons = { team1, team2, ball };				// array con todos los botones.
	private int selected = 0;										// indice del boton seleccionado actualmente.

	public StateControls(ActionListener listener) {
		addListener(this);
		addButtons();
		buttons[0].setEnabled(false);
	}

	/**
	 *  incluye todos los botones en el panel.
	 */
	protected void addButtons() {
		for (JButton button : buttons) {
			add(button);
		}
	}

	/**
	 * @param listener setea correctamente el listener de cada boton.
	 */
	protected void addListener(ActionListener listener) {
		for (JButton button : buttons) {
			button.addActionListener(listener);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass() == JButton.class) {
			for (int index = 0; index < buttons.length; index++) {
				buttons[index].setEnabled(buttons[index] != e.getSource());
				if (!buttons[index].isEnabled())
					setSelected(index);
			}
		}

	}

	// ******************Getters & Setters********************
	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}
}
