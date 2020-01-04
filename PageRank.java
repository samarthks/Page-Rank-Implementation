
/*
 * Computes the PageRank (PR) of each node A in a directed graph using a recursive definition:
 * PR(A) = (1-d) + d (PR(T1)/C(T1) + ... + PR(Tn)/C(Tn))
 * Here d is a damping factor that we will set to 0.85. Nodes T1, T2, ..., Tn are the nodes that
 * connect to A (i.e. having a link going from Ti to A). The term C(Ti) is the number of links outgoing from Ti.
 * 
 */
 


import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageRank{
	public static final double DAMPING_FACTOR = 0.85;	// damping factor
	private double tolerance;							// tolerance to stop
	private long maxIter;								// max iterations to stop
	
	PageRank(){
		// default tolerance=0.000001, default maxIter=100
		this(0.000001, 100);			
	}
	
	PageRank(double tolerance, long maxIter){
		this.tolerance = tolerance;
		this.maxIter = maxIter;
	}
	
	
	
	/**
	 * Computes the PageRank (PR) of each node in a graph in an iterative way.
	 * Iteration stops as soon as this.maxIter or this.tolerance whichever is reached first.
	 * 
	 * @param graph the Graph to compute PR for
     * @return returns a Map<Integer, Double> mapping each node to its PR
     * 
     */

	public Map<Integer, Double> computePageRank(Graph graph){
		
		List<Integer> nodes = graph.getGraphNodes();
		Map<Integer, Double> PR = new HashMap<Integer, Double>(nodes.size());
		Map<Integer, List<Integer>> invertedEdges = createInvertedMap(graph.getGraphEdges());
		
		// Initialize Page Rank table (PR) giving every node initial PageRank 1.0
		for (Integer id : nodes) {
			PR.put(id, 1.0);		
		}
		
		int iteration = 0;
		int changed;
		double maxChange;
		
		do {
			changed = 0;
			maxChange = 0.0;
			
			// Re-calculate Page range for all nodes of graph
			for (Integer node : nodes) {
				
				double weightSum = 0.0;
				List<Integer> pointing = invertedEdges.get(node); // All other nodes that point this node
				
				if (pointing != null) {	// Check there is list of pointing nodes, eg. there is at least one node that have edge to this node
					
					/*
					 *  PR(T1)/C(T1) + ... + PR(Tn)/C(Tn)
					 *  PR(Ti) from table PR and C(Ti) from graph.edges.size for every node Ti.
					 *  
					 *  Here does not need to check there is edges-list for node pn, it have to be
					 *  because that node has been added in pointing-list of target node!
					 */
					for (Integer pn : pointing) {
						weightSum += (PR.get(pn) / (double) graph.getGraphEdges().get(pn).size());
					}
				}		
				// Adjust with Damping factor according algorithm
				double pr =  (1 - PageRank.DAMPING_FACTOR) +  PageRank.DAMPING_FACTOR * weightSum;
				
				/*
				 * Check how big is change of nodes Page rank.
				 * If change is greater then tolerance, update it to new value and increase number of nodes changed in this iteration
				 */
				double change = PR.get(node) - pr; 
			
				if (Math.abs(change) > this.tolerance) {
					++changed;
					PR.put(node, pr);
					
					// Biggest change of node value in this iteration.
					// Used only for logging purposes to see how graph stabilizes with iterations
					if (Math.abs(change) > maxChange) {
						maxChange = Math.abs(change);
					}
				}
			}
			++iteration;
			//System.out.println("Iteration " + iteration + ", changed pageranks " + changed + ". Maximum change = " + maxChange);
			
			// If iterations exceed maximum, write error message and stop.
			if (iteration >= this.maxIter) {
				System.err.println("ERROR : Iterations " + iteration + " exceed maxium iterations " + this.maxIter + " before calculations are ready. Returned weights are not reliable");
				break;
			}
			
			/*
			 * End equation: If there is no nodes with changed PageRank, graph is stabilized, there would be no more changes.
			 * So, we can end iteration
			 */
		}
		while (changed > 0);
		
		return PR;
	}
		
	/**
	 * Creates inverted direction pointing edges, Edgenode. all edges that point to given node.
	 * This structure makes calculating pageRank of one node easy, all nodes that affect 
	 * can be found directly with node id.
	 * 
	 * 
	 * @param graph
	 * @return
	 */
	private Map<Integer, List<Integer>> createInvertedMap(Map<Integer, List<Integer>> graph) {
		
		Map<Integer, List<Integer>> inv = new HashMap <Integer, List<Integer>> (graph.size());
		
		for (Integer parent : graph.keySet()) {
			
			List<Integer> edges = graph.get(parent);
			for (Integer child : edges) {
				
				// If there is not yet pointing edges for this node, add this node to map and create empty edge list
				List<Integer> childEdges = inv.get(child);
				if (childEdges == null) {
					childEdges = new ArrayList<Integer>();
					inv.put(child,  childEdges);
				}
				// Add new pointing edge for this node.
				childEdges.add(parent);
			}
		}
		return inv;
	}
	
}
