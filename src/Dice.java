import java.util.Random;

public class Dice {
	/**
	 * Random object to generate roll
	 */
	private Random rand = new Random();
	/**
	 *  Variable to store the roll
	 */
	private boolean roll;

	/**
	 * Sets roll equal to a random boolean value and returns it
	 * 
	 * @return roll the value of a die roll
	 */
	public boolean roll(){
		roll = rand.nextBoolean();
		return roll;
	}

	/**
	 * Returns a String about what was rolled
	 * 
	 * @return str String about the roll
	 */
	public String toString() {
		String str = "" + (roll?1:0);
		return str;
	}
}
