package environment.missionariescannibals;

import aima.core.search.informed.*;

import java.util.List;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.BidirectionalEightPuzzleProblem;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.search.framework.Node;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.util.datastructure.XYLocation;

public class AStarNonAdmissibleH {

	public static final EightPuzzleBoard GOAL_STATE = new EightPuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 });
	public static double getHeuristicDistance(Node node) {
		EightPuzzleBoard currState = (EightPuzzleBoard) node.getState();
		int result = 0;
		for (int val = 1; val <= 8; val++) {
			XYLocation locCurr = currState.getLocationOf(val);
			XYLocation locGoal = GOAL_STATE.getLocationOf(val);
			result += Math.sqrt(Math.abs(locGoal.getXCoOrdinate() + locCurr.getXCoOrdinate()));
			
			result += Math.sqrt(Math.abs(locGoal.getYCoOrdinate() + locCurr.getYCoOrdinate()));
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		EightPuzzleBoard board = new EightPuzzleBoard(new int[] { 7, 1, 8, 0, 4, 6, 2, 3, 5 });

		Problem problem = new BidirectionalEightPuzzleProblem(board);
		AStarSearch search = new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction());
		aima.core.search.framework.SearchAgent agent = new aima.core.search.framework.SearchAgent(problem, search);
		
		List<Action> qActions = agent.getActions();
		System.out.println("\n--- actions: ---" + qActions.toString());
	    System.out.println(search.getMetrics());
	    System.out.println("\n--- Done ---");
	}
}
