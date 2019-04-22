
package maze;

public class DisjointSet {
	
	private MazeCell sets[];
    
    /**
     * make a singleton set out of each cell in the maze
     * @param maze
     */
    public void makeSet(MazeCell[][] mazeCell) {
    		sets = new MazeCell[mazeCell.length * mazeCell[0].length];
    		int count = 0;
        for (int i = 0; i < mazeCell.length; i++) {
        		for (int j = 0; j < mazeCell[i].length; j++) {
        			 MazeCell current = mazeCell[i][j];
        			 current.setRank(0);
        			 current.setParent(current);
        			 sets[count] = current;
        			 count++;
        		}
        }
    }

    /**
     * Create the union of the sets that contain cell1 and cell2.
     * If the two cells are in the same set, nothing changes,
     * else create the new set as a union. 
     * Please see the union-find algorithm.
     * @param cell1
     * @param cell2
     */
    public void union(MazeCell cell1, MazeCell cell2){
    		MazeCell root1 = find(cell1);
    		MazeCell root2 = find(cell2);
    		if (root1.getRank() == root2.getRank()) {
    			root1.setParent(root2);
    			root2.setRank(root2.getRank()+1);
    		}
    		else if (root1.getRank() > root2.getRank()) 
    			root2.setParent(root1);
    		else root1.setParent(root2);
    }

    /**
     * Find the set that the cell is a part of.
     * While finding the set, do the path compression as well.
     * @param cell
     * @return
     */
    public MazeCell find(MazeCell cell){
    		MazeCell current = cell;
        while(current.getParent() != current) {
        		current = current.getParent();
        }
        // path compression
        cell.setParent(current);
        return current;
    }

}
