import java.io.*;
import java.util.Scanner;
import java.util.Random;
public class Main {
	
	/**
	 * LEARN HOW TO DO
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
		//Scanner for input from keyboard
		Scanner kybd = new Scanner(System.in);
		
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
			Game theGame = createGame(kybd);
			//Create Roll object
			Roll theDice = theGame.roll();	
			//Decides who goes first
			boolean p1Turn = whoGoesFirst(theGame, theDice);
			System.out.println(theGame.getPlayerName(p1Turn) + " goes first.");
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
				if(!theGame.noPossibleMove(p1Turn, roll)) {
					//Variable for is player can go again
					boolean goAgain = false;
					//loop and a half
					while(true) {
						//Checks if player can move from start
						if(theGame.canPlayerMoveFromStart(p1Turn)) {
								System.out.print("Would you like to move "
										+ "a piece from start? Y/N:  ");
								//Read in players selection
								char yn = kybd.next().charAt(0);
								//if yes
								if(Character.toLowerCase(yn) == 'y')
									try {
										//Move the new piece
										goAgain = theGame.newPiece(p1Turn, roll);
										break;
									} catch (Exception e) { 
										System.err.println(e.getMessage());
									}	
/*implement no option*/}//Player didn't move piece from start
						
						System.out.print("Enter a Space to move your piece from: ");
						//Read in piece
						String space = kybd.next();
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
					}//End of playable turn loop
					//Checks if player can go again
					if(!goAgain)
						//Swaps turn
						p1Turn = !p1Turn;
				}else {
					System.out.println("No Legal Moves, Sorry!");
					//Swaps turn
					p1Turn = !p1Turn;
				}//End of non-playable turn loop
			}//End of Game over loop
			
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
	 * @return boolean True player 1 goes first, false player 2 goes first
	 */
	private static boolean whoGoesFirst(Game game, Roll theDice) {
		while(true) {
			//Rolls the dice for each player
			int roll1 = theDice.roll();
			int roll2 = theDice.roll();
			
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
	private static Pair parse(String space){
		char column = space.charAt(0);
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
