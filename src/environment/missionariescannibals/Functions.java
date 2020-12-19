package environment.missionariescannibals;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.problem.ResultFunction;

public class Functions {
	
	public static MissionariesCannibalsProblem createMissionariesCannibalsProblem() {
		return new MissionariesCannibalsProblem(new MissionariesCannibalsState(new int[]{3,3,-1}));
	}
	
	public static ActionsFunction getActionsFunction() {
		return new EPActionsFunction();
	}
	public static ResultFunction getResultFunction() {
		return new EPResultFunction();
	}
	private static class EPResultFunction implements ResultFunction {
		public Object result(Object o, Action aa) {
			MissionariesCannibalsState s = (MissionariesCannibalsState) o;
			MissionariesCannibalsAction a = (MissionariesCannibalsAction) aa;

			assert s.getState()[2] == a.getShipDirection() : String.format("S State %d not consistent with action direction %d", s.getState()[2], a.getShipDirection());
			MissionariesCannibalsState ss = s.clone();
			ss.applyAction(a);
			System.out.println(s.toString() + "\t" + a.toString() + "\t" + ss.toString());
			return ss;
		}
	}
	
	private static class EPActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object o) {
			MissionariesCannibalsState s = (MissionariesCannibalsState) o;

			Set<Action> actions = new LinkedHashSet<Action>();

			int[] state = s.getState();
			
			int missionaries = state[0];
			int cannibals = state[1];
			int direction = state[2];
			
			if (direction == 1) {
				missionaries = MissionariesCannibalsProblem.MissionariesNumber - state[0];
				cannibals = MissionariesCannibalsProblem.MissionariesNumber - state[1];
			}
			
			for (int capacity=1; capacity<=MissionariesCannibalsProblem.ShipCapacity; capacity++) {
				for (int m=0; m<=missionaries; m++) {
					for (int c=0; c<=cannibals; c++) {
						if (m+c == capacity) {
							MissionariesCannibalsAction newAction = new MissionariesCannibalsAction(m, c, direction);
							if (!s.isMissionariesInDanger(newAction)) {
								actions.add(newAction);
							}						 
						}
					}
				}
			}
			return actions;
		}
	}
}
