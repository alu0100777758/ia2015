package es.ull.etsii.ia.interface_.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BinomicInput extends JPanel {
	private JTextArea leftInput = new JTextArea();
	private JTextArea rightInput = new JTextArea();
	private JLabel separator;
	private JLabel title;
	public BinomicInput(String title, String separator){
		this.separator = new JLabel(separator);
		this.title = new JLabel(title);
//		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel inputs = new JPanel();
		inputs.add(new JLabel(title));
		inputs.add(leftInput);
		inputs.add(this.separator);
		inputs.add(rightInput);
		inputs.setBackground(Color.RED);
		add(inputs);
//		setMaximumSize(getMinimumSize());
	}
}
