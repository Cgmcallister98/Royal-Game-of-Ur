import java.util.*;

public class Board {
	/**
	 * ArrayList used to compile the board together
	 */
	private ArrayList<StringBuilder> rep = new ArrayList<>();

	/**
	 * Creates the board
	 * 
	 * <p>Uses StringBuilder objects to store the parts of the board. 
	 * Puts the StringBuilders in appropriately in the ArrayList rep to make 
	 * a graphical representation of the board.
	 */
	public Board() {
		//StringBuilders of the parts of the board
		StringBuilder topBox = new StringBuilder(" #########################           #############");
		StringBuilder midBox = new StringBuilder(" #     #     #     #     #           #     #     #");
		StringBuilder midTopBox = new StringBuilder(" #################################################");
		StringBuilder midMidBox = new StringBuilder(" #     #     #     #     #     #     #     #     #");
		StringBuilder newLine = new StringBuilder("\n");
		
		//adding the parts of the board to the ArrayList
		//Setting up the column coordinate values
		rep.add(new StringBuilder("    A     B     C     D     E     F     G     H"));
		rep.add(newLine);
		rep.add(topBox);
		rep.add(newLine);
		for(int i = 0;i < 3;i++) {
			rep.add(clone(midBox));
			rep.add(newLine);
		}
		rep.add(midTopBox);
		rep.add(newLine);
		for(int i = 0;i < 3;i++) {
			rep.add(clone(midMidBox));
			rep.add(newLine);
		}
		rep.add(midTopBox);
		rep.add(newLine);
		for(int i = 0;i < 3;i++) {
			rep.add(clone(midBox));
			rep.add(newLine);
		}
		rep.add(topBox);
		//board outline complete
		
		//Setting up the row coordinate values
		rep.get(6).setCharAt(0, '1');
		rep.get(14).setCharAt(0, '2');
		rep.get(22).setCharAt(0, '3');
		
		//Setting up the Rossette marker on the correct Squares
		rep.get(6).setCharAt(4, 'X');
		rep.get(6).setCharAt(40, 'X');
		rep.get(14).setCharAt(22, 'X');
		rep.get(22).setCharAt(4, 'X');
		rep.get(22).setCharAt(40, 'X');
	}

	/**
	 * Updates the board image 
	 * 
	 * <p>Checks if the player exists, if so stores the color and and players
	 * color marker, else stores the empty space. Sets the location on the board
	 * with the player's marker of where their piece should be.
	 * 
	 * @param p A player
	 * @param column Column indicator
	 * @param row Row indicator
	 */
	public void update(Player p, char column, int row) {
		
		if(column == Piece.SCORED_COLUMN && row == Piece.SCORED_ROW) {
			repaint();
			return;
		}
			
		//calls rowIndex() on row to set index value into variable 
		int rowIndex = rowIndex(row);
		//calls columnIndex() on column to set index value into variable 
		int columnIndex = columnIndex(column);
		//create color variable
		Color c = null;
		//if player exists get the color
		if(p != null)
			c = p.getColor();
		char letter;
		//if player exists set letter equal to the players color 
		if(p != null)
			//if c is Black, letter = B else W 
			letter = c == Color.BLACK? 'B' : 'W';
		//set letter to blank if no player exists
		else letter = ' ';
		
		//update the space with the row and column coordinates to be letter
		rep.get(rowIndex).setCharAt(columnIndex, letter);
		
		repaint();
	}
	
	/**
	 * Checks that the rossette square when unoccupied has its 'X' marker
	 */
	private void repaint() {
	//for loop for each row
		for(int i = 1; i <= 3; i++) {
		//for loop for each column
			for(char ch = 'A'; ch <= 'H'; ch++) {
			//checks if current space is a rossette
				if(isRossette(ch, i)) {
				//sets the rossette space into output
					char output = rep.get(rowIndex(i)).charAt(columnIndex(ch));
					//checks if rossette space is blank
					if(output == ' ')
					//adds back the X to the rossette space 
					//due to no player occupancy
						rep.get(rowIndex(i)).setCharAt(columnIndex(ch), 'X');
				}
			}
		}
	}
	
	/**
	 * Removes a piece from the board
	 * 
	 * <p>Calls {@link #update(Player, char, int) update()} where the player is
	 * set to null. This enables the square to be reset to its unowned state
	 * 
	 * @param column Column indicator
	 * @param row Row indicator
	 */
	public void removePiece(char column, int row) {
		update(null, column, row);
	}
	
	/**
	 * Checks if requested space is a rossette
	 * 
	 * @param column Column indicator
	 * @param row Row indicator
	 * @return boolean True if space is a rossette, false otherwise
	 */
	public static boolean isRossette (char column, int row) {
		return column == 'A' && (row == 3 || row == 1) || 
				column == 'D' && row == 2 ||
				column == 'G' && (row == 3 || row == 1);
	}
	
	/**
	 * The below two methods use two separate formulas, the row and column 
	 * formula. These formulas were derived to express the middle of a given 
	 * square any where on the board. The Board is an arrayList of StringBuilders. 
	 * The row formula finds which StringBuilder object to modify. The column 
	 * formula finds the correct char in the StringBuilder to modify.
	 */
	
	/*
	* Method: rowIndex
	* Input: row - a int reference to the row coordinate of a square
	* Process: converts row into the row index using the row formula
	* Output: the converted rowIndex
	*/
	
	/**
	 * Uses the row formula to find the row index
	 * 
	 * @param row Row indicator
	 * @return int Row index
	 */
	private static int rowIndex(int row) {
		return 6+8*(row-1);
	}
	
	/*
	* Method: columnIndex
	* Input: column - a char reference to the column coordinate of a square
	* Process: converts column into the column index using the column formula
	* Output: the converted columnIndex
	*/
	
	/**
	 * Use the column formula to find the column index
	 * 
	 * @param column Column indicator
	 * @return int Column index
	 */
	private static int columnIndex(char column) {
		return 4+6*(column-'A');
	}
	
	/*
	* Method: clone
	* Input: s - a reference to a StringBuilder
	* Process: makes a string of s then converts it into a StringBuilder. 
	* Output: a clone of the StringBuilder parameter.
	*/
	
	/**
	 * Clones a StringBuilder object
	 * 
	 * @param s StringBuilder object
	 * @return StringBuilder A clone of the StringBuilder passed in
	 */
	private static StringBuilder clone(StringBuilder s){
		return new StringBuilder (s.toString());
	}
	
	/*
	* Method: toString
	* Input: n/a
	* Process: appends each StringBuilder in rep to str, a StringBuilder which is
	* then turned into a string by StringBuilders toString(). Finally str is returned.
	* Output: str - a String reference containing all elements of rep. 
	*/
	
	/**
	 * Returns a graphical representation of the board
	 * 
	 * @return String Graphical representation of the board
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(StringBuilder sb : rep)
			str.append(sb);
		return str.toString();
	}
}
