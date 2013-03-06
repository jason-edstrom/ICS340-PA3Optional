import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 3/4/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordSearcherCollection {
 String inputFilePath = "C:\\ics340\\words.txt";
 String outputFilePath = "C:\\ics340\\output.txt";


    public WordSearcherCollection (){
            WordCollection defaultCollection = new WordCollection(inputFilePath);
            WordSearcher wordSearcher = new WordSearcher(defaultCollection, 10, 10, false);
    }

    public WordSearcherCollection (String filename, String theme){
       //Tokenizer to get file path
        StringTokenizer
        //Set filepath string for output.txt

        //Create WordCollection

        WordCollection wordCollection = new WordCollection(inputFilePath);
        WordCollection uniqueWords = wordCollection.getSomeUniqueRandomWords(10, 4, theme);


        //Create wordSearcher with Wordcollection, 10, 10 , false, 10 words
          WordSearcher wordSearcher = new WordSearcher(uniqueWords, 10 , 10 , false);


    }
}
