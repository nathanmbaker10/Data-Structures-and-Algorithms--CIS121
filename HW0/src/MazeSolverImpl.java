public class MazeSolverImpl {

    /**
     * You should write your code within this method. A good rule of thumb, especially with
     * recursive problems like this, is to write your input exception handling within this
     * method and then use helper methods to carry out the actual recursion.
     * <p>
     * How you set up the recursive methods is up to you, but note that since this is a *static*
     * method, all helper methods that it calls must *also* be static. Make them package-private
     * (i.e. without private or public modifiers) so you can test them individually.
     *
     * @param maze See the writeup for more details about the format of the input maze.
     * @param sourceCoord The source (starting) coordinate
     * @param goalCoord The goal (ending) coordinate
     * @return a matrix of the same dimension as the input maze containing the solution
     * path marked with 1's, or null if no path exists. See the writeup for more details.
     * @throws IllegalArgumentException in the following instances:
     * 1. If the maze is null
     * 2. If m * n <= 1 where m and n are the dimensions of the maze
     * 3. If either the source coordinate OR the goal coordinate are out of the matrix bounds.
     * 4. If your source or goal coordinate are on a blocked tile.
     */
    public static int[][] solveMaze(int[][] maze, Coordinate sourceCoord, Coordinate goalCoord) {
        
        if (maze == null) {
            throw new IllegalArgumentException();
        }
        int mazeHeight = maze.length;
        if (mazeHeight <= 0) {
            throw new IllegalArgumentException();
        }
        int mazeWidth = maze[0].length;
        if (mazeHeight * mazeWidth <= 1 || !inBounds(sourceCoord, maze) || 
                !inBounds(goalCoord, maze) || isBlocked(sourceCoord, maze) 
                || isBlocked(goalCoord, maze)) {
            throw new IllegalArgumentException();
        }
        
        int[][] solutionPath = new int[mazeHeight][mazeWidth];
        solutionPath[sourceCoord.getY()][sourceCoord.getX()] = 1;
        
        if (findPathFrom(sourceCoord, goalCoord, maze, solutionPath)) {
            return solutionPath;
        } else {
            return null;
        }
        
        
        

    }

    //@return if a given coordinate is in bounds within a certain maze.
    private static boolean inBounds(Coordinate coord, int[][] maze) {
        int coordX = coord.getX();
        int coordY = coord.getY();
        
        int mazeHeight = maze.length;
        int mazeLength = maze[0].length;
        
        
        
        return !(coordX < 0 || coordY < 0 || coordX >= mazeLength || coordY >= mazeHeight);
    }
    
    
    //@return if a given coordinate is blocked at a certain coordinate within a certain maze.
    private static boolean isBlocked(Coordinate coord, int[][] maze) {
        return maze[coord.getY()][coord.getX()] == 1;
    }
    
    
    private static boolean findPathFrom(Coordinate sourceCoord, Coordinate goalCoard, int[][] maze, 
            int[][] path) {
        if (sourceCoord.equals(goalCoard)) {
            return true;
        }
        
        //We store the result of the down functions in a local 2D array
        //This is still just taking up heap 
        boolean downResult = moveDown(sourceCoord, goalCoard, maze, path);
        if (downResult) {
            return true;
        }
        
        boolean upResult = moveUp(sourceCoord, goalCoard, maze, path);
        if (upResult) {
            return true;
        }
        
        boolean leftResult = moveLeft(sourceCoord, goalCoard, maze, path);
        if (leftResult) {
            return true;
        }
        
        
        
        boolean rightResult = moveRight(sourceCoord, goalCoard, maze, path);
        if (rightResult) {
            return true;
        }
        
        //If we have made it to this line of the function, then we know no solution exists
        //Must return false
        
        path[sourceCoord.getY()][sourceCoord.getX()] = 0;
        return false;
    }
    
    
    private static boolean moveDown(Coordinate sourceCoord, Coordinate goalCoard, int[][] maze, 
            int[][] path) {
        int sourceX = sourceCoord.getX();
        int sourceY = sourceCoord.getY();
        
        Coordinate newCoordinate = new Coordinate(sourceX, sourceY + 1);
        
        if (!inBounds(newCoordinate, maze) || isBlocked(newCoordinate, maze) || 
                isBlocked(newCoordinate, path)) {
            return false;
        } else {
            path[newCoordinate.getY()][newCoordinate.getX()] = 1;
            return findPathFrom(newCoordinate, goalCoard, maze, path);
        }
    }
    
    private static boolean moveUp(Coordinate sourceCoord, Coordinate goalCoard, int[][] maze, 
            int[][] path) {
        int sourceX = sourceCoord.getX();
        int sourceY = sourceCoord.getY();
        
        Coordinate newCoordinate = new Coordinate(sourceX, sourceY - 1);
        
        if (!inBounds(newCoordinate, maze) || isBlocked(newCoordinate, maze) 
                || isBlocked(newCoordinate, path)) {
            return false;
        } else {
            path[newCoordinate.getY()][newCoordinate.getX()] = 1;
            return findPathFrom(newCoordinate, goalCoard, maze, path);
        }
    }
    
    private static boolean moveLeft(Coordinate sourceCoord, Coordinate goalCoard, int[][] maze, 
            int[][] path) {
        int sourceX = sourceCoord.getX();
        int sourceY = sourceCoord.getY();
        
        Coordinate newCoordinate = new Coordinate(sourceX - 1, sourceY);
        
        if (!inBounds(newCoordinate, maze) || isBlocked(newCoordinate, maze) 
                || isBlocked(newCoordinate, path)) {
            return false;
        } else {
            path[newCoordinate.getY()][newCoordinate.getX()] = 1;
            return findPathFrom(newCoordinate, goalCoard, maze, path);
        }
    }
    
    private static boolean  moveRight(Coordinate sourceCoord, Coordinate goalCoard, 
            int[][] maze, int[][] path) {
        int sourceX = sourceCoord.getX();
        int sourceY = sourceCoord.getY();
        
        Coordinate newCoordinate = new Coordinate(sourceX + 1, sourceY);
        
        if (!inBounds(newCoordinate, maze) || isBlocked(newCoordinate, maze) || 
                isBlocked(newCoordinate, path)) {
            return false;
        } else {
            path[newCoordinate.getY()][newCoordinate.getX()] = 1;
            return findPathFrom(newCoordinate, goalCoard, maze, path);
        }
    }
    
    
}
