
public class CoinMazeProblem {

	public static void main(String[] args) {
		CoinCollector collector = new CoinCollector("grid.txt");
		int maxRow = collector.gridRows-1;
		int maxCol = collector.gridCols-1;
		int randRow = (int) Math.floor(Math.random() * maxRow);
		int randCol = (int) Math.floor(Math.random() * maxCol);
		
		long startTime = System.nanoTime();
		int coinsAvailable = collector.findMaxCoinsTopDown(randRow,randCol);
		long executionTime = System.nanoTime() - startTime;
		System.out.println(
				"There are " + coinsAvailable + " coins available from point (" + randRow + "," 
				+ randCol + ")" + "\n" + "Taking " + executionTime + 
				" nanoseconds for a Top Down approach"
				);
		
		startTime = System.nanoTime();
		coinsAvailable = collector.findMaxCoinsBottomUp(randRow,randCol);
		executionTime = System.nanoTime() - startTime;
		System.out.println(
				"There are " + coinsAvailable + " coins available from point (" + randRow + "," 
				+ randCol + ")" + "\n" + "Taking " + executionTime + 
				" nanoseconds for a Bottom Up approach"
				);
		
		
		
		
		System.out.println(collector.debugToString());

	}

}
