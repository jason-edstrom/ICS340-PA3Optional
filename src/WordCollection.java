import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/**
 * 
 * @author sstruhar
 *
 */

public class WordCollection 
{
	/**
	 * An array list to store the words
	 */
	 private ArrayList<Word> words;

	/**
	 * A hash-map to keep track of uniqueness and indices
	 */
	private HashMap<Integer, Integer> keysAndIndices = new HashMap<Integer, Integer>();
	private final String DELIMETER = "\\|";
	private final String THEME_DELIMETER = ",";




	/**
	 * Default constructor calls the first overloaded constructor with the file path
	 */
	WordCollection()
	{
		/*mac*/
		//this("src/test2.txt");//assuming the file is UTF-8 please set your run configuration to display UTF-8 in console
		/*windows*/
		this("C:\\ics340\\words.txt");//assuming the file is UTF-8 please set your run configuration to display UTF-8 in console
	}

	/**
	 * A constructor to create a clean and empty copy of a word collection
	 * @param a_createClean is a boolean used to determine if the object should be instantiated empty (true)
	 * Failure to call with a true boolean will result in test data being loaded
	 */
	WordCollection(boolean a_createClean)
	{
		if(a_createClean)
		{
			words = new ArrayList<Word>();
		}
		else//otherwise, you're going get the test data from it's default location
		{
			try 
			{
				words = new ArrayList<Word>();
				readLinesInFile("C:\\ics340\\words.txt");
			} 
			catch (IOException e) 
			{
				System.out.println("There was an error reading or opening the file. Perhaps the file is empty.");
				System.exit(0);
			}
		}
	}
	/**
	 * A constructor that loads test data from a file path that is provided
	 * @param a_file_name is the file path to the test data file.
	 */
	WordCollection(String a_file_name)
	{
		try 
		{
			words = new ArrayList<Word>();
			readLinesInFile(a_file_name);
		} 
		catch (IOException e) 
		{
			System.out.println("There was an error reading or opening the file. Perhaps the file is empty.");
			System.exit(0);
		}
	}
	/**
	 * Adds a word to the collection 
	 * @param //a_line is a single line of text with 16 values separated by 15 commas with values thirteen and fourteen being space delimited lists
	 * @throws WordAdditionException is thrown if the file is empty, a line has other than 15 commas, or the file contains duplicate items
	 */
    // Added getWordCollection and setWordCollection for comparison and removal

    public ArrayList<Word> getWordCollection(){
        return words;
    }
    public void removeWord (Word word){
        words.remove(word);
    }

    public void addWordDirect (Word word){
        words.add(word);
    }

