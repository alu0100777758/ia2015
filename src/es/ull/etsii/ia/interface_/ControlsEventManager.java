package es.ull.etsii.ia.interface_;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de procesar los eventos del  panel de control.
 */
public class ControlsEventManager implements ActionListener {
	GridControls gridControls;  // Atributo que almacena el panel de control al que "escuchar"

 GridControls getGridControls() {
		return gridControls;
	}

	public void setGridControls(GridControls gridControls) {
		this.gridControls = gridControls;
	}

	public ControlsEventManager(GridControls controls) {
		setGridControls(controls);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass().equals(JButton.class)) {
			JButton pulsedButton = (JButton) e.getSource();
			if (pulsedButton.equals(gridControls.setDimensions)) {
				Control.getInstance().trySetGridPoints();
			} else if (pulsedButton.equals(gridControls.setPathStart)) {
				Control.getInstance().trySetPathStart();
			} else if (pulsedButton.equals(gridControls.startPath)) {
				Control.getInstance().tryToRunWithDelay();
			} else if (pulsedButton.equals(gridControls.randColor)) {
				Control.getInstance().setPathColor(
						Control.getInstance().getRandomColor());
			} else if (pulsedButton.equals(gridControls.selectColor)) {
				Control.getInstance().setPathColor(
						Control.getInstance().getColorFromDialog());
			} else if (pulsedButton.equals(gridControls.stopPath)) {
				Control.getInstance().setWalking(false);
			} else if (pulsedButton.equals(gridControls.reset)) {
				System.out.println("reset");
				Control.getInstance().reset();
			}

		}
	}

}
