import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CoinCollector {

	int gridRows;
	int gridCols;
	String[][] field;
	int[][] answers;
	
	public CoinCollector() {
		breakdown("small.txt");
	}
	
	public CoinCollector(String file) {
		breakdown(file);
	}
	
	/**
	 * Reads in the file as a single string
	 * @param filename the name of the file to be read in
	 * @return the contents of the file as a string
	 */
	private String readInProblem(String filename) {
		try {
			return Files.readString(Path.of(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * Fills in the data required of CoinCollector from a string
	 * @param file the string file output of readInProblem()
	 */
	private void breakdown(String file) {
		//Split the string into an array
		String[] arr = this.readInProblem(file).split("\n");
		
		//Take the grid size
		this.gridRows = Integer.parseInt(arr[0]); 
		this.gridCols = Integer.parseInt(arr[1]);
		
		//create field of proper size
		this.field = new String[this.gridRows][];
		this.answers = new int[this.gridRows][];
		
		//For each remaining line (equal to number of rows)
		for(int i = 0; i < this.gridRows; i++) {
			this.field[i] = arr[i+2].split("");
			this.answers[i] = new int[this.gridCols];
		}
	}
	
	
	/**
	 *  Determines the maximum number of coins a robot can pick up on the way to their target location
	 *  using a top down approach.
	 *    @param startRow the initial row the robot starts in
	 *    @param startCol the initial column the robot starts in
	 *    @return the max number of coins that can be picked up on the way to the target destination, -1 
	 *    if it is not possible to reach the target destination from the starting point.
	 */
	public int findMaxCoinsTopDown(int startRow, int startCol) {
		if (startRow >= this.gridRows || startRow < 0 ||
				startCol >= this.gridCols || startCol < 0) {
			return -1;
		}else if (this.field[startRow][startCol].equals("x")) {
			return -1;
		}else if (this.field[startRow][startCol].equals("c")) {
			if(startRow == this.gridRows-1 && startCol == this.gridCols-1) {
				return 1;
			}
			
			int down = findMaxCoinsTopDown(startRow+1 , startCol);
			int right = findMaxCoinsTopDown(startRow, startCol+1);
			
			//if moving down is better
			if(down > right) {
				//move down
				return 1 + down;
			} else {
				//else move right
				return 1 + right;
			}

		}else {
			if(startRow == this.gridRows-1 && startCol == this.gridCols-1) {
				return 0;
			}
			
			
			int down = findMaxCoinsTopDown(startRow+1 , startCol);
			int right = findMaxCoinsTopDown(startRow, startCol+1);
			
			//if moving down is better
			if(down > right) {
				//move down
				return down;
			} else {
				//else move right
				return right;
			}
		}
	}
	
	/**
	 * Determines the maximum number of coins a robot can pick up on the way to their target location
	 * using a bottom up approach.
	 * @param startRow the initial row the robot starts in
	 * @param startCol the initial column the robot starts in
	 * @return the max number o coins that can be picked up on the way to the target destination, -1
	 * if it is not possible to reach the target destination from the starting point.
	 */
	public int findMaxCoinsBottomUp(int startRow, int startCol) {
		for(int i = this.gridRows-1; i >= 0; i--) {
			for(int j = this.gridCols-1; j >=0; j--) {
				if(this.field[i][j].equals("x")) {
					this.answers[i][j] = -1;
				}else if(this.field[i][j].equals("c")) {
					if(i == this.gridRows-1 && j == this.gridCols-1) {
						this.answers[i][j] = 1;
					}else if (i == this.gridRows-1) {
						this.answers[i][j] = this.answers[i][j+1] + 1;
					}else if (j == this.gridCols-1) {
						this.answers[i][j] = this.answers[i+1][j] + 1;
					}else {
						this.answers[i][j] = max(this.answers[i+1][j], this.answers[i][j+1]) + 1;
					}
				}else {
					if(i == this.gridRows-1 && j == this.gridCols-1) {
						this.answers[i][j] = 0;
					}else if (i == this.gridRows-1) {
						this.answers[i][j] = this.answers[i][j+1];
					}else if (j == this.gridCols-1) {
						this.answers[i][j] = this.answers[i+1][j];
					}else {
						this.answers[i][j] = max(this.answers[i+1][j], this.answers[i][j+1]);
					}
				}
			}
		}
		
		
		return this.answers[startRow][startCol];
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private int max(int i, int j) {
		if(i > j) {
			return i;
		}else {
			return j;
		}	
	}

	public String toString() {
		String out = "";
		for (int i = 0; i < this.gridRows; i++) {
			for (int j = 0; j < this.gridCols; j++) {
				if(!this.field[i][j].equals(" ")) {
					out = out + this.field[i][j];
				}else {
					out = out + "~";
				}
				
			}
			out = out + "\n";
		}
		return out;
	}
	
	public String debugToString() {
		String out = "TopDown \n";
		for (int i = 0; i < this.gridRows; i++) {
			for (int j = 0; j < this.gridCols; j++) {
				int max = findMaxCoinsTopDown(i,j);
				//int max = findMaxCoinsBottomUp(i,j);
				String maxString = "";
				if(max >= 0) {
					maxString = "+" + max;
				}else {
					maxString = max + "";
				}
				if(!this.field[i][j].equals(" ")) {
					out = out + maxString + "(" + this.field[i][j] + ")";
				}else {
					out = out + maxString + "(~)";
				}
				
			}
			out = out + "\n";
		}
		out = out + "-------------------------\n Bottom Up \n";
		for (int i = 0; i < this.gridRows; i++) {
			for (int j = 0; j < this.gridCols; j++) {
				//int max = findMaxCoinsTopDown(i,j);
				int max = findMaxCoinsBottomUp(i,j);
				String maxString = "";
				if(max >= 0) {
					maxString = "+" + max;
				}else {
					maxString = max + "";
				}
				if(!this.field[i][j].equals(" ")) {
					out = out + maxString + "(" + this.field[i][j] + ")";
				}else {
					out = out + maxString + "(~)";
				}
				
			}
			out = out + "\n";
		}
		return out;
	}
}
