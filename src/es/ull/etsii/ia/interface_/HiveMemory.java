package es.ull.etsii.ia.interface_;

public class HiveMemory {
	private boolean attackState = false;
	private int hiveSize = 0;
	private int ballSeen = 0;
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
	
}
