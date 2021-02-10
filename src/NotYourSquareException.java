
public class NotYourSquareException extends Exception{
	private int row;
	
	public NotYourSquareException(int row){
		this.row = row;
	}
	
	public String getMessage() {
		return "Row " + row + " is not on your side of the board"; 
	}
}