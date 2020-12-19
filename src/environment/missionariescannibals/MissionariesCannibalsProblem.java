package environment.missionariescannibals;

import aima.core.search.framework.problem.BidirectionalProblem;
import aima.core.search.framework.problem.DefaultGoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.TreeSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.UniformCostSearch;

import java.util.List;

import aima.core.agent.Action;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;

public class MissionariesCannibalsProblem extends Problem implements BidirectionalProblem {
	
	/*
	 * number of missionaries, cannibals equals missionaries there is the same.
	 */
	static final int MissionariesNumber = 3;
	static final int ShipCapacity = 2;

	Problem reverseProblem;
	
	public MissionariesCannibalsProblem(MissionariesCannibalsState initialState) {
		super(initialState, Functions.getActionsFunction(), Functions.getResultFunction(), 
				new DefaultGoalTest(new MissionariesCannibalsState(new int[] { 0, 0, 1})));
	}
	public Problem getOriginalProblem() {
		return this;
	}

	public Problem getReverseProblem() {
		return reverseProblem;
	}
	private static void missionariesCannibalsBFS() throws Exception {
		System.out.println("\n--- BreadthFirstSearch ---");

	    MissionariesCannibalsProblem problem = Functions.createMissionariesCannibalsProblem();
	    SearchForActions search = new BreadthFirstSearch(new GraphSearch());
	    SearchAgent agent = new SearchAgent(problem, search);

	    System.out.println("\n--- actions: ---" + agent.getActions().toString());
	    System.out.println(search.getMetrics());
	    System.out.println("\n--- Done ---");
	}
	private static void missionariesCannibalsDFS() throws Exception {
		System.out.println("\n--- DepthFirstSearch ---");

	    Problem problem = Functions.createMissionariesCannibalsProblem();
	    SearchForActions search = new DepthFirstSearch(new GraphSearch());
	    SearchAgent agent = new SearchAgent(problem, search);
	    
	    System.out.println("\n--- actions: ---" + agent.getActions().toString());
	    System.out.println(search.getMetrics());
	    System.out.println("\n--- Done ---");
	}
	private static void missionariesCannibalsUCS() throws Exception {
		System.out.println("\n--- UniformCostSearch ---");

	    Problem problem = Functions.createMissionariesCannibalsProblem();
	    UniformCostSearch search = new UniformCostSearch(new GraphSearch());
	    SearchAgent agent = new SearchAgent(problem, search);

	    System.out.println("\n--- actions: ---" + agent.getActions().toString());
	    System.out.println(search.getMetrics());
	    System.out.println("\n--- Done ---");
	}
	private static void missionariesCannibalsUCS_NotCheckRepeatedStates() {
		System.out.println("\n--- UniformCostSearch NotCheckRepeatedStates ---");

	    Problem problem = Functions.createMissionariesCannibalsProblem();
	    UniformCostSearch search = new UniformCostSearch(new TreeSearch());
	    List<Action> actions = search.findActions(problem);

	    actions.forEach(System.out::println);
	    System.out.println(search.getMetrics());
	    System.out.println("\n--- Done ---");
	}
	public static void main(String[] args) throws Exception {
		missionariesCannibalsBFS();
		missionariesCannibalsDFS();
		missionariesCannibalsUCS();
		missionariesCannibalsUCS_NotCheckRepeatedStates();
	}
}
