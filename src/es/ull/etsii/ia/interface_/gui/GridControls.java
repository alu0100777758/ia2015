package es.ull.etsii.ia.interface_.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

/**
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 * 		   Clase encargada de dibujar y configurar el panel de controles.
 */
public class GridControls extends JPanel {
	public static final int DEFAULT_FPS = 1;
	private static final long serialVersionUID = -7807762227293272654L;
	public static final String RESET_TEXT = "reset";
	public static final String SET_DIMENSIONS_TEXT = "Establecer";
	public static final String START_TEXT = "Empezar";
	public static final String STOP_TEXT = "Pausar";
	ActionListener listener = null;
	JTextArea vPointsSelector = new JTextArea(1, 5);
	JTextArea hPointsSelector = new JTextArea(1, 5);
	public JButton setDimensions = new JButton(SET_DIMENSIONS_TEXT);
	JTextArea vPathStart = new JTextArea(1, 5);
	JTextArea hPathStart = new JTextArea(1, 5);
//	public BinomicInput dimensions = new BinomicInput(OPTIONS_TITLE_1, COMPONENT_SEPARATOR_1);
	public JButton setPathStart = new JButton(SET_DIMENSIONS_TEXT);
	public  JButton startPath = new JButton(START_TEXT);
	public  JButton step = new JButton("siguiente");
	public  JButton reset = new JButton(RESET_TEXT);
	public  JSlider fps = new JSlider(1, 60, DEFAULT_FPS);
	JTextArea delayMillis = new JTextArea(1, 10);
//	public JCheckBox borderCheck = new JCheckBox("Parada en borde");
//	public JCheckBox revisitCheck = new JCheckBox("Revisita");
	private static final int TEXT_HEIGHT = 25;
	public static final String OPTIONS_TITLE_1 = "Densidad";
	public static final String COMPONENT_SEPARATOR_1 = "x";
//	public static final String OPTIONS_TITLE_2 = "Punto Inicial";
//	public static final String COMPONENT_SEPARATOR_2 = ",";
	public static final String BORDER_CHECK_TOOLTIP_TEXT = "<html>Si lo desmarca la simulaci�n podr� seguir avanzando aunque se llegue<br> a un borde, no obstante  se detendr� si intenta salirse del per�metro</html>";
	public  final JComponent [] initializingList = {fps,startPath, step, reset};
	private StateControls stateInput;
	public StateControls getStateInput() {
		return stateInput;
	}

	public void setStateInput(StateControls stateInput) {
		this.stateInput = stateInput;
	}

	public GridControls() {
		super.setMaximumSize(new Dimension(25, getMaximumSize().height));
		GridLayout layout = new GridLayout(11, 1);
		layout.setVgap(15);
		setLayout(layout);
		JPanel dimensions = new JPanel();
		add(new JLabel(OPTIONS_TITLE_1));
		dimensions.add(vPointsSelector);
		dimensions.add(new JLabel(COMPONENT_SEPARATOR_1));
		dimensions.add(hPointsSelector);
		dimensions.setMaximumSize(getMinimumSize());
		add(dimensions);
		add(setDimensions);
		dimensions.setMaximumSize(new Dimension(Integer.MAX_VALUE, TEXT_HEIGHT));
//		dimensions = new JPanel();
//		add(new JLabel(OPTIONS_TITLE_2));
//		dimensions.add(vPathStart);
//		dimensions.add(new JLabel(COMPONENT_SEPARATOR_2));
//		dimensions.add(hPathStart);
//		dimensions.setMaximumSize(getMinimumSize());
//		add(dimensions);
//		add(setPathStart);
//		add(delayMillis);
		fps.setMajorTickSpacing(10);
		fps.setMinorTickSpacing(1);
		fps.setPaintTicks(true);
		fps.setPaintLabels(true);
		add(new JLabel("     Frames por segundo"));
		add(fps);
//		add(borderCheck);
//		add(revisitCheck);
		add(startPath);
		add(step);
		add(reset);
//		add(selectColor);
		setStateInput(new StateControls(listener));
		add(getStateInput());
//		borderCheck.setSelected(true);
//		borderCheck.setToolTipText(BORDER_CHECK_TOOLTIP_TEXT);
		dimensions
				.setMaximumSize(new Dimension(Integer.MAX_VALUE, TEXT_HEIGHT));
		super.setMinimumSize(new Dimension(0, 0));
	}

	private void updateListener() {
		setDimensions.addActionListener(listener);
		setPathStart.addActionListener(listener);
		startPath.addActionListener(listener);
		reset.addActionListener(listener);
		step.addActionListener(listener);
	}

	public void setListener(ActionListener listener) {
		this.listener = listener;
		updateListener();
	}
	// ******************Getters & Setters********************
	public int gethPoints() {
		return Integer.parseInt(hPointsSelector.getText());
	}

	public int getvPoints() {
		return Integer.parseInt(vPointsSelector.getText());
	}

	public int gethStart() {
		return Integer.parseInt(hPathStart.getText());
	}

	public int getvStart() {
		return Integer.parseInt(vPathStart.getText());
	}

	public int getDelay() {
//		return Integer.parseInt(delayMillis.getText());
		return 1000/fps.getValue();
	}
	public int getActorType(){
		return getStateInput().getSelected();
	}

}
