import java.io.*;
import java.util.*;

public class Game {
	/**
	 * Creates two Player objects
	 */
	private Player p1;
	private Player p2;
	/**
	 * Creates a Board object
	 */
	private Board theBoard;
	/**
	 * Creates Roll object
	 */
	private Roll roll;
	/**
	 * creates a Hashmap object used to translate board coordinates to integer
	 * location
	 */
	private Map<Pair, Integer> translator = new HashMap<Pair, Integer>();
	
	/**
	 * Initializes class variables
	 * 
	 * <p>Calls {@link #mapBoardLocations()}, catches error if file not found.
	 * Creates the two Player objects from the passed in names, catches error
	 * if too many players are created. Creates the Board and roll objects.
	 * 
	 * @param p1Name Player 1's name
	 * @param p2Name Player 2's name
	 */
	public Game(String p1Name, String p2Name) {
		try {
			mapBoardLocations();
		} catch (FileNotFoundException e1) {
			System.err.println("Translation Input File Not Found");
			System.exit(1);
		}
		try {
			p1 = new Player(p1Name);
			p2 = new Player(p2Name);
		} catch (TooManyPlayersException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		theBoard = new Board();
		roll = new Roll();
	}
	
	/**
	 * Maps the Coordinate and Integer values of each board space 
	 * 
	 * <p>Reads in the file with the data setting the value of the squares.
	 * Adds the info to the HashMap in with the Coordinate format being the key
	 * and the integer location being the value.
	 * 
	 * @throws FileNotFoundException
	 */
	private void mapBoardLocations() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("Map.txt"));
		while(sc.hasNext()) {
			char letter = sc.next().charAt(0);
			int num = sc.nextInt();
			int location = sc.nextInt();
			translator.put(new Pair(letter, num), location);
		}
 	}

	/**
	 * Rolls the dice
	 * 
	 * @return int A roll of the dice
	 */
	public Roll roll() {
		return roll;
	}
	
	/**
	 * Passes the correct turn info to {@link #noPossibleMove(int, Player, Player)}
	 * 
	 * @param p1Turn Who's turn it is 
	 * @param roll A players roll
	 * @return boolean True if no possible move, false otherwise
	 */
	public boolean noPossibleMove(boolean p1Turn, int roll) {
		Player player, opponent;
		//Player 1 turn
		if(p1Turn) {
			player = p1;
			opponent = p2;
		}else { //Player 2 turn
			player = p2;
			opponent = p1;
		}
		return noPossibleMove(roll, player, opponent);
	}
	
	/**
	 * Checks if the player has a possible move
	 * 
	 * <p>Checks if the player has a possible move by calling {@link #isIllegalMove(player, roll)}
	 * and {@link #isIllegalMove(int, int, Player, Player)} on each of their 
	 * pieces.
	 * 
	 * @param roll The player's roll
	 * @param player The player who's turn it is
	 * @param opponent The opponent
	 * @return boolean True if no possible move, False otherwise
	 */
	private boolean noPossibleMove(int roll, Player player, Player opponent) {
		if(roll == 0)
			return true;
		//Loop for each of the player's pieces
		for (int i = 0; i < 7; i++) {
			int location = player.getLocation(i);
			//if piece at the start has a legal move or piece on the board has a legal move
			if(location != Piece.SCORED)
				if(location == Piece.OFF_THE_BOARD && !isIllegalMove(player, roll) || location != Piece.OFF_THE_BOARD && !isIllegalMove(location, roll, player, opponent))
					//possible move
					return false;
		}
		//no possible move
		return true;
	}
	
	/**
	 * Checks if a player has a piece at start thats movable
	 * 
	 * <p>Checks who's turn it is, then passes the correct player to
	 * {@link #canPlayerMoveFromStart(boolean) canPlayerMoveFromStart()} to see
	 * if they have a piece that can legally move out of start. 
	 * 
	 * @param p1Turn Indicator for who's turn it is
	 * @return boolean True if player has movable piece, false otherwise
	 */
	public boolean canPlayerMoveFromStart(boolean p1Turn) {
		//true = player 1 turn, false = player 2 turn
		if(p1Turn)
			return canPlayerMoveFromStart(p1);
		else return canPlayerMoveFromStart(p2);
	}

	/**
	 * Checks each of a player's piece to see if its at start
	 * 
	 * @param p A player
	 * @return boolean True if a piece is at start, false otherwise
	 */
	private boolean canPlayerMoveFromStart(Player p) {
		//Loop for each players piece
		for(int i = 0; i < 7; i++) {
			//Check if that piece is located at start
			if(p.getLocation(i) == Piece.OFF_THE_BOARD)
				return true;
		}
		return false;
	}
	
	public boolean allPiecesAtStart(boolean p1Turn) {
		//true = player 1 turn, false = player 2 turn
		if(p1Turn)
			return allPiecesAtStart(p1);
		else return allPiecesAtStart(p2);
	}
	
	private boolean allPiecesAtStart(Player p) {
		//Loop for each players piece
		for(int i = 0; i < 7; i++) {
			//Check if that piece is located at start
			if(p.getLocation(i) != Piece.OFF_THE_BOARD)
				return false;
		}
		return true;
	}
	
	/**
	 * Checks who's turn it is and calls {@link #newPiece(Player, int)}
	 * 
	 * <p>Checks who's turn it is and passes the appropriate player to move their
	 * new piece
	 * 
	 * @param p1Turn The player who's turn it is
	 * @param roll The player's roll
	 * @return boolean The return value from {@link #newPiece(Player, int)}
	 * @throws IllegalMoveException illegal move exception
	 */
	public boolean newPiece(boolean p1Turn, int roll) throws IllegalMoveException {
		//true = player 1 turn, false = player 2 turn
		if(p1Turn)
			return newPiece(p1, roll);
		else return newPiece(p2, roll);
	}
	
	/**
	 * Moves a players piece onto the board
	 * 
	 * <p>Checks that the move is legal. Calls {@link Player#move(int, int) Player.move()}
	 * to move a piece from start. Calls {@link #reverseSearch(int, Player) reverseSearch()}
	 * on the new piece and stores the new Pair. Then passes the new Pair info to the
	 * {@link Board#update(Player, char, int) Board.update()} to update the board. Checks
	 * if the player rolled 4 and landed on the starting rossette. 
	 * @param player
	 * @param roll
	 * @return True if player rolled on starting rossette, false if otherwise
	 * @throws IllegalMoveException
	 */
	private boolean newPiece(Player player, int roll) throws IllegalMoveException {
		int location = Piece.OFF_THE_BOARD;
		//checks if the new piece move is legal
		if(isIllegalMove(player, roll))
			throw new IllegalMoveException(roll);
		//moves the piece
		player.move(roll, player.indexOf(location));
		//stores the new location
		int newLocation = location+roll;
		//uses ReverseSearch to convert the location into coordinates
		//stores it in a pair 
		Pair pair = reverseSearch(newLocation, player);
		//updates the board of the move using the new coordinates
		theBoard.update(player, pair.getFirst(), pair.getSecond());
		//checks if roll was 4 to send an indicator of the player
		//being able to go again
		return roll == 4;		
	}
	
	/**
	 * Checks if the move from start is legal
	 * 
	 * <p>Checks if the player has a piece at the starting square trying
	 * to move to. 
	 * 
	 * @param player The player who's turn it is
	 * @param roll The player's roll
	 * @return True if move is illegal, false if move is legal
	 */
	private boolean isIllegalMove(Player player, int roll) {
		int playerNewIndex = player.indexOf(Piece.OFF_THE_BOARD + roll);
		//player has a piece on one of the starting squares
		if(playerNewIndex != -1)
			return true;
		return false;
	}
	
	/**
	 * Passes the correct parameters according to who's turn it is
	 * 
	 * @param column Column of the board
	 * @param row Row of the board
	 * @param roll Player's roll
	 * @param p1Turn Who's turn it is
	 * @return boolean True move was made, false otherwise
	 * @throws NoSuchSpaceException Illegal square exception
	 * @throws IllegalMoveException illegal move exception
	 */
	public boolean move(char column, int row, int roll, boolean p1Turn) throws NoSuchSpaceException, IllegalMoveException, NotYourSquareException {
		//true player 1 turn, false player 2 turn
		if(p1Turn)
			return move(p1, p2, column, row, roll);
		else return move(p2, p1, column, row, roll);
	}
	
	/**
	 * Moves a player's piece
	 * 
	 * <p>Checks if the space requested to move exists. Then checks if the space
	 * requested is a legal square. Moves the piece  by changing its stored location
	 * and removes the graphical representation from the board. Checks if the new location is
	 * the player scoring, if so passes {@link Board#update(Player, char, int) Board.update()}
	 * the SCORED constants to complete the score. Uses{@link #reverseSearch(int, Player) reverseSearch()}
	 * to find the Pair. Uses the pair info to pass it to {@link Board#update(Player, char, int) Board.update()}
	 * to update the graphical display of the piece. Then checks if the new location is occupied by the
	 * opponent, if so "knocks" them off the board by resetting the opponents piece.
	 * Lastly checks if the player landed on a rossette to go again by 
	 * calling {@link Board#isRossette(char, int) Board.isRossette()}. 
	 * 
	 * @param player The player who's turn it is
	 * @param opponent The opponent
	 * @param column Column indicator
	 * @param row Row indicator
	 * @param roll The player's roll
	 * @return boolean True if the player is on a Rossette, false otherwise
	 * @throws NoSuchSpaceException
	 * @throws IllegalMoveException
	 */
	private boolean move(Player player, Player opponent, char column, int row, int roll) throws NoSuchSpaceException, IllegalMoveException, NotYourSquareException{
		if(row == 1 && player.getColor().equals(Color.WHITE) || row == 3 && player.getColor().equals(Color.BLACK))
			throw new NotYourSquareException(row);
		//Creates Pair of requested coordinates
		Pair initial = new Pair(column, row);
		//Checks if the space exists
		if(!translator.containsKey(initial))
			throw new NoSuchSpaceException(column, row);
		
		//Gets int location of the Pair
		int location = translator.get(initial);
		
		//Checks if the move is legal
		if(isIllegalMove(location, roll, player, opponent)) {
			//Pair destination = reverseTranslator.get(location+roll, p);
 			//2 replace with future location 
			throw new IllegalMoveException(column, row, roll);
		}
		
		//Moves the player's piece
		player.move(roll, player.indexOf(location));
		//Removes the old location piece from the board
		theBoard.removePiece(column, row);
		int newLocation = location+roll;
		
		//Checks if the player's move is them scoring
		if(newLocation == Piece.SCORED) {
			//Passes the scored constants to update the board with
			theBoard.update(player, Piece.SCORED_COLUMN, Piece.SCORED_ROW);
			return false;
		}
		
		//Finds the new location in coordinate form
		Pair playerNewLocation = reverseSearch(newLocation, player);
		Pair opponentPotentialLocation = reverseSearch(newLocation, opponent);
		
		//Checks if the opponent has a piece at the new location
		if(opponent.indexOf(newLocation) != -1 && opponentPotentialLocation.getSecond() == 2)
			//Resets the opponents piece due to being knocked off
			opponent.reset(opponent.indexOf(newLocation));
		
		//Puts the piece on its new location on the board
		theBoard.update(player, playerNewLocation.getFirst(), playerNewLocation.getSecond());
		
		//Checks if the new location is on a Rossette square
		return Board.isRossette(playerNewLocation.getFirst(), playerNewLocation.getSecond());
	}

	/**
	 * Checks if the move is legal
	 * 
	 * <p>First, checks if player has a piece on the requested square. Then checks
	 * if the player is not rolling exactly to the scored square. Next it checks
	 * if the player is blocked by its own piece on the square being moved to.
	 * Lastly it checks if the middle rossette is occupied and if the player is 
	 * trying to roll there. Passing all these tests means the move is legal.
	 * 
	 * @param location Requested location on the board
	 * @param roll Player's roll
	 * @param player The player who's turn it is
	 * @param opponent The opponent
	 * @return True if illegal move, false if move is legal
	 */
	private boolean isIllegalMove(int location, int roll, Player player, Player opponent) {
		int index = player.indexOf(location);
		
		//Illegal move: no owned piece on square
		if(index == -1)
			return true;
		//Illegal move: Too many squares to move to score 
		if(location + roll > Piece.SCORED)
			return true;
		if(location + roll == Piece.SCORED)
			return false;
		
		//Illegal move: player's own piece on to move square
		int playerNewIndex = player.indexOf(location + roll);
		if(playerNewIndex != -1)
			return true;
		
		
		int opponentNewIndex = opponent.indexOf(location + roll);
		//Pair playerPair = reverseSearch(location+roll, player);
		Pair oppPair = reverseSearch(location+roll, opponent);
		
		//Illegal move: Opponent's piece on middle rossette
		//Checks if opponent has a piece on board
		if(opponentNewIndex == -1)
			return false;
		else if(oppPair.getSecond() == 2)
			return false;
		int opponentLocation = opponent.getLocation(opponentNewIndex);
		if(opponentLocation == Piece.ROSSETTE)
			return true;
		
		return false;
	}
	
	/**
	 * Checks if the requested coordinates exist
	 * 
	 * @param column Column indicator
	 * @param row Row indicator
	 * @return True if coordinates exist, false otherwise
	 *
	private boolean containsKey(char column, int row) {
		//Loops through all pairs of the board
		for(Pair p : translator.keySet())
			//Checks the column and row exist
			if(p.getFirst() == column && p.getSecond() == row)
				return true;
		return false;
	}*/
	
	/**
	 * Gets the int location of a square from a requested coordinate pair
	 * 
	 * @param column Column indicator
	 * @param row Row indicator
	 * @return int The int location of a square, -1 otherwise
	 *
	private int get(char column, int row) {
		//Loops through all pairs of the board
		for(Map.Entry<Pair, Integer> entry : translator.entrySet())
			//Checks if the requested Pair exists
			if(entry.getKey().getFirst() == column && entry.getKey().getSecond() == row)
				return entry.getValue();
		return -1;
	}*/
	
	/**
	 * Converts integer location into Pair<> coordinates
	 * 
	 * <p>Searches through the Map then checks if the location exists. It checks
	 * if the space exists, by comparing the integer square, checking what row it
	 * is in, and seeing which color the player is.
	 * 
	 * @param location Location of a square on the board
	 * @param p A player
	 * @return result The Pair<> coordinates, null if location DNE 
	 */
	private Pair reverseSearch(int location, Player p){
		//For loop for each square of the board in the Coordinate format
		for(Map.Entry<Pair, Integer> entry: translator.entrySet()) {
			//Pulls the column of the Pair 
			char column = entry.getKey().getFirst();
			//Pulls the row of the Pair
			int row = entry.getKey().getSecond();
			//Pulls the int location identifier
			int space = entry.getValue();

			/*Compares requested location to the Pairs location, and to which player
			 * it belongs
			 */
			if(location == space && (row == 2 || row == 1 && p.getColor() == Color.BLACK || row ==3 && p.getColor() == Color.WHITE)) {
				Pair result = new Pair(column, row);
				return result;
			}
		}
		return null;				
	}
	
	/**
	 * Gets the player who's turn it is name
	 * 
	 * @param p1Turn Who's turn indicator
	 * @return str A player's name
	 */
	public String getPlayerName(boolean p1Turn) {
		if(p1Turn)
			return p1.getName();
		else return p2.getName();
	}

	/**
	 * Checks if either player won 
	 * 
	 * @return boolean True if a player won, false otherwise
	 */
	public boolean isGameOver() {
		return p1.win() || p2.win();			
	}
	
	/**
	 * Return winners name
	 * @return String Winner's Name
	 */
	public String theWinner() {
		if(p1.win())
			return p1.getName();
		else return p2.getName();
	}
	/**
	 * Returns a String with the scores and board
	 * 
	 * @return results Player's score lines and the board
	 */
	public String toString() {
		String results = p1.getName() + " Score: " + p1.getScore() + "\n";
		results += p2.getName() + " Score: " + p2.getScore() + "\n";
		results += theBoard;
		return results;				
	}
}
