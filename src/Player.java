import java.util.Arrays;

public class Player {
	/**
	 * Array of the player's pieces
	 */
	private Piece[] pieces = new Piece[7];
	/**
	 * The player's name
	 */
	private String name;
	/**
	 * Count of the number of players
	 */
	private static int numPlayers = 0;
	/**
	 * Color of the player's pieces
	 */
	private Color color = numPlayers == 0? Color.BLACK : Color.WHITE;
	/**
	 * Player's score
	 */
	private int score = 0;

	/**
	 * Initializes class variables
	 * 
	 * <p>Sets name and color for each of the pieces. Increases number of players
	 * and checks if greater than 3, if so throws error.
	 * 
	 * @param name Name of the player
	 * @throws TooManyPlayersException Too many players created 
	 */
	public Player(String name) throws TooManyPlayersException {
		this.name = name;
		numPlayers++;
		//error checker to make sure game stays at 2 players max
		if(numPlayers >= 3)
			throw new TooManyPlayersException("error game already has two players"); 
		//assigns each piece the appropriate player's color
		for(int i=0; i < pieces.length; i++)
			pieces[i] = new Piece(color);
	}

	/**
	 * Moves the players piece
	 * 
	 * <p>Stores the pieces old location, sets the piece to its new
	 * location. Checks if the new location == scored space, if so
	 * updates score.
	 * 
	 * @param roll Player's roll
	 * @param index Index of moving piece
	 */
	public void move(int roll, int index) {
		//stores current location
		int old = pieces[index].getLocation();
		//sets new location by adding old and the roll
		pieces[index].setLocation(old+roll);
		//checks to see if new location is scored space, if so increment score
		if(pieces[index].getLocation() == Piece.SCORED)
			score++;
	}

	/**
	 * Returns the index of a piece at the requested location
	 * 
	 * @param location location of a possible piece 
	 * @return i index of a piece, -1 no piece at location
	 */
	public int indexOf(int location) {
		//searches the array
		for(int i = 0; i < pieces.length; i++) {
			//checks if piece i is at the requested location
			if(pieces[i].getLocation() == location)
				return i;
		}
		return -1;
	}

	/**
	 * Finds if the player has a piece at a location
	 * 
	 * <p>Uses {@link #indexOf(int) indexOf()} to see if return value is
	 * not -1 
	 * 
	 * @param location Location of a square
	 * @return boolean True if piece is at location, false otherwise
	 */
	public boolean hasPieceAt(int location) {
		return indexOf(location) != -1;
	}
	
	/**
	 * Resets a requested piece
	 * 
	 * <p>Calls {@link #Piece.reset()} on a players piece
	 * 
	 * @param index Index of a player's piece 
	 */
	public void reset(int index) {
		pieces[index].reset();
		//System.out.println(Arrays.toString(pieces));
	}

	/**
	 * Checks if the player reached the score to win
	 * 
	 * @return boolean True if score is 7, false otherwise
	 */
	public boolean win() {
		return score == 7;
	}
	
	//getters
	
	/**
	 * Gets the location of a requested piece
	 * 
	 * <p>Calls {@link #Piece.getLocation() Piece.getLocation()} to return
	 * the location of the requested piece
	 * 
	 * @param index Index of a players piece
	 * @return int Location of the requested piece
	 */
	public int getLocation(int index) {
		return pieces[index].getLocation();
	}
		
	/**
	 * Returns the player's color 
	 * 
	 * @return color The player's color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns the player's name
	 * 
	 * @return name The player's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the player's score
	 * 
	 * @return score The player's score
	 */
	public int getScore() {
		return score;
	}
}