	public void addWord(String a_line) throws WordAdditionException
	{
		if(a_line.equals(""))
		{
			throw new WordAdditionException("File is empty. ");
		}
		String[] list = a_line.split(DELIMETER);
		List<String> tokens = Arrays.asList(list);
		if(tokens.size() != 16)
		{
			throw new WordAdditionException("Error: an input line is malformed. ");
		}
		ArrayList<String> themeListInEnglish = createThemeList(tokens.get(12).trim());
		ArrayList<String> themeListInLanguage = createThemeList(tokens.get(13).trim());

		Word nextWord = new Word(
				tokens.get(0).trim(), 
				tokens.get(1).trim(), 
				tokens.get(2).trim(), 
				tokens.get(3).trim(), 
				tokens.get(4).trim(), 
				tokens.get(5).trim(), 
				tokens.get(6).trim(), 
				tokens.get(7).trim(), 
				tokens.get(8).trim(), 
				tokens.get(9).trim(), 
				tokens.get(10).trim(), 
				tokens.get(11).trim(), 
				(themeListInEnglish), 
				(themeListInLanguage), 
				tokens.get(14).trim(), 
				tokens.get(15).trim()
				);
		if(!keysAndIndices.isEmpty() && keysAndIndices.containsKey(hashCode(nextWord.getEnglishInEnglish() + nextWord.getLangInLang())))
		{
			throw new WordAdditionException("Error: A duplicate item was found in the input file. ");
		}
		//This step (below) will allow me to retrieve specific unique words from the array in constant time without a loop by simply
		//calling words.get(keysAndIndices.get(item.getEnglishInEnglish() + item.getLangInLang())) or some such
		//this satisfies the requirement to use the first and third strings in the word to create uniqueness because the hashCode method does indeed
		//Use those 2 fields in the hash.
		keysAndIndices.put(hashCode(nextWord.getEnglishInEnglish() + nextWord.getLangInLang()), words.size()); 
		//System.out.println("Item added at index: " + keysAndIndices.get(hashCode(nextWord.getEnglishInEnglish() + nextWord.getLangInLang())));
		words.add(nextWord);
	}
	/**
	 * Create an ArrayList of strings from a token and cleans it up a bit before returning it to the caller
	 * @param themesToken is the string that is passed in (themes separated by spaces)
	 * @return returns an ArrayList of themes in string form
	 */
	private ArrayList<String> createThemeList(String themesToken)
	{
		ArrayList<String> themeList = new ArrayList<String>(Arrays.asList(themesToken.split(THEME_DELIMETER)));
		while(themeList.contains(""))
		{
			themeList.remove("");
		}
		if(themeList.toString().equals("[]"))
		{
			themeList.clear();
			themeList.add("unknown");
		}
		return themeList;
	}
	/**
	 * Indiscriminately adds an existent fully formed word to the array list if the word is not null. 
	 * This is to be used by internal methods only that add words to 
	 * an array list. No validation of the word occurs here.
	 * @param a_Word is the word to add to the array
	 */
	private void insertWordIntoArray(Word a_Word)
	{
		if(a_Word != null)
		{
			words.add(a_Word);
		}
	}
	/**
	 * Gets all of the themes in the collection that are stored in English.
	 * I made the assumption that this list was not required to contain only unique themes.
	 * If that assumption is incorrect, then this function will slow down considerably when 'returnAllThemes' is false.
	 * @return returns an array list of themes in English
	 */
	public ArrayList<String> getAllThemesInEnglish( )
	{
		boolean returnAllThemes = true;//change me to false to get only unique themes!!!
		//*******************return the full list of themes EVEN DUPLICATES *******************//
		if (returnAllThemes)
		{
			ArrayList<String> themesInEnglish = new ArrayList<String>();
			for(Word item : words)
			{
				if(item != null)
				{
					themesInEnglish.addAll(item.getThemesinEnglish());
				}
			}
			return themesInEnglish;
		}

		//***************** return a partial list of ONLY UNIQUE THEMES ************************//
		else
		{
			return uniqueThemesInEnglish();
		}
	}
	/**
	 * Get all of the unique themes in the file.  This is a slightly more expensive operation since it requires a loop 
	 * through all themes in the all the words
	 * @return returns an array list of unique themes in English
	 */
	public ArrayList<String> uniqueThemesInEnglish()
	{
		ArrayList<String> themesInEnglish = new ArrayList<String>();
		for(Word item : words)
		{
			if(item != null)
			{
				for(String theme: item.getThemesinEnglish())
				{
					if(!themesInEnglish.contains(theme))
					{
						themesInEnglish.add(theme);
					}
				}
			}
		}
		return themesInEnglish;
	}
	public ArrayList<String> uniqueThemesInLang()
	{
		ArrayList<String> themesInLang = new ArrayList<String>();
		for(Word item : words)
		{
			if(item != null)
			{
				for(String theme: item.getThemesinLang())
				{
					if(!themesInLang.contains(theme))
					{
						themesInLang.add(theme);
					}
				}
			}
		}
		return themesInLang;
	}
	public ArrayList<String> uniqueThemes()
	{
		ArrayList<String> themes = new ArrayList<String>();
		themes.add("NONE");
		for(Word item : words)
		{
			if(item != null)
			{
				for(String theme: item.getThemesinEnglish())
				{
					if(!themes.contains(theme))
					{
						themes.add(theme);
					}
				}
				for(String theme: item.getThemesinLang())
				{
					if(!themes.contains(theme))
					{
						themes.add(theme);
					}
				}

			}
		}
		return themes;
	}
	/**
	 * Creates a word collection object that is populated with all words that belong to a particular and specified English theme
	 * @param a_english_theme is the theme that qualifies the return list
	 * @return returns a WordCollection object of all of the qualified words
	 */
	public WordCollection getWordCollectionforEnglishTheme(String a_english_theme)
	{
		WordCollection answer = new WordCollection(true);
		for(Word item : words)
		{
			if( item != null && (item.getThemesinEnglish().contains(a_english_theme) || item.getThemesinEnglish().contains(a_english_theme.toLowerCase())))
			{
				answer.insertWordIntoArray(item);
			}
		}
		return answer;	
	}
	public WordCollection getWordCollectionforTheme(String a_theme)
	{
		WordCollection answer = new WordCollection(true);
		for(Word item : words)
		{
			if( item != null && ((item.getThemesinEnglish().contains(a_theme) || item.getThemesinEnglish().contains(a_theme.toLowerCase())) || (item.getThemesinLang().contains(a_theme) || item.getThemesinLang().contains(a_theme.toLowerCase()))))
			{
				answer.insertWordIntoArray(item);
			}
		}
		return answer;	
	}
	/**
	 * Gets a WordCollection list of a certain number of randomly selected words.  If the number is more than are in the collection, then the entire current collection
	 * is returned to the caller
	 * @param some_number the number of words to return in the collection
	 * @return returns a WordCollection of the correct number of randomly selected items
	 */
	public WordCollection getSomeRandomWords(int some_number)
	{
		WordCollection randomCollection = new WordCollection(true);
		ArrayList<Integer> randomIntegersArray = new ArrayList<Integer>(some_number);
		if(some_number >= words.size())
		{
			return this;
		}
		else
		{
			while(randomIntegersArray.size() < some_number)
			{
				int testInteger = (int) (Math.random() * words.size());
				if(randomIntegersArray.contains((Integer)testInteger)) 
				{
					continue;
				}
				else 
				{
					randomIntegersArray.add((Integer) testInteger);
				}
			}
		}
		for(int i = 0; i < randomIntegersArray.size() ; i++)
		{
			randomCollection.insertWordIntoArray(words.get(randomIntegersArray.get(i)));
		}
		return randomCollection;

	}
	/**
	 * Get a specific sized collection of unique words that have a specific them in english/Lang and have a certain length
	 * of course, sometimes there are enough in the theme of the right length and sometimes not.  If not, we try to add words
	 * from the wrong theme, but the right length, if that fails, then we add words of both the wrong theme and wrong length in an attempt to fil the size
	 * request only.  
	 * @param some_number the num of words requested
	 * @param min_length_of_word the length of words requested
	 * @param desired_theme the desired theme of words
	 * @return returns a word collection with as close a match as possible, or throws an invalid argument exception if a reasonable list cannot be assembled
	 */
	public WordCollection getSomeUniqueRandomWords(int some_number, int min_length_of_word, String desired_theme)
	{
		WordCollection randomCollection = new WordCollection(true);
		//out.println(some_number);
		if (words.size() < some_number)
		{

			throw new IllegalArgumentException("Not enough words to complete the puzzle.");
		}
		else if( (desired_theme != null) && !(desired_theme.equals("")) && !(desired_theme.equals("NONE")) && !(desired_theme.equals("unknown")) )
		//A Theme has been selected.
		{
			WordCollection themeCollection = getWordCollectionforTheme(desired_theme);
			//if all the words with the theme are not enough, add some random ones with the right length but wrong theme
			if(themeCollection.size() <= some_number)
			//we don't have enough or enough unique words with the right theme
			{
				View.showMessage("Adding Random Words to Satisfy Request.", Color.GREEN, Color.GREEN);
				for(int i = 0; i < words.size() ; i++)
				{					
					int randomIndex = ((int)(Math.random() * words.size()));
					Word aWord = words.get(randomIndex);
					if(!(themeCollection.words.contains(aWord)) && !(aWord.getEnglishInEnglish().length() < min_length_of_word)) 
					{
						themeCollection.insertWordIntoArray(aWord);
						if(themeCollection.words.size() == some_number)
						{
							return themeCollection;
						}
					}
				}
				//There still aren't enough, so add some with the wrong theme and wrong length
				for(int i = 0; i < words.size() ; i++)
				{					
					int randomIndex = ((int)(Math.random() * words.size()));
					Word aWord = words.get(randomIndex);
					if(!(themeCollection.words.contains(aWord))) 
					{
						themeCollection.insertWordIntoArray(aWord);
						if(themeCollection.words.size() == some_number)
						{
							return themeCollection;
						}
					}
				}
				//return whatever we have at this point.
				return themeCollection;
			}
			if(themeCollection.size() > some_number)
			//build a collection of the right length words with the right theme
			{
				WordCollection smallerThemeCollection = new WordCollection(true);
				for(int i = 0; i < themeCollection.size() ; i++)
				{
					int randomIndex = ((int)(Math.random() * themeCollection.size()));
					Word aWord = themeCollection.words.get(randomIndex);
					if(!(smallerThemeCollection.words.contains(aWord)) && !(aWord.getEnglishInEnglish().length() < min_length_of_word)) 
					{
						smallerThemeCollection.insertWordIntoArray(aWord);
						if(smallerThemeCollection.words.size() == some_number)
						{
							return smallerThemeCollection;
						}
					}
				}
				//there aren't enough words of the theme AND right length, so add some of the right theme and wrong length.
				View.showMessage("Adding Random Words to Satisfy Request.", Color.GREEN, Color.GREEN);
				if (smallerThemeCollection.size() < some_number)
				{
					for(int j = 0 ; (j < themeCollection.size()); j++)
					{
						int randomIndex2 = ((int)Math.random()*themeCollection.size());
						Word aWord2 = themeCollection.words.get(randomIndex2);
						if(!(smallerThemeCollection.words.contains(aWord2)))
						{
							smallerThemeCollection.insertWordIntoArray(aWord2);
							if(smallerThemeCollection.size() == some_number)
							{
								return smallerThemeCollection;
							}
						}
					}
				}
				//There still aren't enough words in the list, so add some with the wrong theme and wrong length.
				for(int i = 0; i < words.size() ; i++)
				{					
					int randomIndex = ((int)(Math.random() * words.size()));
					Word aWord = words.get(randomIndex);
					if(!(smallerThemeCollection.words.contains(aWord))) 
					{
						smallerThemeCollection.insertWordIntoArray(aWord);
						if(smallerThemeCollection.words.size() == some_number)
						{
							return smallerThemeCollection;
						}
					}
				}
				//return whatever we have at this point
				return smallerThemeCollection;
			}
		}
		else
		//we don't care about the theme (cause it's null or whatever) so just build a collection of words with the right length.
		{
			for(int i = 0; i < words.size() ; i++)
			{
				int randomIndex = ((int)(Math.random() * words.size()));
				Word aWord = words.get(randomIndex);
				if(!(randomCollection.words.contains(aWord)) && !(aWord.getEnglishInEnglish().length() < min_length_of_word)) {
					randomCollection.words.add(aWord);
					if(randomCollection.words.size() == some_number)
					{
						return randomCollection;
					}
				}
			}
			View.showMessage("Adding Random Words to Satisfy Request.", Color.GREEN, Color.GREEN);
			//But if there aren't enough with the right length, add some with other lengths
			for(int i = 0; i < words.size() ; i++)
			{
				int randomIndex = ((int)(Math.random() * words.size()));
				Word aWord = words.get(randomIndex);
				if(!(randomCollection.words.contains(aWord))) {
					randomCollection.words.add(aWord);
					if(randomCollection.words.size() == some_number)
					{
						return randomCollection;
					}
				}
			}
		}
		//We tried everything we could think of, but alas, we end up here and have to throw an exception.
		throw new IllegalArgumentException("Unable to create Word Collection as requested. Quantity: " + some_number + " Theme: " + desired_theme + " Length: " + min_length_of_word);
	}
	/**
	 * A method to get an array list of all English words in English language
	 * Because uniqueness is guaranteed at insertion, this should be a full list of uniquely named words
	 * @return returns an array list of all English in English words
	 */
	public ArrayList<String> getEnglishInEnglishCollection( )
	{
		ArrayList<String> englishInEnglishWords = new ArrayList<String>();
		for(Word item : words)
		{
			if(item != null)//in case an item is null, which it should not be
			{
				englishInEnglishWords.add(item.getEnglishInEnglish());
			}
		}
		return englishInEnglishWords;
	}
	/**
	 * Returns a wonderfully formatted string of the collection's first four fields
	 * although, in UTF-8 on the console, this formatting can be slightly askew depending on the UTF chars used.
	 * @return returns of course, a string representing the array storage of objects
	 */
	public String toString( )
	{
		String[] args = {"E_inE","E_in_L","L_in_L","L_in_E" };
		String divider = "------------------------------------------------------------------------------------------------------------------\n";
		String answer = String.format( "%-25s %-25s %-25s %-25s %n", args[0] , args[1] , args[2], args[3]) + divider;
		for(Word item : words)
		{
			if(item != null)
			{
				answer += String.format("%-25s %-25s %-25s %-25s %n", item.getEnglishInEnglish() ,item.getEnglishInLang(), item.getLangInLang(), item.getLangInEnglish());
			}
		}
		return answer + divider;
	}
	/**
	 * Counts the number of lines in the file that is read in.  !!!!!  Unused in the current implementation.  !!!!!!!
	 * @param filename is the name of the file to count the lines in
	 * @return is an integer representative of the computed number of lines in the file
	 * @throws IOException thrown if the file that is specified fails to load
	 */
	@SuppressWarnings("unused")
	private int countLinesInFile(String filename) throws IOException 
	{
		LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
		String lineRead = "";
		if ((lineRead = reader.readLine()) == null) 
		{
			throw new IOException();
		}
		int count = 0;	    
		while ((lineRead = reader.readLine()) != null) 
		{
			continue;//keep on counting
		}
		count = reader.getLineNumber(); 
		reader.close();
		return count;
	}
	/**
	 * Reads lines from a text file one by one and sends them to the addWord method. Catches a WordAdditionException if one is thrown,
	 * and exits the program as per instructions.  It is possible however, to not exit and skip to the next line. This is a one line code change
	 * that involves removing the exit statement.
	 * @param filename is a string that represents the path of the file to read
	 * @throws IOException is thrown if the file fails to load
	 */
	private void readLinesInFile(String filename) throws IOException 
	{
		LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
		String lineRead = "";
		int lineNumber = 0;
		while ((lineRead = reader.readLine()) != null) 
		{
			lineNumber++;
			try 
			{
				addWord(lineRead);
			} 
			catch (WordAdditionException e) 
			{
				System.out.println(e.getMessage() + "Exiting with error code 0 at test data line # " + lineNumber);
				System.exit(0);
			}
		}
		reader.close();
	}
	/**
	 * A method to hash a string using mutually prime numbers (who's GCF is 1) in an attempt to create very unique representations of each word
	 * @param key is the string to hash
	 * @return is an integer representation of a hashed key
	 */
	private static int hashCode(String key) 
	{
		int hashVal = 0;
		for (int i = 0; i< key.length(); i++) 
		{
			hashVal = (127 * hashVal + key.charAt(i)) % 16908799;
		}
		return hashVal;
	}
	/**
	 * Get the size of this word collection
	 * @return the integer that represents the size of the collection
	 */
	public int size()
	{
		return words.size();
	}
	/**
	 * Get the array list of words in this collection
	 * @return word : an array list of Word Objects
	 */
	public String getWordList()
	{
		String answer = "";
		for(Word item : words)
		{
			answer+= item.getEnglishInEnglish() +" : " + item.getThemesinEnglish()+ "::" +item.getThemesinLang() + "\n";
		}
		return answer;
	}
}
