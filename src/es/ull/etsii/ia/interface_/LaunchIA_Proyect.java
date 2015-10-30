package es.ull.etsii.ia.interface_;

public class LaunchIA_Proyect {
	/**
	 * @author Javier Martin Hernandez y Tomas Rodriguez 
	 *	Clase encargada de lanzar la aplicacion.
	 */
	public static void main(String[] args) {
		Control control = Control.getInstance();
		control.play();
		
	}

}
