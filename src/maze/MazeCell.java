package maze;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * The <code>MazeCell</code> class stores information about each individual cell
 * in the maze. The boolean values <code>north</code>, <code>east</code>,
 * <code>west</code>, and <code>south</code> store which walls are up; e.g., if
 * <code>north</code> is <code>true</code>, then the north wall is up.
 * 
 * Each cell also has pointer to its four <code>MazeCell</code> neighbors,
 * <code>neighborN</code>, <code>neighborE</code>, <code>neighborS</code>, and
 * <code>neighborW</code>. If any of these values are null, it means this cell
 * is on the border of the maze.
 *
 *
 */
public class MazeCell {

	private boolean north, east, south, west;
	private boolean visited, examined;
	private MazeCell neighborN, neighborE, neighborS, neighborW;
	private Random generator;

	private MazeCell parent;
	private MazeCell pred;

	private int rank = 0;

	/**
	 * Sets all the walls to <code>true</code> and initializes the random number
	 * generator.
	 */
	public MazeCell() {
		north = true;
		east = true;
		south = true;
		west = true;
		generator = new Random();
		visited = false;
		examined = false;
		setParent(null);
		setPred(null);
	}

	/**
	 * Sets the visited flag to <code>true</code>.
	 */
	public void visit() {
		visited = true;
	}
	
	/**
	 * Returns whether or not this cell has been visited.
	 * 
	 * @return <code>true</code> if the cell has been visited.
	 */
	public boolean visited() {
		return visited;
	}

	/**
	 * Sets the examined flag to <code>true</code>.
	 */
	public void examine() {
		examined = true;
	}
	
	public void unExamine() {
		examined = false;
	}

	/**
	 * Returns whether or not this cell has been examined.
	 * 
	 * @return <code>true</code> if the cell has been examined.
	 */
	public boolean examined() {
		return examined;
	}

	/**
	 * Sets the pointers to the neighbors of this cell. If a pointer is null, that
	 * means this cell is along the border of the maze.
	 * 
	 * @param n
	 *            The north neighbor of this cell.
	 * @param e
	 *            The east neighbor of this cell.
	 * @param s
	 *            The south neighbor of this cell.
	 * @param w
	 *            The west neighbor of this cell.
	 */
	public void setNeighbors(MazeCell n, MazeCell e, MazeCell s, MazeCell w) {
		neighborN = n;
		neighborE = e;
		neighborS = s;
		neighborW = w;
	}

	/**
	 * Sets whether or not there are walls up to the north, east, south, and west of
	 * this cell.
	 * 
	 * @param north
	 *            <code>true</code> if there's a wall on the north side of the cell.
	 * @param east
	 *            <code>true</code> if there's a wall on the east side of the cell.
	 * @param south
	 *            <code>true</code> if there's a wall on the south side of the cell.
	 * @param west
	 *            <code>true</code> if there's a wall on the west side of the cell.
	 */
	public void setWalls(boolean north, boolean east, boolean south, boolean west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
	}

	/**
	 * Returns whether or not this cell has all its walls up.
	 * 
	 * @return <code>true</code> if all walls are up.
	 */
	public boolean hasAllWalls() {
		// see if all walls are true
		return north && east && south && west;
	}

	/**
	 * Returns whether or not this cell has its north wall up.
	 * 
	 * @return <code>true</code> if the cell's north wall is up.
	 */
	public boolean north() {
		return north;
	}

	/**
	 * Returns whether or not this cell has its south wall up.
	 * 
	 * @return <code>true</code> if the cell's south wall is up.
	 */
	public boolean south() {
		return south;
	}

	/**
	 * Returns whether or not this cell has its east wall up.
	 * 
	 * @return <code>true</code> if the cell's east wall is up.
	 */
	public boolean east() {
		return east;
	}

	/**
	 * Returns whether or not this cell has its west wall up.
	 * 
	 * @return <code>true</code> if the cell's west wall is up.
	 */
	public boolean west() {
		return west;
	}

