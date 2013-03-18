import java.io.*;
import java.util.ArrayList;
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
 int minimum_word_length = 4;
 int grid_width = 10;
 int grid_length = 10;
 int number_of_words = 10;
 int puzzleCounter = 0;
 int themeCounter =  0;
 int no_theme_collection_size = 35;
 int number_of_words_to_fill = 0;
 boolean hasEnough = true;
 boolean finished = true;

    public WordSearcherCollection (){
            this(null, null);
    }

    public WordSearcherCollection (String filepath){
          this(filepath, null);
    }

    public WordSearcherCollection (String filepath, String theme){
        if (filepath != null){
        inputFilePath = filepath;
            String output = "";

       //Tokenizer to get file path
        ArrayList<String> substrings = new ArrayList<String>();
       StringTokenizer stringTokenizer = new StringTokenizer(filepath, "\\");
        while (stringTokenizer.hasMoreTokens()) {

            substrings.add(stringTokenizer.nextToken());

        }
        //Set filepath string for output.txt
        for (int index = 0; index < substrings.size()-1; index++){
            output = output + substrings.get(index) +"\\";
        }
             output = output + "output.txt";
            outputFilePath = output;
        }
        readPlaceHolderFile();
        //Create WordCollection
        WordCollection themedWords = null;
        WordCollection uniqueWords = null;
        WordCollection wordCollection = new WordCollection(inputFilePath);
        if (theme != null){
        themedWords = wordCollection.getWordCollectionforEnglishTheme(theme);
        if(themedWords.getWordCollection().size() == 0){
           themedWords = wordCollection.getWordCollectionforTheme(theme);
        }}else{
            themedWords = wordCollection.getSomeRandomWords(no_theme_collection_size);
        }


        do{

            uniqueWords = themedWords.getSomeUniqueRandomWords(number_of_words, minimum_word_length, theme);

            //themeCounter ++;

            //Create wordSearcher with Wordcollection, 10, 10 , false, 10 words
        WordSearcher wordSearcher = new WordSearcher(uniqueWords, grid_width , grid_length , true);
            //Checks for bad word and redo on puzzle build
            if (!wordSearcher.getBadWord()){
            try {
                writeFile(wordSearcher, theme);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            ArrayList<Word> compareWords = uniqueWords.getWordCollection();
            for (Word compare : compareWords){
                themedWords.removeWord(compare);
                wordCollection.removeWord(compare);
            }
            }

            if(themedWords.getWordCollection().size() < 10){
                hasEnough = false;
            }
            //Do until the number is under a full puzzle
        } while (hasEnough);

        ArrayList<Word> compareWords = themedWords.getWordCollection();
        for (Word compare : compareWords){
            wordCollection.removeWord(compare);
        }
        // CHeck the remainder
        number_of_words_to_fill = number_of_words - themedWords.size();
        //themeCounter++;
        do{
        WordCollection fillInCollection = wordCollection.getSomeUniqueRandomWords(number_of_words_to_fill, minimum_word_length, null);

        for (Word word : themedWords.getWordCollection()){
             fillInCollection.addWordDirect(word);
        }

        WordSearcher wordSearcher =  new WordSearcher(fillInCollection, grid_width, grid_length, true);
        //Check for badword on remainder puzzle
        if(wordSearcher.getBadWord()){
            //fillInCollection = null;
            //wordSearcher = null;
            finished = false;
        }   else{
            try {
                writeFile(wordSearcher, theme);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
           finished = true;
        }
        }while (!finished);
        writePlaceHolderFile();



          //


    }

    public void writeFile(WordSearcher puzzle, String theme) throws IOException {
        puzzleCounter++;

        File file = new File(outputFilePath);

        FileWriter outFile = new FileWriter(file, true);

        PrintWriter out = new PrintWriter(outFile);
        if (theme != null){
            themeCounter++;
            if (!hasEnough){
                out.println("[ " + puzzleCounter +" ]  "+ theme + ": puzzle " + themeCounter + " * ");
            } else{
                out.println("[ " + puzzleCounter +" ]  "+ theme + ": puzzle " + themeCounter + " ");
            }
        }else{
            if (!hasEnough){
                out.println("[ " + puzzleCounter +" ]  No Theme: puzzle " + themeCounter + " * ");
            }else{
                out.println("[ " + puzzleCounter +" ]  No Theme: puzzle " + themeCounter + " ");
        }
        }
        out.println(puzzle);
        out.close();
        /*


        ObjectOutputStream out = null;

        try{
            File file = new File("puzzle.ser");
            boolean isThere = file.exists();
            if (!isThere){
            out = new ObjectOutputStream(new FileOutputStream(file,false));
            } else{
            out = new ObjectOutputStream(new FileOutputStream(file,true));
            }
            out.writeObject(puzzle);




            out.flush();
            out.close();
            System.out.println("Object written to file");
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error with specified file") ;
            ex.printStackTrace();
        }
        catch (IOException ex) {
            System.out.println("Error with I/O processes") ;
            ex.printStackTrace();
        }
        */
    }
    public void readPlaceHolderFile(){
        //Reads placeholder file to find place in the output document
     File output = new File(outputFilePath);
     File file = new File("placeholder.txt");
     boolean outputThere = output.exists();
     boolean phThere = file.exists();
     BufferedReader reader = null;

        if (outputThere){
            if (phThere){
                try {
                    reader = new BufferedReader(new FileReader(file));
                    String text = null;

                    while((text = reader.readLine()) != null){
                        puzzleCounter = Integer.parseInt(text);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } finally {
                    if (reader !=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }
        }

    }
    public void writePlaceHolderFile (){

        //Writing the placeholder file in the project location
        File file = new File("placeholder.txt");
        boolean isThere = file.exists();

        if (isThere){
            file.delete();
        }

        FileWriter outFile = null;
        try {
            outFile = new FileWriter(file,false);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        PrintWriter out = new PrintWriter(outFile);

        out.print(puzzleCounter);
        out.close();
    }


}
