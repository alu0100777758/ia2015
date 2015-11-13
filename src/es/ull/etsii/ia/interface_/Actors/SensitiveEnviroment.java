package es.ull.etsii.ia.interface_.Actors;


/**
 *	Interfaz que confiere la capacidad de actuar como medio ante un sensor.
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 */
public interface SensitiveEnviroment {
	/**
	 * "sensor" es el actor que percibe el entorno y devuelve el mapa de la vision del sensor.
	 * @param sensor 
	 * @return Array 
	 */
	Vision2D perceive(Actor sensor);
}