	/**
	 * Gets the neighbors of this cell. Returns an array of those neighbors. The
	 * length of the array is the number of neighbors this cell has.
	 * 
	 * @return An array with the neighbors contained within it.
	 */
	public MazeCell[] getNeighbors() {
		LinkedList<MazeCell> neighbors = new LinkedList<MazeCell>();
		if (neighborN != null)
			neighbors.add(neighborN);
		if (neighborE != null)
			neighbors.add(neighborE);
		if (neighborS != null)
			neighbors.add(neighborS);
		if (neighborW != null)
			neighbors.add(neighborW);
		MazeCell[] neighbor = new MazeCell[neighbors.size()];
		for (int i = 0; i < neighbors.size(); i++)
			neighbor[i] = neighbors.get(i);
		return neighbor;
	}

	/**
	 * Knocks down the wall between this cell and the neighbor cell. The neighbor
	 * cell must actually be a neighbor of this cell. This method is used in
	 * conjunction with <code>neighborWithWalls</code>.
	 * 
	 * @param neighbor
	 *            The neighboring cell; must be one of the neighbors set in
	 *            <code>setNeighbors</code>.
	 */
	public void knockDownWall(MazeCell neighbor) {
		// will require you to change values for both this and neighbor.
		if (neighbor.equals(neighborN)) {
			north = false;
			neighbor.south = false;
		} else if (neighbor.equals(neighborE)) {
			east = false;
			neighbor.west = false;
		} else if (neighbor.equals(neighborS)) {
			south = false;
			neighbor.north = false;
		} else if (neighbor.equals(neighborW)) {
			west = false;
			neighbor.east = false;
		} else
			System.out.println("neighbor not valid");
	}

	/**
	 * Use this function whenever you want to randomly pick one of the neighbours
	 * 
	 * @return - random choice of one of the neighbours.
	 */
	public MazeCell getRandomNeighbor() {
		MazeCell[] neighbor = getNeighbors();
		int range = neighbor.length;
		int random = generator.nextInt(range);
		return neighbor[random];
	}

	/**
	 * Returns a neighbor that has all its walls intact.
	 * 
	 * @return Neighbor with all its walls intact.
	 */
	public MazeCell neighborWithWalls() {
		MazeCell neighbor[] = getNeighbors();
		// new array store all neighbors with walls
		LinkedList<MazeCell> neighbors = new LinkedList<MazeCell>();
		int count = 0;
		for (int i = 0; i < neighbor.length; i++) {
			if (neighbor[i].hasAllWalls()) {
				neighbors.add(neighbor[i]);
				count++;
			}
		}
		if (neighbors.size() > 0) {
			// Randomly choose a neighbor from among
			// the neighbors with walls, and return it.
			int rand = generator.nextInt(count);
			return neighbors.get(rand);
		}
		// no such neighbor
		return null;
	}

	public MazeCell getParent() {
		return parent;
	}

	public void setParent(MazeCell parent) {
		this.parent = parent;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void setPred(MazeCell pred) {
		this.pred = pred;
	}
	
	public MazeCell getPred() {
		return pred;
	}

	/**
	 * return a list of neighbors with walls
	 * 
	 * @param neighbor
	 * @return
	 */
	public LinkedList<MazeCell> hasWallBetween() {
		LinkedList<MazeCell> neighbors = new LinkedList<MazeCell>();
		// will require you to change values for both this and neighbor.
		if (north && neighborN != null)
			neighbors.add(neighborN);
		if (east && neighborE != null)
			neighbors.add(neighborE);
		if (south && neighborS != null)
			neighbors.add(neighborS);
		if (west && neighborW != null)
			neighbors.add(neighborW);
		return neighbors;
	}

	public LinkedList<MazeCell> possiblePath() {
		LinkedList<MazeCell> neighbors = new LinkedList<MazeCell>();
		// will require you to change values for both this and neighbor.
		if (!north && neighborN != null)
			neighbors.add(neighborN);
		if (!east && neighborE != null)
			neighbors.add(neighborE);
		if (!south && neighborS != null)
			neighbors.add(neighborS);
		if (!west && neighborW != null)
			neighbors.add(neighborW);
		return neighbors;
	}
}
