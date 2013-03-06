import java.util.ArrayList;


public class JasthiTest
{

	public static void main(String[] args)
	{
	// get the entire word collection
	WordCollection x = new WordCollection("C:\\ics340\\words.txt");//Insert your file path here
	System.out.println("Test case 1:\n");
	WordCollection y1 = x.getSomeUniqueRandomWords(10,4,null);
	WordSearcher z1 = new WordSearcher(y1);//create a default constructor with chain
	System.out.println(z1);
	
	
	
	System.out.println("Test case 2:\n");
	WordCollection y2 = x.getSomeUniqueRandomWords(15,5,"animals");
	WordSearcher z2 = new WordSearcher(y2,20,24,false);
	System.out.println(z2);
	
	
	
	System.out.println("Test case 3:\n");
	WordCollection y3 = x.getSomeUniqueRandomWords(8,4,"flowers");
	WordSearcher z3 = new WordSearcher(y3,20,20,true);
	System.out.println(z3);
	
	
	
	System.out.println("Test case 3.1 (null solution):\n");
	z3.setSolutionArray(null);
	ArrayList<String> word_list = y3.getEnglishInEnglishCollection();
	z3.setSolutionArray(z3.solvePuzzle(z3.getPuzzle(),word_list));
	System.out.println(z3);
	}

}
