package es.ull.etsii.ia.interface_;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de dibujar y configurar el panel de controles.
 */
public class GridControls extends JPanel {
	private static final long serialVersionUID = -7807762227293272654L;
	ActionListener listener = null;
	JTextArea vPointsSelector = new JTextArea(1,5);
	JTextArea hPointsSelector = new JTextArea(1,5);
	JButton setDimensions = new JButton("Establecer");
	JTextArea vPathStart = new JTextArea(1,5);
	JTextArea hPathStart = new JTextArea(1,5);
	JButton setPathStart = new JButton("Establecer");
	JButton startPath = new JButton("Empezar");
	
	
	JButton selectColor = new JButton("Seleccionar");
	JButton reset = new JButton("reset");
	JTextArea delayMillis = new JTextArea(1,10);
	JCheckBox borderCheck = new JCheckBox("Parada en borde");
	JCheckBox revisitCheck = new JCheckBox("Revisita");
	private static final int TEXT_HEIGHT = 25;
	public static final String OPTIONS_TITLE_1 = "Densidad";
	public static final String COMPONENT_SEPARATOR_1 = "x";
	public static final String OPTIONS_TITLE_2 = "Punto Inicial";
	public static final String COMPONENT_SEPARATOR_2 = ",";
	public static final String BORDER_CHECK_TOOLTIP_TEXT = "<html>Si lo desmarca la simulaci�n podr� seguir avanzando aunque se llegue<br> a un borde, no obstante  se detendr� si intenta salirse del per�metro</html>";
	public GridControls() {
		super.setMaximumSize(new Dimension(25, getMaximumSize().height));
		GridLayout layout = new GridLayout(15, 1);
		layout.setVgap(10);
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
		dimensions = new JPanel();
		add(new JLabel(OPTIONS_TITLE_2));
		dimensions.add(vPathStart);
		dimensions.add(new JLabel(COMPONENT_SEPARATOR_2));
		dimensions.add(hPathStart);
		dimensions.setMaximumSize(getMinimumSize());
		add(dimensions);
		add(setPathStart);
		add(delayMillis);
		add(borderCheck);
		add(revisitCheck);
		add(startPath);
	
		add(reset);
		
		add(selectColor);
		add(new StateControls(listener));
		borderCheck.setSelected(true);
		borderCheck.setToolTipText(BORDER_CHECK_TOOLTIP_TEXT);
		dimensions.setMaximumSize(new Dimension(Integer.MAX_VALUE, TEXT_HEIGHT));
		super.setMinimumSize(new Dimension(0,0));
	}

	private void updateListener() {
		setDimensions.addActionListener(listener);
		setPathStart.addActionListener(listener);
		startPath.addActionListener(listener);
		
		selectColor.addActionListener(listener);
		reset.addActionListener(listener);
	}

	public void setListener(ActionListener listener) {
		this.listener = listener;
		updateListener();
	}
	public int gethPoints(){
		return Integer.parseInt(hPointsSelector.getText());
	}
	public int getvPoints(){
		return Integer.parseInt(vPointsSelector.getText());
	}
	public int gethStart(){
		return Integer.parseInt(hPathStart.getText());
	}
	public int getvStart(){
		return Integer.parseInt(vPathStart.getText());
	}
	public int getDelay(){
		return Integer.parseInt(delayMillis.getText());
	}

}
