
public class Pair {
	/**
	 * Column coordinate
	 */
	private char column;
	/**
	 * Row coordinate
	 */
	private int row;
	
	/**
	 * Initializes Class variables 
	 * 
	 * @param column Column coordinate
	 * @param row Row coordinate
	 */
	public Pair(char column, int row) {
		this.column = column;
		this.row = row;
	}
	
	/**
	 * Generates the hashcode for the Pair
	 * 
	 * @return int The hashcode
	 */
	public int hashCode() {
		return 10*column+row;
	}

	/**
	 * Compares two Pair objects to check for equality
	 * 
	 * @param o An Object
	 * @return result True if equal, false otherwise
	 */
	public boolean equals(Object o) {
		boolean result;
		//check o is a Pair object
		if(!(o instanceof Pair))
			return false;
		//create copy of o
		Pair another = (Pair) o;
		result = another.column == column && another.row == row;
		return result;
	}
	
	/**
	 * Returns a String with info about the Pair
	 * 
	 * @return str 	The pair variables written in coordinate style
	 */
	public String toString() {
		String str = column +""+ row;
		return str;
	}
	
	//Getters
	
	/**
	 *Returns first value
	 * 
	 * @return first Variable first's value
	 */
	public char getFirst() {
		return column;
	}
	
	/**
	 *Returns second value
	 * 
	 * @return second Variable second's value
	 */
	public int getSecond() {
		return row;
	}
}
