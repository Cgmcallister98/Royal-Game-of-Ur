
public class Roll {
	/**
	 * Array of 4 Dice to be rolled
	 */
	private Dice[] rolled = new Dice[4];
	
	/**
	 * The total roll of the dice
	 */
	int count = 0;

	/**
	 * Fills rolled[] with Dice objects
	 */
	public Roll() {
		for(int i = 0; i < rolled.length; i++)
			rolled[i] = new Dice();
	}
	
	/**
	 * Calculates the dice roll
	 * 
	 * @return count Sum of the rolled dice
	 */
	public int roll() {
		count = 0;
		//goes through all Dice elements in rolled
		for(Dice d : rolled) 
			//add 1 to count if true
			if(d.roll())
				count++;
		return count;
	}
	
	/**
	 *Return a String about the roll
	 *
	 *@return str Details about the roll
	 */
	public String toString() {
		String str="";
		int x = 1;
		for(Dice d : rolled) {
			str +="Die "+ x + ": " + d +"\n";
			x++;
		}
		str += "Your roll total is: " + count;
		return str;
	} 
}

