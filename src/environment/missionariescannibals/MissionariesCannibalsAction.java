package environment.missionariescannibals;

import aima.core.agent.impl.DynamicAction;

public class MissionariesCannibalsAction extends DynamicAction {
	private int missionaries;
	private int cannibals;
	private int shipDirection;
	
	public int getMissionaries() {
		return missionaries;
	}
	public int getCannibals() {
		return cannibals;
	}
	public int getShipDirection() {
		return shipDirection;
	}

	/**
	 * Action constructor
	 * @param missionaries the number of missionaries to ship
	 * @param cannibals the number of cannibals to ship
	 * @param direction the direction the ship to go towards, -1 means -> to, 1 means <- back
	 */
	public MissionariesCannibalsAction(int missionaries, int cannibals, int direction) {
		super(String.format("(%d, %d, %s)", missionaries, cannibals, direction == -1 ? "=>" : "<="));
		this.missionaries = missionaries;
		this.cannibals = cannibals;
		this.shipDirection = direction;
	}

}
