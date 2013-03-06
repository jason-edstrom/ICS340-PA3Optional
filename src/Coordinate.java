/**
 * Represents an x and y coordinate
 * @author sstruhar
 *
 */
public class Coordinate
{
	private int x;
	private int y;

	/**
	 * Constructor
	 * @param a_x an x coordinate
	 * @param a_y a y coordinate
	 */
	public Coordinate(int a_x, int a_y)
	{
		setX(a_x);
		setY(a_y);
	}
	/**
	 * get the x value
	 * @return this object's x value
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * Set the x value
	 * @param x the x value to set
	 */
	private void setX(int x)
	{
		this.x = x;
	}
	/**
	 * Get the y coordinate
	 * @return this object's y coordinate
	 */
	public int getY()
	{
		return y;
	}
	/**
	 * Set the y coordinate
	 * @param y the y coordinate to set
	 */
	private void setY(int y)
	{
		this.y = y;
	}
	/**
	 * Output this x and y coordinate to a string
	 */
	public String toString()
	{
		return "Row: " + (x+1) + " - Column: " + (y+1);
	}

}
