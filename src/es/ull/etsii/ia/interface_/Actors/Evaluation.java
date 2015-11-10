package es.ull.etsii.ia.interface_.Actors;

import es.ull.etsii.ia.interface_.geometry.Point2D;

public class Evaluation<Type> {
	private Decision<Type> decision;
	private int pos;
	private int value;
	public Evaluation(){
		
	}
	public Evaluation(Decision<Type> decision, int pos, int val) {
		setDecision(decision);
		setPos(pos);
		setValue(val);
	}
	public Decision<Type> getDecision() {
		return decision;
	}
	public void setDecision(Decision<Type> decision) {
		this.decision = decision;
	}

	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
