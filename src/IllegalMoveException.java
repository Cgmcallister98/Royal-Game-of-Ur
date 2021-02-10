
public class IllegalMoveException extends Exception{
	/**
	 * Column location of a square
	 */
	private char column;
	/**
	 * Row location of a square 
	 */
	private int row;
	
	/**
	 * The Player's roll 
	 */
	private int roll;
	
	/**
	 * Initializes class variables to parameters
	 *  
	 * @param column column location of a piece
	 * @param row row location of a piece
	 * @param roll Player's roll
	 */
	public IllegalMoveException(char column, int row, int roll) {
		this.column = column;
		this.row = row;
		this.roll = roll;
	}
		
	/**
	 * Initializes roll and sets location variables to 0
	 * 
	 * @param roll the player's roll
	 */
	public IllegalMoveException(int roll) {
		this.roll = roll;
		column = 0;
		row = 0;
	}
	
	/**
	 * Returns the error message based on square location
	 * 
	 * <p> Checks if square is past the start, if so returns error message about 
	 * requested square. Otherwise returns error message about starting square.
	 * 
	 * @return String Error message based location of move
	 */
	public String getMessage() {
		//if piece is on the board
		if(column > 0)
			return "This is not a legal move: " + column + row + ", You rolled: " + roll + ". Try Again! \n";
		return "Space is occupied cannot take out new piece. Move another piece.";
	}
}
