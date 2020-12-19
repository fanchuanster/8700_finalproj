package finalproj;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.BidirectionalEightPuzzleProblem;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.SearchForStates;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstEvaluationFunction;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.informed.RecursiveBestFirstSearch;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import assignment2.Util;

public class Program {
	
	private static void testEightPuzzle(SearchForActions search, EightPuzzleBoard initialState) {        
		String[] nameparts = search.toString().split("\\.");
		System.out.println("\ntestEightPuzzle search with " + nameparts[nameparts.length - 1]);
		
		Problem problem = new Problem(initialState,
				EightPuzzleFunctionFactory.getActionsFunction(),
				EightPuzzleFunctionFactory.getResultFunction(),
				new EightPuzzleGoalTest());

		try {
			SearchAgent agent = new SearchAgent(problem, search);
			
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
	}
	
	private static void eightPuzzleAStarManhattanDemo(EightPuzzleBoard initialState) {
		System.out.println("\nEightPuzzleDemo AStar Search (ManhattanHeursitic)-->");
		try {
			Problem problem = new Problem(initialState, EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(), new EightPuzzleGoalTest());
			SearchForActions search = new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}
	private static void printInstrumentation(Properties properties) {
		properties.keySet().stream().map(key -> key + "=" + properties.get(key)+"\t").forEach(System.out::print);
	}
	
//	unsolvable initial states
//	4 0 8
//	7 5 3 
//	2 6 1
	
//	1 8 7
//	4 3 2 
//	6 5 0
//	
//	solvable initial states
//	7 4 1
//	5 3 0 
//	2 6 8
//	
//	7, 1, 8,
//	0, 4, 6, 
//	2, 3, 5
	public static void main(String[] args) {
		
		ManhattanHeuristicFunction hf = new ManhattanHeuristicFunction();
//		187432650
		EightPuzzleBoard unresolvable_initstate = new EightPuzzleBoard(new int[] {1, 8, 7,
				4, 3, 2, 
				6, 5, 0});
//		718046235
		EightPuzzleBoard solvable_initstate = new EightPuzzleBoard(new int[] { 7, 1, 8,
				0, 4, 6, 2, 3, 5 });
		
		EightPuzzleBoard initstate = solvable_initstate;
		
		System.out.println("Initial state \n" + initstate.toString());
		
//		testEightPuzzle(new RecursiveBestFirstSearch(new AStarEvaluationFunction(hf)), initstate);
		testEightPuzzle(new AStarSearch(new GraphSearch(), hf), initstate);
//		testEightPuzzle(new GreedyBestFirstSearch(new GraphSearch(), hf), initstate);
//		
//		testEightPuzzle(new BreadthFirstSearch(new GraphSearch()), initstate);
//		eightPuzzleAStarManhattanDemo(unresolvable_initstate);
		System.out.println();
	}
}

