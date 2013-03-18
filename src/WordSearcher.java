import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A word search Class that contains a puzzle and methods for solving it
 * @author sstruhar
 *
 */


public class WordSearcher
{
	private String[][] puzzle;
	private String[][] solution;
	private WordCollection miniWordCollection;
	private ArrayList<String> wordList;
	private String[] headers;
	private ArrayList<ArrayList<Object>> solutionsList = new ArrayList<ArrayList<Object>>();
	private ArrayList<String> failureList = new ArrayList<String>();
	private final String gameBoardHeader = "\n[GAME BOARD]\n";
	private final String solutionBoardHeader = "\n[SOLUTION BOARD]\n";
	private final Compass compass = new Compass();
	private int grid_width;
	private int grid_height;
	private int volume;
	private boolean canOverlap;
	private final boolean allowReversedWords = true;
	private final String NEW_LINE = "\n";
	private final String OUT_OF_BOUNDS = "@";
	private final String PLACE_HOLDER = "";
	private final String SOLUTION_MARKER = "*";
	private final String SPACER = " ";
	private final int GRID_VOL_MULTIPLIER = 1;//increase this to increase the difficulty of the puzzle. 
	private int allowedPlacementAttempts;
	@SuppressWarnings("unused")
	private int numCompares = 0;
	private int wordsSet = 0;
	private int wordsSetAttempts = 0;
	private boolean traceProgress = false;
    private boolean badWord = false;

