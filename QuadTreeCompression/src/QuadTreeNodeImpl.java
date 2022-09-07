// CIS 121, QuadTree

public class QuadTreeNodeImpl implements QuadTreeNode {
    private QuadTreeNodeImpl upperLeft;
    private QuadTreeNodeImpl upperRight;
    private QuadTreeNodeImpl bottomLeft;
    private QuadTreeNodeImpl bottomRight;
    
    
    private int startingX;
    private int startingY;
    private int dimension;
    private boolean leaf;
    private int color;
    
    
    
    
    
    /**
     * ! Do not delete this method !
     * Please implement your logic inside this method without modifying the signature
     * of this method, or else your code won't compile.
     * <p/>
     * As always, if you want to create another method, make sure it is not public.
     *
     * @param image image to put into the tree
     * @return the newly build QuadTreeNode instance which stores the compressed image
     * @throws IllegalArgumentException if image is null
     * @throws IllegalArgumentException if image is empty
     * @throws IllegalArgumentException if image.length is not a power of 2
     * @throws IllegalArgumentException if image, the 2d-array, is not a perfect square
     */
    public static QuadTreeNodeImpl buildFromIntArray(int[][] image) {
        if (image == null) {
            throw new IllegalArgumentException("Null Image");
        }
        int imageHeight = image.length;
        if (imageHeight <= 0) {
            throw new IllegalArgumentException("Empty Image");
        }
        
        int imageWidth = image[0].length;
        
        if (imageWidth != imageHeight) {
            throw new IllegalArgumentException("Not a square");
        }
        
        double divisionResult = imageHeight / 2.0;
        
        int intDivisionResult = imageHeight / 2;
        
        
        if (divisionResult != (double) intDivisionResult && imageHeight != 1) {
            throw new IllegalArgumentException("Not a power of 2");
        }
        
        for (int i = 0; i < imageHeight; i++) {
            if (image[i].length != imageWidth) {
                throw new IllegalArgumentException();
            }
        }
        
        
        return new QuadTreeNodeImpl(image, 0, 0, imageHeight);
        
    }
    
    
    
    
    private QuadTreeNodeImpl(int[][] image, int startingX, int startingY, int dimension) {
        this.startingX = startingX;
        this.startingY = startingY;
        int startingColor = image[startingY][startingX];
        
        
        this.dimension = dimension;
        this.color = startingColor;
        
        if (this.dimension == 1) {
            this.bottomLeft = null;
            this.bottomRight = null;
            this.upperLeft = null;
            this.upperRight = null;
            
            this.leaf = true;
            
        } else {
            int halfDimension = dimension / 2;
            this.upperLeft = new QuadTreeNodeImpl(image, startingX, startingY, halfDimension);
            this.upperRight = new QuadTreeNodeImpl(image, startingX + halfDimension, startingY, 
                    halfDimension);
            this.bottomLeft = new QuadTreeNodeImpl(image, startingX, startingY + halfDimension, 
                    halfDimension);
            this.bottomRight = new QuadTreeNodeImpl(image, startingX + halfDimension, 
                    startingY + halfDimension, halfDimension);
            this.checkThenMerge();
        }
        
        
        
    }
    
    
    private QuadTreeNodeImpl(int startingX, int startingY, int dimension, int color) {
        this.dimension = dimension;
        this.color = color;
        this.startingX = startingX;
        this.startingY = startingY;
        if (dimension == 1) {
            this.leaf = true;
            this.upperLeft = null;
            this.upperRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
            
        } else {
            int halfDimension = dimension / 2;
            this.leaf = false;
            this.upperLeft = new QuadTreeNodeImpl(startingX, startingY, dimension / 2, color);
            this.upperRight = new QuadTreeNodeImpl(startingX + halfDimension, startingY, 
                    halfDimension, color);
            this.bottomRight = new QuadTreeNodeImpl(startingX + halfDimension, 
                    startingY + halfDimension, halfDimension, color);
            this.bottomLeft = new QuadTreeNodeImpl(startingX, startingY + halfDimension, 
                    halfDimension, color);
            
            
        }
        
        
        
    }

    @Override
    public int getColor(int x, int y) {
        if (x >= dimension || y >= dimension || x < 0 || y < 0) {
            throw new IllegalArgumentException("Get Color: X or Y out of bounds");
        }
        if (this.isLeaf()) {
            return this.color;
        }
        
        int halfDimension = this.dimension / 2;
        
        if (x < halfDimension) {
            if (y < halfDimension) {
                return upperLeft.getColor(x, y);
            } else {
                return bottomLeft.getColor(x, y - halfDimension);
            }
        } else {
            if (y < halfDimension) {
                return upperRight.getColor(x - halfDimension, y);
            } else {
                return bottomRight.getColor(x - halfDimension, y - halfDimension);
            }
        }
        
        
    }

