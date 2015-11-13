package es.ull.etsii.ia.interface_;

public class LaunchIA_Proyect {
	/**
	 *	Clase encargada de lanzar la aplicacion.
	 * @author Javier Martin Hernandez y Tomas Rodriguez 
	 */
	public static void main(String[] args) {
		Control control = Control.getInstance();
		control.play();
		
	}

}
