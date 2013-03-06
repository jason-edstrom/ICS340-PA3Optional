/**
 * For the creation and deliver of coordinate objects to a caller
 * @author sstruhar
 *
 */

public final class Compass
{
	/**
	 * Get the next coordinate given a start coordinate and a direction
	 * @param startCoordinate is where you are now
	 * @param startDirection is the direction you intend to go
	 * @return a coordinate object that represents the spot you need to step
	 */
	public final Coordinate getNextCoordinate(Coordinate startCoordinate, Direction startDirection)
	{
		switch (startDirection) 
		{
		case N:
			return new Coordinate((startCoordinate.getX()-1) , (startCoordinate.getY()));
		case NE:
			return new Coordinate((startCoordinate.getX()-1) , (startCoordinate.getY()+1));
		case E:
			return new Coordinate((startCoordinate.getX()) , (startCoordinate.getY()+1));
		case SE:
			return new Coordinate((startCoordinate.getX()+1) , (startCoordinate.getY()+1));
		case S:
			return new Coordinate((startCoordinate.getX()+1) , (startCoordinate.getY()));
		case SW:
			return new Coordinate((startCoordinate.getX()+1) , (startCoordinate.getY()-1));
		case W:
			return new Coordinate((startCoordinate.getX()) , (startCoordinate.getY()-1));
		case NW:
			return new Coordinate((startCoordinate.getX()-1) , (startCoordinate.getY()-1));
		}
		return new Coordinate(0,0);
	}
	/**
	 * Gets a random coordinate for a given grid size
	 * @param grid_height height of the grid
	 * @param grid_width width of the grid
	 * @return a random coordinate inside the grid
	 */
	public final Coordinate getRandomCoordinate(int grid_height, int grid_width)
	{
		Coordinate randomCoordinate = new Coordinate((int)(Math.random()*grid_height),(int)(Math.random()*grid_width) );
		return randomCoordinate;
	}
	/**
	 * Get a random direction enum
	 * @return an enum with a random direction
	 */
	public final Direction getRandomDirection()
	{
		return Direction.values()[(int)(Math.random()*(Direction.values().length))];
	}

}
