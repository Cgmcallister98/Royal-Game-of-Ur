
public class Piece {
	/**
	 * location of the piece
	 */
	private int location;
	/**
	 * Color of the piece
	 */
	private Color color;
	/**
	 * Constant signifying starting space for pieces
	 */
	public static final int OFF_THE_BOARD = -1;
	/**
	 * Constant signifying the scored space
	 */
	public static final int SCORED = 14;
	/**
	 * Constant signifying the middle of the board
	 */
	public static final int ROSSETTE = 7;
	/**
	 * Constant for scored column pair
	 */
	public static final char SCORED_COLUMN = 'Z';
	
	/**
	 * Constant for scored row pair
	 */
	public static final int SCORED_ROW = 5;
	
	/**
	 * Initializes the class variables
	 * 
	 * @param color The color of the piece
	 */
	public Piece(Color color){
		location = OFF_THE_BOARD;
		this.color = color;
	}

	/**
	 * Compares two Piece objects for equality
	 * 
	 * @param o An object
	 * @return boolean True if equal, false otherwise
	 */
	public boolean equals(Object o) {
		//checks o is a Piece object
		if(!(o instanceof Piece))
			return false;
		//copy o
		Piece another = (Piece) o;
		//checks for equality
		return another.color == this.color;
	}
	
	/**
	 * Prints the location of a piece
	 * 
	 * @return str Piece's location
	 */
	public String toString() {
		return "" + location;
	}

	/**
	 * Resets the location of the piece back to start
	 * 
	 * <p> Passes the constant OFF_THE_BOARD to {@link #setLocation(int) setLocation()}
	 * to set the pieces location to start
	 */
	public void reset() {
		setLocation(OFF_THE_BOARD);
	}
	
	//Getters and Setters
	/**
	 * Sets the location of piece
	 * 
	 * @param local location of the piece
	 */
	public void setLocation (int local) {
		location = local;
	}
	
	/**
	 * Gets the location of a piece
	 * 
	 * @return location The location of a piece
	 */
	public int getLocation () {
		return location;
	}
	
	/**
	 * Gets the color of a pie e
	 * 
	 * @return color The color of a piece
	 */
	public Color getColor() {
		return color;
	}
}
