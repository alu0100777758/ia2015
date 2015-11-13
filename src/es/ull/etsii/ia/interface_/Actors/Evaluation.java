package es.ull.etsii.ia.interface_.Actors;


/**
 * clase encargada de almacenar el resultado de la evaluacion de una percepcion.
 * @author Javier Martin Hernandez y Tomas Rodriguez 
 *
 * @param <Type> tipo parametrizado de la accion que use.
 */
public class Evaluation<Type> implements Comparable<Evaluation<Type>> {
	private Action<Type> decision;		// 	decision más idonea (elegida mediante situacion / accion).
	private int pos;					// 	posicion (estado) asociado a la evaluacion.
	private double value;				//	valor otorgado al estado por parte de la heuristica utilizada.				
	public Evaluation(){
	}
	/**
	 * @param decision
	 * @param pos
	 * @param val
	 */
	public Evaluation(Action<Type> decision, int pos, int val) {
		setDecision(decision);
		setPos(pos);
		setValue(val);
	}
	
	// ******************Getters & Setters********************
	public Action<Type> getDecision() {
		return decision;
	}
	public void setDecision(Action<Type> decision) {
		this.decision = decision;
	}

	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double d) {
		this.value = d;
	}
	@Override
	public String toString() {
		return "" + getPos() +" val: " + getValue();
	}
	@Override
	public int compareTo(Evaluation<Type> o) {
		Evaluation<Type>  o1 = (Evaluation<Type>)o;
		return (int)(getValue() - o1.getValue())*100 ;
	}
}
