package es.ull.etsii.ia.interface_;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StateControls extends JPanel implements ActionListener {
	private JButton obstacle = new JButton("Team1");
	private JButton goal = new JButton("goal");
	private JButton initPoint = new JButton("Team2");
	private JButton[] buttons = { obstacle, goal, initPoint };
	private short selected = 0;

	public StateControls(ActionListener listener) {
		addListener(this);
		addButtons();
	}

	protected void addButtons() {
		add(obstacle);
		add(goal);
		add(initPoint);
	}

	protected void addListener(ActionListener listener) {
		obstacle.addActionListener(listener);
		goal.addActionListener(listener);
		initPoint.addActionListener(listener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass() == JButton.class) {
			for (JButton button : buttons) {
				button.setEnabled(button != e.getSource());
			}
		}

	}
}
