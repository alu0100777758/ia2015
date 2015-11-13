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
 * 		   Clase encargada de dibujar y configurar el panel de controles.
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 */
public class GridControls extends JPanel {
	private static final long serialVersionUID = -7807762227293272654L;
	private static final int VGAP = 15;												//	gap vertical.
	private static final String FPS_TEXT = "     Frames por segundo";				//	texto sobre el slider de fps.
	private static final int TEXT_HEIGHT = 25;										//	altura de los cuadros de texto.
	public static final int DEFAULT_FPS = 1;										//	fps por defecto.
	public static final String OPTIONS_TITLE_1 = "Densidad";						//	texto sobre la seleccion de dimension.
	public static final String COMPONENT_SEPARATOR_1 = "x";							//	separador entre los cuadros de texto de la seleccion de dimension.
	public static final String SET_DIMENSIONS_TEXT = "Establecer";					//	texto en el boton usado para confirmar la dimension.
	public static final String RESET_TEXT = "reset";								//	texto en el boton usado para reiniciar la simulacion.
	public static final String START_TEXT = "Empezar";								//	texto sobre el boton usado para comenzar/retomar la simulacion.
	public static final String STOP_TEXT = "Pausar";								//	texto sobre el boton usado para pausar la simulacion
	private ActionListener listener = null;											//	listener encargado de manejar los eventos.
	private JTextArea vPointsSelector = new JTextArea(1, 5);						//	cuadro de texto para introducir la densidad horizontal.
	private JTextArea hPointsSelector = new JTextArea(1, 5);						//	cuadro de texto para introducir la densidad vertical.
	private JButton setDimensions = new JButton(SET_DIMENSIONS_TEXT);				//	boton para confirmar la seleccion de densidad.
	private JButton startStop = new JButton(START_TEXT);							//	boton usado para empezar/continuar/pausar la simulacion.
	private JButton step = new JButton("siguiente");								//	boton usado para avanzar un solo paso en la simulacion.
	private JButton reset = new JButton(RESET_TEXT);								//	boton usado para reiniciar la simulacion.
	private JSlider fps = new JSlider(1, 60, DEFAULT_FPS);							//	slider usado para marcar la velocidad de la simulacion.
	private  final JComponent [] initializingList = {fps,startStop, step, reset};	//	lista con los botones a inicializar.
	private StateControls stateInput;												//	panel con los controles referentes al contenido de la simulacion.

	public GridControls() {
		super.setMaximumSize(new Dimension(25, getMaximumSize().height));
		GridLayout layout = new GridLayout(11, 1);
		layout.setVgap(VGAP);
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
		fps.setMajorTickSpacing(10);
		fps.setMinorTickSpacing(1);
		fps.setPaintTicks(true);
		fps.setPaintLabels(true);
		add(new JLabel(FPS_TEXT));
		for(JComponent com : getInitializingList())
			add(com);
		setStateInput(new StateControls(listener));
		add(getStateInput());
		dimensions.setMaximumSize(new Dimension(Integer.MAX_VALUE, TEXT_HEIGHT));
		super.setMinimumSize(new Dimension(0, 0));
	}

	/**
	 * setea los listeners de aquellos elementos que lo requieran.
	 */
	private void updateListener() {
		setDimensions.addActionListener(listener);
		startStop.addActionListener(listener);
		reset.addActionListener(listener);
		step.addActionListener(listener);
	}

	// ******************Getters & Setters********************
	public void setListener(ActionListener listener) {
		this.listener = listener;
		updateListener();
	}
	public int gethPoints() {
		return Integer.parseInt(hPointsSelector.getText());
	}

	public int getvPoints() {
		return Integer.parseInt(vPointsSelector.getText());
	}

	public int getDelay() {
		return 1000/fps.getValue();
	}
	public int getActorType(){
		return getStateInput().getSelected();
	}
	
	public StateControls getStateInput() {
		return stateInput;
	}
	
	public void setStateInput(StateControls stateInput) {
		this.stateInput = stateInput;
	}

	public JTextArea getvPointsSelector() {
		return vPointsSelector;
	}

	public void setvPointsSelector(JTextArea vPointsSelector) {
		this.vPointsSelector = vPointsSelector;
	}

	public JTextArea gethPointsSelector() {
		return hPointsSelector;
	}

	public void sethPointsSelector(JTextArea hPointsSelector) {
		this.hPointsSelector = hPointsSelector;
	}

	public JButton getSetDimensions() {
		return setDimensions;
	}

	public void setSetDimensions(JButton setDimensions) {
		this.setDimensions = setDimensions;
	}

	public JButton getStartStop() {
		return startStop;
	}

	public void setStartStop(JButton startPath) {
		this.startStop = startPath;
	}

	public JButton getStep() {
		return step;
	}

	public void setStep(JButton step) {
		this.step = step;
	}

	public JButton getReset() {
		return reset;
	}

	public void setReset(JButton reset) {
		this.reset = reset;
	}

	public JSlider getFps() {
		return fps;
	}

	public void setFps(JSlider fps) {
		this.fps = fps;
	}

	public ActionListener getListener() {
		return listener;
	}

	public JComponent[] getInitializingList() {
		return initializingList;
	}
	
}
