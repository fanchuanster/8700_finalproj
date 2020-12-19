package assignment2;

import java.util.ArrayList;
import java.util.List;
import aima.core.util.Util;

import aima.core.search.framework.Metrics;
import aima.core.search.framework.Node;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.SearchUtils;
import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.search.framework.problem.Problem;
import aima.core.search.local.HillClimbingSearch;
import aima.core.util.CancelableThread;

public class HillClimbingSearchFirstChoice extends HillClimbingSearch {

	public HillClimbingSearchFirstChoice(HeuristicFunction hf) {
		super(hf);
		this.nodeExpander = new NodeExpander();
		this.hf = hf;
	}
	
	private HeuristicFunction hf = null;
	private final NodeExpander nodeExpander;
	private SearchOutcome outcome = SearchOutcome.FAILURE;
	private Object lastState = null;
	private Metrics metrics = new Metrics();
	
	// function HILL-CLIMBING(problem) returns a state that is a local maximum
	public Node searchNode(Problem p) {
		clearInstrumentation();
		outcome = SearchOutcome.FAILURE;
		// current <- MAKE-NODE(problem.INITIAL-STATE)
		Node current = nodeExpander.createRootNode(p.getInitialState());
		Node neighbor = null;
		// loop do
		while (!CancelableThread.currIsCanceled()) {
			lastState = current.getState();
			metrics.set(METRIC_NODE_VALUE, getValue(current));
			List<Node> children = nodeExpander.expand(current, p);
			// neighbor <- a highest-valued successor of current
			List<Node> betterNodes = getBetterNodesFrom(children, current);
			
			// if neighbor.VALUE <= current.VALUE then return current.STATE
			if (betterNodes.isEmpty()) {
				if (SearchUtils.isGoalState(p, current))
					outcome = SearchOutcome.SOLUTION_FOUND;
				return current;
			}
			// current <- neighbor
			current = Util.selectRandomlyFromList(betterNodes);
		}
		return null;
	}
	
	/**
	 * Sets all metrics to zero.
	 */
	private void clearInstrumentation() {
		nodeExpander.resetCounter();
		metrics.set(METRIC_NODES_EXPANDED, 0);
		metrics.set(METRIC_NODE_VALUE, 0);
	}
	
	@Override
	public Object getLastSearchState() {
		return lastState;
	}
	@Override
	public NodeExpander getNodeExpander() {
		return nodeExpander;
	}
	@Override
	public Metrics getMetrics() {
		metrics.set(METRIC_NODES_EXPANDED, nodeExpander.getNumOfExpandCalls());
		return metrics;
	}
	
	//
	// PRIVATE METHODS
	//

	private List<Node> getBetterNodesFrom(List<Node> children, Node current) {
		ArrayList<Node> betterNodes = new ArrayList<Node>();
		for (int i = 0; i < children.size(); i++) {
			Node child = (Node) children.get(i);

			if (getValue(child) > getValue(current)) {
				betterNodes.add(child);
			}
		}
		return betterNodes;
	}

	private double getValue(Node n) {
		// assumption greater heuristic value =>
		// HIGHER on hill; 0 == goal state;
		return -1 * hf.h(n.getState());
	}

}
