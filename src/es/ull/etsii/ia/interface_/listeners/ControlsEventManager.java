package es.ull.etsii.ia.interface_.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import es.ull.etsii.ia.interface_.Control;
import es.ull.etsii.ia.interface_.gui.GridControls;

/**
 * @author Javier Mart�n Hern�ndez Clase encargada de procesar los eventos del
 *         panel de control.
 */
public class ControlsEventManager implements ActionListener {
	GridControls gridControls; // Atributo que almacena el panel de control al
								// que "escuchar"
	private boolean start;

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	GridControls getGridControls() {
		return gridControls;
	}

	public void setGridControls(GridControls gridControls) {
		this.gridControls = gridControls;
	}

	public ControlsEventManager(GridControls controls) {
		setGridControls(controls);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass().equals(JButton.class)) {
			JButton pulsedButton = (JButton) e.getSource();
			if (pulsedButton.equals(gridControls.setDimensions)) {
				Control.getInstance().updateDimension();
			} else if (pulsedButton.equals(gridControls.startPath)) {
				if (isStart()){
					Control.getInstance().setWalking(false);
					pulsedButton.setText(GridControls.START_TEXT);
				}
				else{
					Control.getInstance().tryToRunWithDelay();
					pulsedButton.setText(GridControls.STOP_TEXT);
				}
				setStart(!isStart());
			} else if (pulsedButton.equals(gridControls.selectColor)) {
				Control.getInstance().setPathColor(
						Control.getInstance().getColorFromDialog());
			} else if (pulsedButton.equals(gridControls.reset)) {
				System.out.println("reset");
				Control.getInstance().reset();
			}

		}
	}

}
