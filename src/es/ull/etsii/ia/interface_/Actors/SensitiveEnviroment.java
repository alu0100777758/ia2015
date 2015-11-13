package es.ull.etsii.ia.interface_.Actors;


/**
 *	Interfaz que confiere la capacidad de actuar como medio ante un sensor.
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 */
public interface SensitiveEnviroment {
	/**
	 * @param sensor el sensor que solicita los datos.
	 * @return Array con el mapa de la vision del sensor.
	 */
	Vision2D perceive(Actor sensor);
}
