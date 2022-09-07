import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final public class IslandBridge {
    private IslandBridge() {}

    /**
     * See question details in the write-up. Input is guaranteed to be valid.
     *
     * @param g the input graph representing the islands as vertices and bridges as directed edges
     * @param x the starting node
     * @return true, if no matter how you navigate through the one-way bridges from node x,
     * there is always a way to drive back to node x, and false otherwise.
     * @implSpec This method should run in worst-case O(n + m) time.
     */
    public static boolean allNavigable(Graph g, int x) {
        Set<Integer> xNeighbors = g.outNeighbors(x);
        
        
        
        Graph transposeGraph = g.transpose();
        
        
        
        Set<Integer> discoveredSet = new HashSet<Integer>();
        ResizingDeque<Integer> Q = new ResizingDequeImpl<Integer>();
        
        discoveredSet.add(x);
        Q.addFirst(x);
        
        
        while (Q.size() != 0) {
            Integer currentNode = Q.pollLast();
            Set<Integer> neighborSet = transposeGraph.outNeighbors(currentNode);
            Iterator<Integer> neighborIterator = neighborSet.iterator();
            
            while (neighborIterator.hasNext()) {
                Integer currentNeighbor = neighborIterator.next();
                
                if (!discoveredSet.contains(currentNeighbor)) {
                    Q.addFirst(currentNeighbor);
                    discoveredSet.add(currentNeighbor);
                }
                
            }
            
        }
        
        
        return discoveredSet.containsAll(xNeighbors);
        
        
    }
}
