import java.awt.EventQueue;

public class GuiTest
{


	public static void main (String[] args) throws IllegalArgumentException{
		final WordCollection wordList = new WordCollection("C:\\ics340\\words.txt"); //Will include a test file in submission
		
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					View frame = new View(wordList, 10, 10, true);
					frame.setVisible(true);
				} catch (OutOfMemoryError e)
				{
					System.out.println(e.getMessage() + ": Out of memory. Exiting with code(0)");
					System.exit(0);
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
