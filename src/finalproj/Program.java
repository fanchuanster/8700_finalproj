package finalproj;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Properties;

import java.util.stream.IntStream;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;

public class Program {
	
	private static void testEightPuzzles(SearchForActions search, EightPuzzleBoard initialState) {
		System.out.println("Initial state:\n" + initialState.toString());
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
	
	private static void testEightPuzzles(SearchForActions search, int[][] initialStates) {
		for (final int[] state:initialStates) {
			testEightPuzzles(search, new EightPuzzleBoard(state));
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

	/**
	 * Create an array of initial states
	 * @param n indicates how many states to create
	 * @return an array of the initial states created
	 */
	private static int[][] getInitialStates(int n) {
		final String STATES_FILE = "resources/solvable_initial_states.txt";
		
		try {
			FileInputStream is = new FileInputStream(STATES_FILE);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
			
			int[][] states = new int[n][];
			String line = null;
			int count = 0;
			while ((line = bufferedReader.readLine()) != null && count < n) {
				IntStream intSteam = line.chars().map((c) -> (int)c - (int)'0');
				states[count] = intSteam.toArray();
				count++;
			}
			bufferedReader.close();
			return states;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		ManhattanHeuristicFunction hf = new ManhattanHeuristicFunction();
		MisplacedTilleHeuristicFunction misplacedhf = new MisplacedTilleHeuristicFunction();
		EuclideanDistanceHeuristicFunction euclideanhf = new EuclideanDistanceHeuristicFunction();
		
		
		int[][] states = getInitialStates(1);
		
//		testEightPuzzles(new RecursiveBestFirstSearch(new AStarEvaluationFunction(misplacedhf)), states);
//		testEightPuzzles(new AStarSearch(new GraphSearch(), hf), states);
		testEightPuzzles(new AStarSearch(new TreeSearch(), hf), states);
		testEightPuzzles(new AStarSearch(new TreeSearch(), euclideanhf), states);
		testEightPuzzles(new GreedyBestFirstSearch(new GraphSearch(), hf), states);
		
//		testEightPuzzles(new BreadthFirstSearch(), states);
//		testEightPuzzles(new UniformCostSearch(), states);
		
//		

		System.out.println();
	}
}

