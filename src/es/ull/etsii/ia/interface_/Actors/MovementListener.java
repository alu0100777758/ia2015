package es.ull.etsii.ia.interface_.Actors;

/**
 * @author alu4550
 *	Interfaz que otorga la capacidad de actuar como oyente  ante los movimientos de otro.
 */
public interface MovementListener {
	/**
	 *  Funcion encargada de denotar una actualizacion de tipo movimiento en el oyente.
	 */
	void updated();
}
