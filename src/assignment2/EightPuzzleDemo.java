package assignment2;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.BidirectionalEightPuzzleProblem;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.SearchForStates;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.SimulatedAnnealingSearch;

public class EightPuzzleDemo {
	
	static Set<EightPuzzleBoard> usedBoards = new HashSet<EightPuzzleBoard>();
	final static int timesMax = 1000;
	
	static EightPuzzleBoard getUniqueEightPuzzleState() {
		EightPuzzleBoard board = null;
		while (board == null) {
			board = new EightPuzzleBoard(Util.getRandomEightPuzzleState());
			if (usedBoards.contains(board)) {
				board = null;
			} else {
				usedBoards.add(board);
			}			
		}
		return board;
	}

	private static int eightPuzzleSimulatedAnnealingDemo() {
		System.out.println("\nEightPuzzleDemo Simulated Annealing Search");
		int succeeded = 0;
		usedBoards.clear();
		for (int i=0; i<timesMax; i++) {
			
			EightPuzzleBoard initialState = getUniqueEightPuzzleState();
//			System.out.println("Initial State:\n" + initialState.toString());
			try {
				Problem problem = new BidirectionalEightPuzzleProblem(initialState);
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(new ManhattanHeuristicFunction());
				SearchAgent agent = new SearchAgent(problem, search);
				
				if (problem.isGoalState(search.getLastSearchState())) {
					System.out.println("achieved in: " + i);
					System.out.println("Final State:\n" + search.getLastSearchState());
					printInstrumentation(agent.getInstrumentation());
					succeeded += 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("\nEightPuzzleDemo Simulated Annealing Search done");
		return succeeded;
	}
	
	private static int eightPuzzleHillClimbingSearchDemo() {
		System.out.println("\nEightPuzzleDemo HillClimbingSearch");
		int succeeded = 0;
		usedBoards.clear();
		for (int i=0; i<timesMax; i++) {
			
			EightPuzzleBoard initialState = getUniqueEightPuzzleState();
//			System.out.println("Initial State:\n" + initialState.toString());
			try {
				Problem problem = new BidirectionalEightPuzzleProblem(initialState);
				HillClimbingSearch search = new HillClimbingSearch(new ManhattanHeuristicFunction());
				SearchAgent agent = new SearchAgent(problem, search);
				printActions(agent.getActions());
				
				if (problem.isGoalState(search.getLastSearchState())) {
					System.out.println("achieved in: " + i);
					System.out.println("Final State:\n" + search.getLastSearchState());
					printInstrumentation(agent.getInstrumentation());
					succeeded += 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("\nEightPuzzleDemo ClimbingSearchDemo done");
		return succeeded;
	}
	
	private static int testEightPuzzle(SearchForActions search, int times) {
		System.out.println("\ntestEightPuzzle search with " + search.toString());
		usedBoards.clear();
		
		int succeeded = 0;
		for (int i=0; i<times; i++) {
			
			EightPuzzleBoard initialState = getUniqueEightPuzzleState();
			try {
				Problem problem = new BidirectionalEightPuzzleProblem(initialState);
				SearchAgent agent = new SearchAgent(problem, search);
				
				if (problem.isGoalState(((SearchForStates)search).findState(problem))) {
					search.getMetrics();
					System.out.println("Path cost="+agent.getActions().size());
					printInstrumentation(agent.getInstrumentation());
					succeeded += 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(String.format("Success rate: %d/%d", succeeded, times));
		return succeeded;
	}
	
	
	private static void printInstrumentation(Properties properties) {
		properties.keySet().stream().map(key -> key + "=" + properties.get(key)).forEach(System.out::println);
	}

	private static void printActions(List<Action> actions) {
		actions.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		final int times = 1000;
		ManhattanHeuristicFunction hf = new ManhattanHeuristicFunction();
		testEightPuzzle(new HillClimbingSearch(hf), times);
		testEightPuzzle(new HillClimbingSearchFirstChoice(hf), times);
		testEightPuzzle(new SimulatedAnnealingSearch(hf), times);
		testEightPuzzle(new SimulatedAnnealingSearch(hf), times);
		testEightPuzzle(new AStarSearch(new GraphSearch(), hf), 1);
	}

}
