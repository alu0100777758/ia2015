package es.ull.etsii.ia.interface_.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import es.ull.etsii.ia.interface_.Control;
import es.ull.etsii.ia.interface_.gui.GridControls;

/**
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 * 		   Clase encargada de procesar los eventos del panel de control.
 */
public class ControlsEventManager implements ActionListener {
	GridControls gridControls;			//	Atributo que almacena el panel de control al que "escuchar"
	private boolean running;     		//	true si la simulacion esta ejecutandose.

	

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
			if (pulsedButton.equals(getGridControls().getSetDimensions())) {
				Control.getInstance().updateDimension();
			} else if (pulsedButton.equals(getGridControls().getStartStop())) {
				if (isRunning()){
					Control.getInstance().setWalking(false);
					pulsedButton.setText(GridControls.START_TEXT);
				}
				else{
					Control.getInstance().tryToRunWithDelay();
					pulsedButton.setText(GridControls.STOP_TEXT);
				}
				setStart(!isRunning());
			} 
			else if (pulsedButton.equals(getGridControls().getStep())) {
				Control.getInstance().step();
			} 
			else if (pulsedButton.equals(getGridControls().getReset())) {
				Control.getInstance().reset();
			}

		}
	}
	// ******************Getters & Setters********************
	public boolean isRunning() {
		return running;
	}
	
	public void setStart(boolean start) {
		this.running = start;
	}
	
	GridControls getGridControls() {
		return gridControls;
	}
	
	public void setGridControls(GridControls gridControls) {
		this.gridControls = gridControls;
	}
}
