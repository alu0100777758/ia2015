package es.ull.etsii.ia.interface_.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author alu4550
 * Define la clase encargada de representar la interfaz de usuario referente al estado actual de la simulacion.
 *
 */
public class StateControls extends JPanel implements ActionListener {
	public static final String TEAM2_TEXT = "Azules";
	public static final long serialVersionUID = 1L;
	public static final String BALL_TEXT = "Balón";
	public static final String TEAM1_TEXT = "Rojos";
	private JButton team1 = new JButton(TEAM1_TEXT);
	private JButton ball = new JButton(BALL_TEXT);
	private JButton team2 = new JButton(TEAM2_TEXT);
	private JButton[] buttons = { team1, team2,  ball};
	private int selected = 0;


	public StateControls(ActionListener listener) {
		addListener(this);
		addButtons();
		buttons[0].setEnabled(false);
	}

	protected void addButtons() {
		for(JButton button : buttons){
			add(button);
		}
	}

	protected void addListener(ActionListener listener) {
		team1.addActionListener(listener);
		ball.addActionListener(listener);
		team2.addActionListener(listener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass() == JButton.class) {
			for (int index = 0; index < buttons.length; index++) {
				buttons[index].setEnabled(buttons[index] != e.getSource());
				if(!buttons[index].isEnabled())
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