	/**
	 * Default constructor (call the overloaded constructor with a default word list)
	 */
	public WordSearcher()
	{
		this(new WordCollection());
	}
	/**
	 * Overloaded Constructor.  Call the other overloaded constructor with the given word list and default values
	 * @param a_Word_List 
	 */
	public WordSearcher(WordCollection a_Word_List)
	{
		this(a_Word_List,10,10, false);
	}
	/**
	 * Constructor with all values specified
	 * @param a_wordList a word collection to make the puzzle out of
	 * @param a_width the width of the grid
	 * @param a_height the height of the grid
	 * @param allowOverlaps allow words to overlap if they share a letter
	 */
	public WordSearcher(WordCollection a_wordList, int a_width, int a_height, boolean allowOverlaps)
	{
		grid_width = a_width;
		grid_height = a_height;
		canOverlap = allowOverlaps;
		volume = a_width * a_height;//the total number of cells in the grid
		if(a_wordList != null)
		{
			miniWordCollection = a_wordList;
		}
		else
		{
			System.out.println("Word List is null.");
			System.exit(0);
		}
		wordList = miniWordCollection.getEnglishInEnglishCollection();
		allowedPlacementAttempts = wordList.size() * 1000;//set the number of attempt to set a word in the puzzle before giving up
		// this is necessary because of the random nature of the list, word length, grid size and themes.  It may (and in fact does) become impossible
		//to set words into a puzzle that is to small.
		if((volume < getAllStringsLength() * GRID_VOL_MULTIPLIER) || !proveWordsAgainstDimensions())//if the grid is not of adequate size
		{
			View.showMessage("Inadequate grid dimensions for the randomly selected words." , Color.RED , Color.RED);
			System.out.println("Inadequate grid dimensions for the randomly selected words.");
			return;
		}
		try
		{
			generatePuzzleAndSolution();//solution creation is done algorithmically, so if the puzzle is too large, (30000x3000) it will run out of memory
		}catch (OutOfMemoryError e)
		{
			throw e;
		}
	}
	/**
	 * Generate the puzzle and the solution
	 */
	private void generatePuzzleAndSolution()
	{
		puzzle = new String[grid_height][grid_width];
		solution = new String[grid_height][grid_width];
		for(int i = 0; i < grid_height; i++)
		{
			for(int j = 0; j < grid_width; j++)
			{
				puzzle[i][j] = PLACE_HOLDER;//Fill the puzzle with placeholder letters (could be any letter or none)
			}
		}
		while(wordsSet < wordList.size())
		{
			//try to set the word in the puzzle
			if(setWord(wordList.get(wordsSet).toUpperCase(), compass.getRandomCoordinate(grid_height, grid_width) , compass.getRandomDirection()))
			{
				wordsSetAttempts = 0;
			}
			else
			{
				if(wordsSetAttempts > allowedPlacementAttempts)
				{
					//This is the case where we have exhausted the practical number of attempts to place a word in the puzzle.
					System.out.println("Could not find a suitable location in the puzzle for " + wordList.get(wordsSet) + ": " + wordsSetAttempts+ "\n Redoing puzzle"); /*+ "\nTry a larger grid."); */
                    badWord = true;
                    return;
					//System.exit(0);
				}
			}
		}
		//fill the remaining places with random English alpha letters
		for (int i = 0; i < grid_height; i++)
		{
			for(int j = 0; j < grid_width; j++)
			{
				if(puzzle[i][j].equals(PLACE_HOLDER))
				{
					char c = (char)((Math.random()*26) + 'a');
					puzzle[i][j] = String.valueOf(c).toUpperCase();
				}
			}
		}
		//Now create the solution
		solvePuzzle(puzzle , wordList);
	}
	/**
	 * Solve a puzzle with a list of words
	 * @param grid the puzzle to solve
	 * @param dictionary the list of words to find
	 * @return a 2d array of the solution
	 */
	public String[][] solvePuzzle(String[][] grid, ArrayList<String> dictionary)
	{
		solution = copyArray(grid);
		failureList.clear();
		solutionsList.clear();
		numCompares = 0;
		for(int i = 0; i < dictionary.size(); i++) 
		{
			if(findWord(dictionary.get(i), solution))
			{
				if(traceProgress)
				{
					System.out.println("Searching for word #" + (i+1) + ": " + wordList.get(i));
					ArrayList<Object> foundCoordinateDirection = solutionsList.get(solutionsList.size()-1);
					Coordinate wordLocation = (Coordinate)foundCoordinateDirection.get(1);
					Direction wordDirection = (Direction)foundCoordinateDirection.get(2);
					System.out.println("Solution Start Coordinate is: " + wordLocation + " Direction: " + wordDirection);
				}
				continue;
			}
			else
			{
				failureList.add(dictionary.get(i));
				if(traceProgress)
				{
					System.out.println("Failed to find word #" + (i+1) + ": " + dictionary.get(i) );
				}
			}
		}
		for(int i = 0; i < solutionsList.size(); i++)
		{
			String word = (String)solutionsList.get(i).get(0);
			Coordinate coord = (Coordinate)solutionsList.get(i).get(1);
			Direction dir = (Direction)solutionsList.get(i).get(2);
			setWordInSolution(word.toUpperCase(), coord, dir, solution);
		}
		return solution;
	}
	/**
	 * Set up the solution array
	 * @param inputSolution is the array to make the solution identical too
	 */
	public void setSolutionArray(String[][] inputSolution)
	{
		if(inputSolution == null)
		{
			solution = null;//null it out at the user's request.  This is for you Jasthi
		}
		else
		{
			solution = copyArray(inputSolution);//otherwise, the solution becomes the input array
		}
	}
	/**
	 * Find a word in a grid
	 * @param searchWord the word to find
	 * @param grid the grid to find it in
	 * @return returns true if the word is found
	 */
	private boolean findWord(String searchWord, String[][] grid)
	{
		for(int row = 0; row < grid_height; row++)
		{
			for(int column = 0; column < grid_width; column++)
			{ 
				numCompares++;

				{
					for(Direction dir: Direction.values())
					{
						Coordinate returnCoordinate = new Coordinate(row,column);
						Coordinate testCoordinate = new Coordinate(row,column);
						String testWord = getLetterAt(testCoordinate, puzzle);

						for(int k = 1; k < searchWord.length(); k++)
						{
							String tempLetter = getLetterAt(testCoordinate, dir, puzzle);
							numCompares++;
							if(tempLetter.equals(OUT_OF_BOUNDS)) 
							{
								break;
							}
							testWord += getLetterAt(testCoordinate, dir, puzzle);
							testCoordinate = compass.getNextCoordinate(testCoordinate, dir);
						}
						numCompares++;
						if(testWord.toUpperCase().equals(searchWord.toUpperCase())) 
						{
							ArrayList<Object> answer = new ArrayList<Object>();
							answer.add(searchWord);
							answer.add(returnCoordinate);
							answer.add(dir);
							solutionsList.add(answer);//add the solution to the solutions list
							return true;
						}
					}

				}

			}
		}
		return false;
	}

