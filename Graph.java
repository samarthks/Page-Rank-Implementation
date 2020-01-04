

import java.util.List;
import java.util.Map;

public class Graph {
    List<Integer> nodes;
    Map<Integer, List<Integer>> edges;
    
    public Graph(List<Integer> nodes, Map<Integer, List<Integer>> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }
    
    public List<Integer> getGraphNodes() {
        return this.nodes;
    }
    
    public Map<Integer, List<Integer>> getGraphEdges() {
        return this.edges;
    }
}
