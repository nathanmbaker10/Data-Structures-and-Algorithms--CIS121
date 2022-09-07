import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HuffmanTest {
    @Test(expected = IllegalArgumentException.class)
    public void testUniformFrequenciesErrorToCompress() {
        Huffman tester = new Huffman("abcdefgh");
        tester.compress("ijk");
    }
    
    
    @Test
    public void testMultipleOccuringLetter() {
        Huffman tester = new Huffman("aaaaabcdefgh");
        assertEquals(1, tester.compress("a").length());
        
        assertEquals("a", tester.decompress("1"));
        
        assertTrue(tester.compressionRatio() < 1);
    }
    
    @Test
    public void testOtherConstructor() {
        Map<Character, Integer> alphabet = new HashMap<Character, Integer>();
        
        alphabet.put('a', 1);
        alphabet.put('b', 2);
        
        Huffman tester = new Huffman(alphabet);
        
        assertEquals(1, tester.expectedEncodingLength(), 0);
    }
}
