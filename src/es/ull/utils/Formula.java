package es.ull.utils;

/**
 * Clase encargada de facilitar diversas formulas comunes.
 * @author Javier Martin Hernandez
 */
public class Formula {
	/**
	 * devuelve el resultado de una regla de 3 para enteros.
	 * @param relationTop	valor con el que se relaciona la variable de salida.
	 * @param top			valor conocido que se relaciona con bottom como relationTop con la variable de salida.
	 * @param bottom		valor conocido que se relaciona con top como variable de salida con relationTop.
	 * @return int
	 */
	public static int rule3int(int relationTop, int top, int bottom){
		return relationTop*bottom/top;
	}
	/**
	 *  devuelve el resultado de una regla de 3 para dobles.
	 * @param relationTop	valor con el que se relaciona la variable de salida.
	 * @param top			valor conocido que se relaciona con bottom como relationTop con la variable de salida.
	 * @param bottom		valor conocido que se relaciona con top como variable de salida con relationTop.
	 * @return double
	 */
	public static double rule3double(double relationTop, double top, double bottom){
		return relationTop*bottom/top;
	}
}
