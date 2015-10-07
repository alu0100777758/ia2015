package es.ull.etsii.ia.interface_;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StateControls extends JPanel {
	private JButton obstacle = new JButton("obst");
	private JButton goal = new JButton("goal");
	private JButton initPoint = new JButton("init");
	private JButton [] buttons = { obstacle, goal, initPoint};
	private short selected = 0;
	public StateControls(ActionListener listener){
		addListener(listener);
		addButtons();
	}
	protected void addButtons() {
		add(obstacle);
		add(goal);
		add(initPoint);
	}
	protected void addListener(ActionListener listener){
		obstacle.addActionListener(listener);
		goal.addActionListener(listener);
		initPoint.addActionListener(listener);
	}
}
