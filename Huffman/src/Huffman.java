import java.util.Map;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Implements construction, encoding, and decoding logic of the Huffman coding algorithm. Characters
 * not in the given seed or alphabet should not be compressible, and attempts to use those
 * characters should result in the throwing of an {@link IllegalArgumentException} if used in {@link
 * #compress(String)}.
 */
public class Huffman {
    private Node huffmanRoot;
    private Map<Character, Integer> alphabet;
    int inputLengths = 0;
    int outputLengths = 0;
    class Node {
        private Node left;
        private Node right;
        private Node parent;
        
        private Character binaryDirection;
        private Character value;
        
        Node(Character value) {
            this.value = value;
        }
        
        Node(Node left, Node right) {
            this.left = left;
            this.right = right;
            left.setParent(this);
            left.setBinaryDirection('0');
            right.setParent(this);
            right.setBinaryDirection('1');
        }
        
        Node getLeft() {
            return this.left;
        }
        
        Node getRight() {
            return this.right;
        }
        
        void setBinaryDirection(Character c) {
            this.binaryDirection = c;
        }
        
        void setParent(Node parent) {
            this.parent = parent;
        }
        
        Character getValue() {
            return this.value;
        }
        
        boolean isLeaf() {
            return (this.left == null && this.right == null);
        }
    }

    /**
     * Constructs a {@code Huffman} instance from a seed string, from which to deduce the alphabet
     * and corresponding frequencies.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param seed the String from which to build the encoding
     * @throws IllegalArgumentException seed is null, seed is empty, or resulting alphabet only has
     *                                  1 character
     */
    public Huffman(String seed) {
        if (seed == null || seed.length() <= 1) {
            throw new IllegalArgumentException();
        }
        
        Map<Character, Integer> alphabet = new HashMap<Character, Integer>();
        
        for (int i = 0; i < seed.length(); i++) {
            Character current = seed.charAt(i);
            if (alphabet.containsKey(current)) {
                alphabet.replace(current, alphabet.get(current) + 1);
            } else {
                alphabet.put(current, 1);
            }
        }
        if (alphabet.size() <= 1) {
            throw new IllegalArgumentException();
        }
        this.alphabet = alphabet;
        createTree(alphabet);
    }

    /**
     * Constructs a {@code Huffman} instance from a frequency map of the input alphabet.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param alphabet a frequency map for characters in the alphabet
     * @throws IllegalArgumentException if the alphabet is null, empty, has fewer than 2 characters,
     *                                  or has any non-positive frequencies
     */
    public Huffman(Map<Character, Integer> alphabet) {
        if (alphabet == null || alphabet.isEmpty() || alphabet.size() < 2) {
            throw new IllegalArgumentException();
        }
        this.alphabet = alphabet;
        
        Iterator<Integer> frequencies = alphabet.values().iterator();
        while (frequencies.hasNext()) {
            if (frequencies.next() <= 0) {
                throw new IllegalArgumentException();
            }
        }
        createTree(alphabet);
    }
    
    public void createTree(Map<Character, Integer> alphabet) {
        BinaryMinHeap<Integer, Node> heap = new BinaryMinHeapImpl<Integer, Node>();
        alphabet.forEach((character, freq) -> {
            heap.add(freq, new Node(character));
        });

        while (heap.size() > 1) {
            int rightFrequency = (int) heap.peek().key;
            Node rightNode = heap.extractMin().value;
            
            int leftFrequency = (int) heap.peek().key;
            Node leftNode = heap.extractMin().value;
            
            
            
            Node connectNode = new Node(leftNode, rightNode);
            
            heap.add(leftFrequency + rightFrequency, connectNode);
            
            
        }
        
        this.huffmanRoot = heap.extractMin().value;
        
        
    }

    /**
     * Compresses the input string.
     *
     * @param input the string to compress, can be the empty string
     * @return a string of ones and zeroes, representing the binary encoding of the inputted String.
     * @throws IllegalArgumentException if the input is null or if the input contains characters
     *                                  that are not compressible
     */
    public String compress(String input) {
        if (input == null) {
            throw new IllegalArgumentException(); 
        }
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            try {
                encodingSearchFor(input.charAt(i), output, huffmanRoot);
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException();
            }
        }
        inputLengths += input.length() * 16;
        outputLengths += output.length();
        return output.toString();
    }
    
    void encodingSearchFor(Character c, StringBuilder output, Node current) {
        if (current.isLeaf()) {
            if (!current.getValue().equals(c)) {
                throw new NoSuchElementException();
            }
        } else {
            try {
                output.append('1');
                encodingSearchFor(c, output, current.getRight());
            } catch (NoSuchElementException e) {
                try {
                    output.delete(output.length() - 1, output.length());
                    output.append('0');
                    encodingSearchFor(c, output, current.getLeft());
                } catch (NoSuchElementException x) {
                    output.delete(output.length() - 1, output.length());
                    throw new NoSuchElementException();
                }
            }
        }
        
        
    }

    /**
     * Decompresses the input string.
     *
     * @param input the String of binary digits to decompress, given that it was generated by a
     *              matching instance of the same compression strategy
     * @return the decoded version of the compressed input string
     * @throws IllegalArgumentException if the input is null, or if the input contains characters
     *                                  that are NOT 0 or 1, or input contains a sequence of bits
     *                                  that is not decodable
     */
    public String decompress(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder output = new StringBuilder();
        Node currentNode = huffmanRoot;
        for (int i = 0; i < input.length(); i++) {
            if (currentNode.isLeaf()) {
                output.append(currentNode.getValue());
                currentNode = huffmanRoot;
            }
            
            if (input.charAt(i) == '0') {
                currentNode = currentNode.getLeft();
            } else if (input.charAt(i) == '1') {
                currentNode = currentNode.getRight();
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (currentNode.isLeaf()) {
            output.append(currentNode.getValue());
            currentNode = huffmanRoot;
        }
        if (currentNode.parent != null) {
            throw new IllegalArgumentException();
        }
        
        return output.toString();
    }

    /**
     * Computes the compression ratio so far. This is the length of all output strings from {@link
     * #compress(String)} divided by the length of all input strings to {@link #compress(String)}.
     * Assume that each char in the input string is a 16 bit int.
     *
     * @return the ratio of the total output length to the total input length in bits
     * @throws IllegalStateException if no calls have been made to {@link #compress(String)} before
     *                               calling this method
     */
    public double compressionRatio() {
        if (inputLengths == 0) {
            throw new IllegalStateException();
        }
        return (double) (outputLengths * 1.0) / (1.0 * inputLengths);
    }

    /**
     * Computes the expected encoding length of an arbitrary character in the alphabet based on the
     * objective function of the compression.
     * <p>
     * The expected encoding length is simply the sum of the length of the encoding of each
     * character multiplied by the probability that character occurs.
     *
     * @return the expected encoding length of an arbitrary character in the alphabet
     */
    public double expectedEncodingLength() {
        int frequenciesSum = 0;
        double output = 0;
        Iterator<Integer> frequencies = alphabet.values().iterator();
        Iterator<Character> letters = alphabet.keySet().iterator();
        
        while (frequencies.hasNext()) {
            
            Integer currentFrequency = frequencies.next();
            frequenciesSum += currentFrequency;
        }
        StringBuilder encodingBuilder;
        while (letters.hasNext()) {
            encodingBuilder = new StringBuilder();
            Character currentLetter = letters.next();
            encodingSearchFor(currentLetter, encodingBuilder, huffmanRoot);
            int encodingLength = encodingBuilder.length();
            
            int numerator = alphabet.get(currentLetter);
            
            output += ((double) numerator / frequenciesSum) * (double) encodingLength;
        }
        
        return output;
        
    }
}
