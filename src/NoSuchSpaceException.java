
public class NoSuchSpaceException extends Exception {
	
	/**
	 * Column location of a square
	 */
	private char column;
	/**
	 * Row location of a square 
	 */
	private int row; 
	
	/**
	 * Initializes class variables
	 * 
	 * @param column Column location of a square
	 * @param row Row location of a square
	 */
	public NoSuchSpaceException(char column, int row) {
		this.column = column;
		this.row = row;
	}

	/**
	 * Returns error message
	 * 
	 * @return String Error message
	 */
	public String getMessage(){
		return "This is not a legal square: " + column + row +  ", Try Again.";
	}
	
}
