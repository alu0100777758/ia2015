package es.ull.utils;

/**
 * @author Javier Martin Hernandez
 * Clase encargada de facilitar diversas formulas comunes.
 */
public class Formula {
	/**
	 * @param relationTop	valor con el que se relaciona la variable de salida.
	 * @param top			valor conocido que se relaciona con bottom como relationTop con la variable de salida.
	 * @param bottom		valor conocido que se relaciona con top como variable de salida con relationTop.
	 * @return		el resultado de una regla de 3 para enteros.
	 */
	public static int rule3int(int relationTop, int top, int bottom){
		return relationTop*bottom/top;
	}
	/**
	 * @param relationTop	valor con el que se relaciona la variable de salida.
	 * @param top			valor conocido que se relaciona con bottom como relationTop con la variable de salida.
	 * @param bottom		valor conocido que se relaciona con top como variable de salida con relationTop.
	 * @return		el resultado de una regla de 3 para dobles.
	 */
	public static double rule3double(double relationTop, double top, double bottom){
		return relationTop*bottom/top;
	}
}
