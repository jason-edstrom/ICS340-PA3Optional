/**
 * An enum to represent one of 8 directions
 * @author sstruhar
 *
 */
public enum Direction
{
	/*
	 * The values
	 */
	N(0), NE(1), E(2), SE(3), S(4), SW(5) , W(6), NW(7);

	private int value;

	/**
	 * Set the value of the direction
	 * @param a_value an integer value 0-7
	 */
	private Direction(int a_value) 
	{
		this.value = a_value;
	}
	/**
	 * Get the value of this enum
	 * @param a_value to ask for
	 * @return N,S,E,W,NW,SW,SE,etc
	 */
	public int getValue(int a_value)
	{
		return this.value;
	}
	/**
	 * Ask if this direction is diagonal
	 * @return true if diagonal
	 */
	public boolean isOblique()
	{
		if(value % 2 > 0)
		{
			return true;
		}
		else return false;
	}
	/**
	 * Returns the direction of the object
	 */
	public String toString() 
	{
		return this.name();
	}
}