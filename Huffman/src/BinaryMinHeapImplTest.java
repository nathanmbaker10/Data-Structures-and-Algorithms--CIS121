import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;



public class BinaryMinHeapImplTest {

    
    
    @Test(expected = NoSuchElementException.class)
    public void testEmpty() {
        BinaryMinHeap<Integer, Integer> testHeap = new BinaryMinHeapImpl<Integer, Integer>();
        
        assertTrue(testHeap.isEmpty());
        
        assertFalse(testHeap.containsValue(6));
        
        testHeap.add(3, 4);
        
        testHeap.add(2, 7);
        
        BinaryMinHeap.Entry<Integer, Integer> minValue = testHeap.extractMin();
        
        assertEquals((int) 2, (int) minValue.key);
        
        assertEquals((int) 7, (int) minValue.value);
        
        
        assertEquals(3, (int) testHeap.peek().key);
        
        testHeap.extractMin();
        
        testHeap.extractMin();
    }
    
    
    
    @Test
    public void testValues() {
        BinaryMinHeap<Integer, Integer> testHeap = new BinaryMinHeapImpl<Integer, Integer>();
        
        assertTrue(testHeap.isEmpty());
        
        assertFalse(testHeap.containsValue(6));
        
        testHeap.add(3, 4);
        
        testHeap.add(2, 7);
        
        Set<Integer> expectedValues = new HashSet<Integer>();
        
        expectedValues.add(4);
        expectedValues.add(7);
        
        
        Set<Integer> actualValueSet = testHeap.values();
        
        assertTrue(actualValueSet.contains(4));
        assertTrue(actualValueSet.contains(7));
    }
    
    
    
    @Test
    public void testMinHeapify() {
        BinaryMinHeap<Integer, Integer> testHeap = new BinaryMinHeapImpl<Integer, Integer>();
        
        assertTrue(testHeap.isEmpty());
        
        assertFalse(testHeap.containsValue(6));
        
        testHeap.add(3, 4);
        
        testHeap.add(2, 7);
        
        testHeap.add(5, 9);
        
        testHeap.add(20, 22);
        
        testHeap.add(1, 6);
        
        
        assertEquals((int) 1, (int) testHeap.extractMin().key);
        
    }
    
}