	/**
	 * Set a word into a grid
	 * @param a_Word the word to set
	 * @param startCoordinate the start location to set the word
	 * @param startDirection the direction the word should go
	 * @return return true if the word could be set false if not
	 */
	private boolean setWord(String a_Word , Coordinate startCoordinate , Direction startDirection)
	{
		if(puzzle != null)
		{
			wordsSetAttempts++;
			Coordinate nextCoordinate = compass.getNextCoordinate(startCoordinate, startDirection);
			if(edgeDetect(startCoordinate, startDirection , a_Word))
			{
				setLetterAt(startCoordinate , String.valueOf(a_Word.charAt(0)), puzzle);
				for(int j = 1; j < a_Word.length(); j++)
				{
					setLetterAt(nextCoordinate , String.valueOf(a_Word.charAt(j)), puzzle);
					nextCoordinate = compass.getNextCoordinate(nextCoordinate, startDirection);
				}
				wordsSet++;
				return true;
			}
		}
		else
		{
			System.out.println("Attempt to set word in a null puzzle.");//this is bad
			return false;
		}
		return false;
	}
	/**
	 * Set a solution word into the solution grid
	 * @param a_Word the word to set
	 * @param startCoordinate the start location of the word
	 * @param direction the direction the word should go
	 * @param grid the grid to set it in
	 */
	private void setWordInSolution(String a_Word , Coordinate startCoordinate, Direction direction, String[][] grid)
	{
		if(grid != null)
		{
			Coordinate nextCoordinate = compass.getNextCoordinate(startCoordinate, direction);
			setLetterAt(startCoordinate , String.valueOf(a_Word.charAt(0)) + SOLUTION_MARKER, grid);
			for(int j = 1; j < a_Word.length(); j++)
			{
				setLetterAt(nextCoordinate , String.valueOf(a_Word.charAt(j)) + SOLUTION_MARKER, grid);
				nextCoordinate = compass.getNextCoordinate(nextCoordinate, direction);
			}
		}
		else
		{
			System.out.println("Attempt to set a solution word in a null solution.");
			System.exit(0);
		}
	}
	/**
	 * Detect if a word can fit in a particular vector
	 * @param startCoordinate the start location
	 * @param startDirection the direction to look
	 * @param a_Word the word to try and set
	 * @return true if the word can fit.  False if not
	 */
	private boolean edgeDetect(Coordinate startCoordinate, Direction startDirection, String a_Word)
	{
		String testLetter = getLetterAt(startCoordinate, puzzle);
		String wordLetter;
		if(!allowReversedWords)//this is always true in this assignment, but can be set to false which will prevent backwards word directions
		{
			switch (startDirection) 
			{
			case NW:
			case W:
			case NE:
			case N:
				return false;
			}
		}
		if (testLetter.equals(OUT_OF_BOUNDS))//we are already our of bound here.
		{
			return false;
		}
		Coordinate nextCoordinate = startCoordinate;
		for(int i = 0; i < a_Word.length(); i++)
		{
			wordLetter = String.valueOf(a_Word.charAt(i));
			testLetter = getLetterAt(nextCoordinate, puzzle);
			if(testLetter.equals(OUT_OF_BOUNDS))
			{
				return false;
			}
			else
			{
				if(testLetter.equals(PLACE_HOLDER))
				{//this block of code can prevent non-sharing overlaps where 2 words overlap but do not share a letter.  Commented out as the instructor
					//states that this is legal.
					/*//Check if we are oblique and if so, and we aren't to overlap, prevent non-letter sharing overlap
					if(getLetterAt(nextCoordinate, Direction.N, puzzle).equals(PLACE_HOLDER) && !getLetterAt(nextCoordinate, Direction.S, puzzle).equals(PLACE_HOLDER) && canOverlap == false && startDirection.isOblique())
					{
						return false;
					}
					if(getLetterAt(nextCoordinate, Direction.S, puzzle).equals(PLACE_HOLDER) && !getLetterAt(nextCoordinate, Direction.N, puzzle).equals(PLACE_HOLDER) && canOverlap == false && startDirection.isOblique())
					{
						return false;
					}
					else
					{*/
					nextCoordinate = compass.getNextCoordinate(nextCoordinate, startDirection);
					continue;
					//}
				}
				if(!testLetter.equals(PLACE_HOLDER))//Here we find an overlapping word
				{
					if(canOverlap == true)
					{
						if(testLetter.equals(wordLetter))//if they happen to share the same letter in this spot
						{
							nextCoordinate = compass.getNextCoordinate(nextCoordinate, startDirection);
							continue;
						}
						else return false;
					}
					else return false;
				}
			}
		}
		return true;
	}
	/**
	 * Get a letter at a particular coordinate
	 * @param location the coordinate to look at
	 * @param grid the grid to look in
	 * @return return the string at that location
	 */
	private String getLetterAt(Coordinate location, String[][] grid)
	{
		try
		{
			return grid[location.getX()][location.getY()];
		} catch (IndexOutOfBoundsException e)
		{
			return OUT_OF_BOUNDS;
		}
	}
	/**
	 * Get a letter at the next location in a given direction from a start location
	 * @param location location to start
	 * @param direction direction to look
	 * @param grid grid to look in
	 * @return a string of the next spot in that direction
	 */
	private String getLetterAt(Coordinate location, Direction direction, String[][] grid)
	{
		try
		{
			switch (direction) 
			{
			case N:
				return grid[location.getX()-1][location.getY()];
			case NE:
				return grid[location.getX()-1][location.getY()+1];
			case E:
				return grid[location.getX()][location.getY()+1];
			case SE:
				return grid[location.getX()+1][location.getY()+1];
			case S:
				return grid[location.getX()+1][location.getY()];
			case SW:
				return grid[location.getX()+1][location.getY()-1];
			case W:
				return grid[location.getX()][location.getY()-1];
			case NW:
				return grid[location.getX()-1][location.getY()-1];

			}
			return OUT_OF_BOUNDS;

		} catch (IndexOutOfBoundsException e)
		{
			return OUT_OF_BOUNDS;
		}
	}
	/**
	 * Set a letter in a grid at a coordinate
	 * @param location location to set the letter
	 * @param newLetter the letter to put there
	 * @param grid the grid to put it in
	 */
	private void setLetterAt(Coordinate location, String newLetter, String[][] grid)
	{
		try
		{
			grid[location.getX()][location.getY()] = newLetter;
		} catch (IndexOutOfBoundsException e)
		{
			System.out.println("Fatal Error");
			System.exit(0);
		}
	}
	/**
	 * Get the puzzle height
	 * @return int of the height
	 */
	public int getHeight()
	{
		return grid_height;
	}
	/**
	 * Get the puzzle width
	 * @return an int of the puzzle width
	 */
	public int getWidth()
	{
		return grid_width;

	}
	/**
	 * Get the total number of letters in all the words.  Used to see of the grid is large enough
	 * @return an integer equal to the total number of letters in the words
	 */
	private int getAllStringsLength()
	{
		int length = 0;
		for(String item : wordList)
		{
			length += item.length();
		}
		return length;
	}
	/**
	 * Prove that the grid is at least as wide and tall as the words are long.
	 * @return
	 */
	private boolean proveWordsAgainstDimensions()
	{
		for(String item : wordList)
		{
			if (item.length() > grid_width || item.length() > grid_height)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Return a string that represents the puzzle, the word list and the solution as per instructions.
	 *un-commenting and tweaking will produce nice little grid displays
	 */
	public String toString() {
		String answer = NEW_LINE + miniWordCollection.toString();
		if(puzzle != null && solution != null)
		{
			answer += gameBoardHeader;
			for(int i = 0; i < grid_height; i++)
			{  
				for(int j = 0; j < grid_width; j++)
				{  
					answer += puzzle[i][j] + " , ";//SPACER;
				}  
				answer += NEW_LINE;
			} 
			answer += NEW_LINE;
			answer += solutionBoardHeader;
			for(int i = 0; i < grid_height; i++)
			{  
				for(int j = 0; j < grid_width; j++)
				{  
					answer += solution[i][j] + " , ";//SPACER;
				}  
				answer += NEW_LINE;
			}
			answer += NEW_LINE;// + "Number of String Compares: " + numCompares + NEW_LINE;
			return answer; 
		}
		else
		{
			answer += NEW_LINE + "No Puzzle to solve." + NEW_LINE;
			return answer;
		}
	}
	/**
	 * Get the puzzle array
	 * @return
	 */
	public String[][] getPuzzle()
	{
		return puzzle;
	}
	/**
	 * Get the solution array
	 * @return
	 */
	public String[][] getSolution()
	{
		return solution;
	}
	/**
	 * Get the list of words
	 * @return
	 */
	public ArrayList<String> getWordList()
	{
		return wordList;
	}
	/**
	 * Get the headers for the jTable
	 * @return
	 */

    public boolean getBadWord(){
        return badWord;
    }
	public String[] getHeaders()
	{
		headers = new String[grid_width];
		for(int i = 0; i < headers.length; i++)
		{
			headers[i] = SPACER;
		}
		return headers;
	}
	/**
	 * Get the list of unique themes from this puzzle's particular list of words
	 * @return an array list of themes from this puzzles list of words
	 */
	public ArrayList<String> getThemes()
	{
		ArrayList<String> answer = new ArrayList<String>();
		answer.addAll(miniWordCollection.uniqueThemes());
		return answer;
	}
	/**
	 * Get this puzzle's word collection
	 * @return
	 */
	public WordCollection getMiniWordCollection()
	{
		return miniWordCollection;
	}
	/**
	 * Copy an array and return the copy (Deep Copy)
	 * @param grid
	 * @return
	 */
	public String[][] copyArray(String[][] grid) {
		String[][] copy = new String[grid_height][grid_width];
		for (int i = 0; i < grid.length; ++i)
			copy[i] = grid[i].clone();
		return copy;
	}
}
