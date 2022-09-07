import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UnionFind {

    
    Map<Integer, Integer> representativeMapping = new HashMap<Integer, Integer>();
    
    Map<Integer, Integer> parentPointersMap = new HashMap<Integer, Integer>();
    
    Map<Integer, Integer> rankingsMap = new HashMap<Integer, Integer>();
    
    
    
    public UnionFind() {}
    
    
    public void makeSet(int x) {
        parentPointersMap.put(x, x);
        rankingsMap.put(x, 0);
    }
    
    public int findNotCompressed(int x) {
        while(representativeMapping.get(x)!= representativeMapping.get(parentPointersMap.get(x))) {
            representativeMapping.put(x, parentPointersMap.get(x));
        }
        return representativeMapping.get(x);
    }
    
    public void Union(int x, int y, boolean compressed) {
        
        int xRank;
        int yRank;
        
        if(compressed) {
            xRank = findCompressed(x);
            yRank = findCompressed(y);
        } else {
            xRank = findNotCompressed(x);
            yRank = findNotCompressed(y);
        }
        
        
        
        if (xRank == yRank) {
            return;
        } 
        
        if (rankingsMap.get(xRank) > rankingsMap.get(yRank)) {
            parentPointersMap.put(yRank, xRank);
        } else {
            parentPointersMap.put(xRank, yRank);
            if (rankingsMap.get(xRank) == rankingsMap.get(yRank)) {
                rankingsMap.put(yRank, rankingsMap.get(yRank) + 1);
            }
        }
    }
    
    public int findCompressed(int x) {
        while(representativeMapping.get(x)!= representativeMapping.get(parentPointersMap.get(x))) {
            parentPointersMap.put(x, findCompressed(parentPointersMap.get(x)));
        }
        return parentPointersMap.get(x);
    }
    
    
    
}
