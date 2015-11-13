package es.ull.etsii.ia.interface_.Actors;


public class Evaluation<Type> implements Comparable<Evaluation<Type>> {
	private Action<Type> decision;
	private int pos;
	private double value;
	public Evaluation(){
		
	}
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
