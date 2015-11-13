package es.ull.etsii.ia.interface_.Actors;

/**
 * interfaz funcional con la que representar las acciones de los agentes.
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 *
 * @param <Type> tipo de entrada con la que trabaja.
 */
public interface Action<Type> {
	public void decide(Type object);
}
