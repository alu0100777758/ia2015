package es.ull.etsii.ia.interface_.simulation;

import es.ull.etsii.ia.interface_.Actors.Robo_Player;

/**
 * @author Javier Martin Hernandez y Tomas Rodriguez
 * Clase que representa la memoria colectiva del sistema colmena.
 */
public class HiveMemory {
	private boolean attackState = false;	// denota si es verdad que el equipo se encuentra en un estado de ataque.
	private int hiveSize = 0;				// denota el tamaño actual de la colmena.
	private int ballSeen = 0;				// denota la cantidad de turnos desde la ultima vez que se percibio el balon.
	private Robo_Player ballOwner;			// denota el actor del equipo en posesion del balon.
	
	// ******************Getters & Setters********************
	public boolean isAttackState() {
		return attackState;
	}

	public void setAttackState(boolean attackState) {
		this.attackState = attackState;
	}

	public int getHiveSize() {
		return hiveSize;
	}

	public void setHiveSize(int hiveSize) {
		this.hiveSize = hiveSize;
	}

	public int getBallSeen() {
		return ballSeen;
	}

	public void setBallSeen(int ballSeen) {
		this.ballSeen = ballSeen;
	}

	public Robo_Player getBallOwner() {
		return ballOwner;
	}

	public void setBallOwner(Robo_Player ballOwner) {
		this.ballOwner = ballOwner;
	}
	
}
