import java.io.*;
import java.util.Scanner;
public class Main {
	
	/**
	 * LEARN HOW TO DO
	 * @param args Command line args
	 * @throws IOException Exception handling
	 */
	
	public static void main(String[] args) throws IOException {
		//Scanner for input from keyboard
		Scanner kybd = new Scanner(System.in);
		
		Game theGame = null;
		
		while(true) {
			//Prints menu
			menu();
			//Reads in users choice
			char choice = kybd.next().charAt(0);
			//Passes choice 
			boolean playGame = processChoice(choice, kybd);
			//Checks for exiting program
			if(!playGame)
				break;
			//Creates game 
			theGame = createGame(kybd);
			//Create Roll object
			Roll theDice = theGame.roll();	
			//Decides who goes first
			boolean p1Turn = whoGoesFirst(theGame, theDice);
			System.out.println(theGame.getPlayerName(p1Turn) + " goes first.");
			System.out.println();
			//Run loop till game is over
			while(!theGame.isGameOver()) {
				//Prints out scores and board
				System.out.println(theGame);
				System.out.println("It is " + theGame.getPlayerName(p1Turn) +
						"'s turn.");
				//Rolls for the player
				int roll = theDice.roll();
				System.out.println(theDice);
				//Checks if player can move
				//Variable for is player can go again
				boolean goAgain = true;
				//loop and a half
				while(true) {
					//Checks if player can move from start
							System.out.print("Would you like to move "
									+ "a piece from start? Y/N/F:  ");
							//Read in players selection
							char yn = kybd.next().charAt(0);
							//if yes
							if(Character.toLowerCase(yn) == 'y') {
								if(theGame.canPlayerMoveFromStart(p1Turn)) {
									try {
										//Move the new piece
										goAgain = theGame.newPiece(p1Turn, roll);
										break;
									} catch (Exception e) { 
										System.err.println(e.getMessage());
									}
								}
								else {
									System.err.println("Error: No Pieces at start");
								}
							}//Player didn't move piece from start
							else if(Character.toLowerCase(yn) == 'n'){
								System.out.print("Enter a Space to move your piece from: ");
								//Read in piece
								String space = kybd.next();
									try {
										//Parse input to be readable
										Pair pair = parse(space);	
										//Get column
										char column = pair.getFirst();
										//Get row
										int row = pair.getSecond();
											try{
												//Move requested piece
												goAgain = theGame.move(column, row, roll, p1Turn);
												break;
											} catch (Exception e) {
												System.err.println(e.getMessage());
											}
									}
									catch (IOException e) {
										System.err.println(e.getMessage());
									}
								}
							else if(Character.toLowerCase(yn) == 'f') {
								System.err.println("Turn Forfeited");
								goAgain = false;
								break;
							}
							else {
								System.err.println("Not a valid selection");
							}
				}//End of playable turn loop
				//Checks if player can go again
				if(!goAgain)
					//Swaps turn
					p1Turn = !p1Turn;
			}//End of Game over loop	
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("*********************************************************");
			System.out.println("* Congradulations " + theGame.theWinner() + ", you won! *");
			System.out.println("*********************************************************");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("Would you like to play again? Y/N");
			char yn = kybd.next().charAt(0);
			if(Character.toLowerCase(yn) != 'y') {
				System.out.println("Thanks for playing!");
				System.exit(0);
			}
		}//End of while loop
	}

	/**
	 * Prints the menu
	 */
	public static void menu() {
		System.out.println();
		System.out.println("Welcome to The Royal Game of Ur! "
				+ "\n\tPlease select and option from the menu below");
		System.out.println("*****************************");
		System.out.println("*         Main Menu         *");
		System.out.println("*****************************");
		System.out.println("     A -- 2 Player Game");
		System.out.println("     B -- How To Play");
		System.out.println("     C -- Credits");
		System.out.println("     D -- Exit");
		System.out.println();
		System.out.print("Enter your selection: ");
	}

	/**
	 * Executes the users menu choice
	 * 
	 * <p> Sets the indicator if a game will be played to true, play, takes in
	 * the users menu choice and runs it into a switch. Runs the appropriate case then 
	 * returns play.
	 * 
	 * @param ch User's menu choice
	 * @param kybd Scanner object
	 * @return play True if game will be played, false otherwise
	 */
	private static boolean processChoice(char ch, Scanner kybd) {
		boolean play = true;
		
		switch (ch) {
		case 'a':
		case 'A':
			System.out.println("Game is starting...");
			System.out.println();
			break;
		case 'b':
		case 'B':
			howToPlay();
			break;
		case 'c':
		case 'C':
			credits();
			break;
		case 'd':
		case 'D':
			play = false;
			System.out.println("Exiting...");
			break;
		}
		
		return play;
	}
	
	/**
	 * Instantiates a Game object
	 * 
	 * @param kybd Scanner object
	 * @return Game Game object
	 */
	public static Game createGame(Scanner kybd) {
		String p1Name, p2Name;
		//Ask for first player's name
		System.out.print("Enter Player 1 name: ");
		p1Name = kybd.next();
		//Ask for second player's name
		System.out.print("Enter Player 2 name: ");
		p2Name = kybd.next();
		//Creates Game object 
		return new Game(p1Name, p2Name);
	}	
		
	/**
	 * Determines which player goes first
	 * 
	 * <p>Rolls the dice twice and stores each value. 
	 * Checks for which roll is greater, returns boolean
	 * value based on result. 
	 * 
	 * @param game The game being played
	 * @param theDice Dice object
	 * @return boolean True player 1 goes first, false player 2 goes first
	 */
	private static boolean whoGoesFirst(Game game, Roll theDice) {
		while(true) {
			//Rolls the dice for each player
			int roll1 = theDice.roll();
			int roll2 = theDice.roll();
			
			System.out.println();
			System.out.println(game.getPlayerName(true) + " rolled " + roll1);
			System.out.println(game.getPlayerName(false) + " rolled " + roll2);
			
			if(roll1 > roll2)
				//player 1 goes first
				return true;
			else if(roll2 > roll1)
				//player 2 goes first
				return false;
		}
	}
	
	/**
	 * Converts a String into a Pair
	 * 
	 * @param space Coordinates of a space to be converted   
	 * @return Pair Coordinates separated into parts 
	 */
	private static Pair parse(String space) throws IOException{
		if(space.length() != 2)
			throw new IOException("Error: Input must be Column and then Row!");
		char column = space.charAt(0);
		column = Character.toUpperCase(column);
		int row = Integer.parseInt(space.substring(1));
		return new Pair(column, row);
	}
	
	/**
	 * Prints how to play the game
	 */
	public static void howToPlay() {
		String instructions = "Objectivce: \n\nMove all 7 of one's pieces around the board then off before the opponent\n\nHow To Play: \n\n1.Roll the dice\n2.Move a piece the distance of the dice roll. \n";//type up the instructions on how to play
		System.out.println(instructions);		
	}

	/**
	 * Prints the credits of the program
	 */
	public static void credits() {
		String credits = "Made by Cassidy McAllister & Ari Mermelstien";
		System.out.println(credits);
	}
}