    @Override
    public void setColor(int x, int y, int c) {
        if (x >= dimension || y >= dimension || x < 0 || y < 0) {
            throw new IllegalArgumentException("Set Color: X or Y out of bounds");
        } 
        
        
        int halfDimension = dimension / 2;
        
        if (this.dimension == 1 || (this.isLeaf() && this.color == c)) {
            this.color = c;
        } else if (this.isLeaf()) {
            breakUpLeaf();
            this.setColor(x, y, c);
        } else {
            if (x < halfDimension) {
                if (y < halfDimension) {
                    upperLeft.setColor(x, y, c);
                } else {
                    bottomLeft.setColor(x, y - halfDimension, c);
                }
            } else {
                if (y < halfDimension) {
                    upperRight.setColor(x - halfDimension, y, c);
                } else {
                    bottomRight.setColor(x - halfDimension, y - halfDimension, c);
                }
            }
            
            this.checkThenMerge();
        }
        
        
        
    }
    
    private void breakUpLeaf() {
        int halfDimension = dimension / 2;
        this.leaf = false;
        this.upperLeft = new QuadTreeNodeImpl(this.startingX, this.startingY, halfDimension, 
                this.color);
        this.upperRight = new QuadTreeNodeImpl(this.startingX + halfDimension, 
                this.startingY, halfDimension, this.color);
        this.bottomRight = new QuadTreeNodeImpl(this.startingX + halfDimension, 
                this.startingY + halfDimension, halfDimension, this.color);
        this.bottomLeft = new QuadTreeNodeImpl(this.startingX, this.startingY + halfDimension, 
                halfDimension, this.color);
    }
    
    
    private void checkThenMerge() {
        int upperLeftColor = upperLeft.getColor(0, 0);
        int upperRightColor = upperRight.getColor(0, 0);
        int bottomLeftColor = bottomLeft.getColor(0, 0);
        int bottomRightColor = bottomRight.getColor(0, 0);
        
        
        if (upperLeftColor == upperRightColor && upperLeftColor == bottomLeftColor && 
                upperLeftColor == bottomRightColor && upperLeft.isLeaf() && upperRight.isLeaf() 
                && bottomLeft.isLeaf() && bottomRight.isLeaf()) {
            
            this.color = upperLeftColor;
            
            this.leaf = true;
            
            this.upperLeft = null;
            this.upperRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
            
        } else {
            this.leaf = false;
        }
    }

    @Override
    public QuadTreeNode getQuadrant(QuadName quadrant) {
        QuadTreeNode output;
        if (this.isLeaf()) {
            return null;
        }
        
        switch (quadrant) {
            case TOP_LEFT:
                output =  this.upperLeft;
                break;
            case TOP_RIGHT:
                output =  this.upperRight;
                break;
            case BOTTOM_LEFT:
                output =  this.bottomLeft;
                break;
            case BOTTOM_RIGHT:
                output =  this.bottomRight;
                break;
            default:
                return null;
        }
        
        return output;
    }

    @Override
    public int getDimension() {
        return this.dimension;
    }

    @Override
    public int getSize() {
        if (this.isLeaf()) {
            return 1;
        } else {
            return 1 + upperLeft.getSize() + upperRight.getSize() + 
                    bottomLeft.getSize() + bottomRight.getSize();
        }
    }

    @Override
    public boolean isLeaf() {
        return this.leaf;
    }

    @Override
    public int[][] decompress() {
        int[][] output = new int[this.dimension][this.dimension];
        this.decompressRecursive(output);
        return output;
    }
    
    private void decompressRecursive(int[][] outputArray) {
        if (this.isLeaf()) {
            for (int i = startingY; i < startingY + dimension; i++) {
                for (int j = startingX; j < startingX + dimension; j++) {
                    outputArray[i][j] = this.color;
                }
            }
        } else {
            this.upperLeft.decompressRecursive(outputArray);
            this.upperRight.decompressRecursive(outputArray);
            this.bottomLeft.decompressRecursive(outputArray);
            this.bottomRight.decompressRecursive(outputArray);
        }
    }

    @Override
    public double getCompressionRatio() {
        double size = (double) this.getSize();
        double n = (double) (this.dimension * this.dimension);
        
        return (double) (size / n);
    }
}
