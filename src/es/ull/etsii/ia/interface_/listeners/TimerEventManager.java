package es.ull.etsii.ia.interface_.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import es.ull.etsii.ia.interface_.Control;
/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de procesar los eventos de timpo temporizador.
 */
public class TimerEventManager implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass().equals(Timer.class)) {
			Timer timerLaunched = (Timer) e.getSource();
			if (timerLaunched.equals(Control.getInstance().getStepTimer())) {
				Control.getInstance().stepForward();
			}
		}
	}

}
