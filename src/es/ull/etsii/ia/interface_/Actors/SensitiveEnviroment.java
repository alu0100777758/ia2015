package es.ull.etsii.ia.interface_.Actors;

import es.ull.utils.Array2D;

/**
 * @author alu4550
 *	Interfaz que confiere la capacidad de actuar como medio ante un sensor.
 */
public interface SensitiveEnviroment {
	/**
	 * @param sensor el sensor que solicita los datos
	 * @return Array con el mapa de la vision del sensor
	 */
	Vision2D getVision(Actor sensor);
}
