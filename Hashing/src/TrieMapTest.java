import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Test;

public class TrieMapTest {
    @Test
    public void testEmptyTrieMap() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        
        assertEquals(0, newMap.size());
    }
    
    @Test
    public void testSingleElementTrieMap() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        
        assertNull(newMap.put("a", 1));
        
        assertTrue(newMap.containsKey("a"));
        
        assertEquals(1, (int) newMap.get("a"));
        
        assertTrue(newMap.containsValue(1));
        
        
        assertEquals(1, (int)newMap.remove("a"));
        
        assertEquals(0, newMap.size());
        
    }
    
    
    
    @Test 
    public void testTwoDifferentElements() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        
        assertNull(newMap.put("a", 1));
        assertNull(newMap.put("b", 2));
        
        assertEquals(2, newMap.size());
        
        
        assertTrue(newMap.containsKey("a"));
        assertTrue(newMap.containsKey("b"));
        
        assertEquals(1, (int) newMap.get("a"));
        assertEquals(2, (int) newMap.get("b"));
        
        assertTrue(newMap.containsValue(1));
        assertTrue(newMap.containsValue(2));
        
        
        
        assertEquals(1, (int)newMap.remove("a"));
        
        assertEquals(1, newMap.size());
        
        assertEquals(2, (int) newMap.remove("b"));
        
        assertEquals(0, newMap.size());
    }
    
    
    @Test 
    public void prefixStrings() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        
        assertNull(newMap.put("a", 1));
        assertNull(newMap.put("ab", 2));
        
        assertEquals(2, newMap.size());
        
        
        assertTrue(newMap.containsKey("a"));
        assertTrue(newMap.containsKey("ab"));
        
        assertEquals(1, (int) newMap.get("a"));
        assertEquals(2, (int) newMap.get("ab"));
        
        assertTrue(newMap.containsValue(1));
        assertTrue(newMap.containsValue(2));
        
        
        
        assertEquals(1, (int)newMap.remove("a"));
        
        assertTrue(newMap.containsKey("ab"));
        assertFalse(newMap.containsKey("a"));
        assertEquals(1, newMap.size());
        
        assertEquals(2, (int) newMap.remove("ab"));
        
        assertEquals(0, newMap.size());
    }
    
    
    
    @Test
    public void testClear() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        
        assertNull(newMap.put("a", 1));
        assertNull(newMap.put("ab", 2));
        
        assertEquals(2, newMap.size());
        
        newMap.clear();
        
        assertEquals(0, newMap.size());
    }
    
    @Test
    public void testEmptyString() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        
        
        newMap.put("", 1);
        
        assertTrue(newMap.containsKey(""));
        
        assertEquals(1, newMap.size());
        
        assertEquals(1, (int) newMap.get(""));
        assertEquals(1, (int) newMap.remove(""));
        
        
        
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullPut() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        newMap.put(null, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullGet() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        newMap.get(null);
    }
    
    @Test
    public void testMorePut() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        assertNull(newMap.put("abcde", 2));
        
        assertEquals(1, newMap.size());
        
        assertEquals(2, (int)newMap.get("abcde"));
        
        assertEquals(2, (int) newMap.put("abcde", 1));
        
        assertEquals(1, newMap.size());
        assertEquals(1, (int) newMap.get("abcde"));
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void getCodeCoverage() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        TrieMap.Node<Integer> rootNode = newMap.getRoot();
        
        assertFalse(rootNode.hasChildren());
        assertFalse(rootNode.hasValue());
        assertNull(rootNode.getChildren());
        assertNull(rootNode.getValue());
        
        newMap.put("", 1);
        assertTrue(rootNode.hasValue());
        
        newMap.containsValue(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getCodeCoverage2() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        newMap.remove(null);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void getCodeCoverage3() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        Iterator<Entry<CharSequence, Integer>> newMapIterator = newMap.entryIterator();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getCodeCoverage4() {
        TrieMap<Integer> newMap = new TrieMap<Integer>();
        newMap.containsKey(null);
    }
    
}
