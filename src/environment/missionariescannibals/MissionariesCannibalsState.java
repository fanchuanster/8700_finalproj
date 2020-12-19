package environment.missionariescannibals;

import java.util.Arrays;

public class MissionariesCannibalsState implements Cloneable {
	private int[] state;
	
	public MissionariesCannibalsState(int[] state) {
		this.state = state.clone();
	}
	
	public int[] getState() {
		return state;
	}
	
	public void applyAction(MissionariesCannibalsAction action) {
		int multiplication = action.getShipDirection();
		int addMissionaries = action.getMissionaries() * multiplication;
		int addCannibals = action.getCannibals() * multiplication;
		
		state[0] += addMissionaries;
		state[1] += addCannibals;
		state[2] = (-1) * state[2];
	}
	
	public boolean isMissionariesInDanger(MissionariesCannibalsAction action) {
		MissionariesCannibalsState tmpState = this.clone();
		tmpState.applyAction(action);
		
		int[] state = tmpState.getState();
		if (state[0] > 0 && state[0] < state[1]) {
			return true;
		}
		
		int[] otherState = new int[] { MissionariesCannibalsProblem.MissionariesNumber - state[0], MissionariesCannibalsProblem.MissionariesNumber - state[1], state[2] };
		if (otherState[0] > 0 && otherState[0] < otherState[1]) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d, %s)", state[0], state[1], state[2] == -1 ? ".|" : "|.");
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && getClass() == o.getClass()) {
			MissionariesCannibalsState aState = (MissionariesCannibalsState) o;
			return Arrays.equals(state, aState.state);
		}
		return false;
	}
	@Override
	public MissionariesCannibalsState clone() {
		MissionariesCannibalsState result = null;
		try {
			result = (MissionariesCannibalsState) super.clone();
			result.state = result.state.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace(); // should never happen...
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(state);
	}

}
