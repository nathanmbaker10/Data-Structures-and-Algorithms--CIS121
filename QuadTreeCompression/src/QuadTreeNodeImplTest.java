import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;




public class QuadTreeNodeImplTest  {
    
    
    
    @Test
    public void oneByOneImage() {
        int[][] image = new int[1][1];
        
        image[0][0] = 54;
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
        
        assertTrue(newTree.isLeaf());
        assertEquals(54, newTree.getColor(0, 0));
        int[][] decompressOutput = newTree.decompress();
        assertArrayEquals(image, decompressOutput);
        
        newTree.setColor(0, 0, 67);
        
        assertEquals(67, newTree.getColor(0, 0));
        
        decompressOutput = newTree.decompress();
        image[0][0] = 67;
        
        assertArrayEquals(image, decompressOutput);
        
    }
    
    
    @Test
    public void by4Image() {
        int[][] image = new int[4][4];
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                image[i][j] = 5; 
            }
        }
        
        image[0][3] = 1;
        
        image[3][0] = 1;
        
        image[3][3] = 1;
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
        
        
        assertFalse(newTree.isLeaf());
        assertEquals(4, newTree.getDimension());
        
        
        assertTrue(newTree.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).isLeaf());
        
    }
    
    
    @Test
    public void createsNewLeaf() {
        int[][] image = new int[4][4];
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                image[i][j] = 5; 
            }
        }
        
        image[0][0] = 3;
        
        image[0][3] = 1;
        
        image[3][0] = 1;
        
        image[3][3] = 1;
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
        
        assertFalse(newTree.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).isLeaf());
        assertEquals(3, newTree.getColor(0, 0));
        
        newTree.setColor(0, 0, 5);
        assertTrue(newTree.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).isLeaf());
        assertEquals(5, newTree.getColor(0, 0));
        
        
        
    }
    
    
    
    
    @Test
    public void breakUpLeafToSetColor() {
        int[][] image = new int[4][4];
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                image[i][j] = 5; 
            }
        }
        
        
        
        image[0][3] = 1;
        
        image[3][0] = 1;
        
        image[3][3] = 1;
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
        
        assertTrue(newTree.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).isLeaf());
        
        newTree.setColor(0, 0, 3);
        
        assertFalse(newTree.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).isLeaf());
        assertFalse(newTree.getQuadrant(QuadTreeNode.QuadName.TOP_RIGHT).isLeaf());
        assertFalse(newTree.getQuadrant(QuadTreeNode.QuadName.BOTTOM_RIGHT).isLeaf());
        assertFalse(newTree.getQuadrant(QuadTreeNode.QuadName.BOTTOM_RIGHT).isLeaf());
        
        
        
        
        
    }
    
    @Test
    public void testDecompress() {
        int[][] image = new int[4][4];
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                image[i][j] = 5; 
            }
        }
        
        image[0][3] = 1;
        
        image[3][0] = 1;
        
        image[3][3] = 1;
        
        QuadTreeNode newTreeNode = QuadTreeNodeImpl.buildFromIntArray(image);
        
        assertTrue(newTreeNode.getQuadrant(QuadTreeNode.QuadName.TOP_LEFT).isLeaf());
        
        assertArrayEquals(image, newTreeNode.decompress());
        
    }
    
    @Test
    public void testSizeAndCompressionRatio() {
        int[][] image = new int[4][4];
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                image[i][j] = 5; 
            }
        }
        
        image[0][3] = 1;
        
        image[3][0] = 1;
        
        image[3][3] = 1;
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
        
        assertEquals(17, newTree.getSize());
        
        assertEquals((double) (17.0 / 16.0), newTree.getCompressionRatio(), 0);
    }

    
    
    @Test(expected = IllegalArgumentException.class)
    public void testLeafBreakup() {
        int[][] image = new int[8][8];
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                image[i][j] = 5; 
            }
        }
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
       
        newTree.setColor(0, 0, 4);
        
        newTree.setColor(3, 3, 4);
        
        newTree.setColor(3, 0, 4);
        
        newTree.setColor(0, 3, 4);
        
        newTree.setColor(8, 0, 2);
        
        
        
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void moreCoverage() {
        int[][] image = new int[8][8];
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                image[i][j] = 5; 
            }
        }
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
        
        assertEquals(0, newTree.getColor(7, 7));
        
        assertEquals(0, newTree.getColor(7, 0));
        
        QuadTreeNodeImpl.buildFromIntArray(null);
        
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void nonSquareImage() {
        int[][] image = new int[4][3];
        
        QuadTreeNodeImpl.buildFromIntArray(image);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void raggedImage() {
        int[][] image = {{0,1,2,3}, {0,1,2}, {1}, {2,3,4}};
        
        QuadTreeNodeImpl.buildFromIntArray(image);
        
    }
    
    @Test
    public void case1() {
        int[][] image = {
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
        int originalSize = newTree.getSize();
        
        newTree.setColor(3, 0, 1);
        int newSize = newTree.getSize();
        
        assertEquals(originalSize, newSize);
    }
    
    @Test
    public void case2() {
        int[][] image = {
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
        int originalSize = newTree.getSize();
        
        newTree.setColor(3, 3, 1);
        int newSize = newTree.getSize();
        
        assertEquals(originalSize + 4, newSize);
    }
    
    @Test public void case3() {
        int[][] image = {
                {0, 0, 1, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        
        QuadTreeNode newTree = QuadTreeNodeImpl.buildFromIntArray(image);
        int originalSize = newTree.getSize();
        
        newTree.setColor(3, 0, 1);
        int newSize = newTree.getSize();
        image[0][3] = 1;
        
        assertEquals(originalSize - 4, newSize);
        
        assertEquals(1, newTree.getColor(3, 0));
        
        assertArrayEquals(image, newTree.decompress());
        
    }
    
}
