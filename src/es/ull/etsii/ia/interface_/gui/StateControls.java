package es.ull.etsii.ia.interface_.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StateControls extends JPanel implements ActionListener {
	private JButton obstacle = new JButton("Team1");
	private JButton goal = new JButton("goal");
	private JButton initPoint = new JButton("Team2");
	private JButton[] buttons = { obstacle, initPoint,  goal};
	private int selected = 0;

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

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
		obstacle.addActionListener(listener);
		goal.addActionListener(listener);
		initPoint.addActionListener(listener);
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
}
