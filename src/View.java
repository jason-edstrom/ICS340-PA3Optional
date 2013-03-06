import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
/**
 * 
 * @author sstruhar
 *Layout the UI and provide for the event handling
 */

public class View extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6460554677305119782L;
	private JPanel contentPane;
	private JPanel puzzlePanel;
	private WordSearcher board;
	private JPanel solutionPanel;
	private JTable puzzleTable;
	private JTable solutionTable;
	private String[] headers;
	private TableModel puzzleModel;
	private TableModel solutionModel;
	private JScrollPane solutionScroll = new JScrollPane();
	private JScrollPane puzzleScroll = new JScrollPane();
	private JButton btnChooseWordInput;
	private WordCollection wordList;
	private String wordCollectionFilePath;
	private JTextField width_Field;
	private JTextField height_Field;
	private JCheckBox chckbxAllowCrossings;
	private JLabel filePathLabel;
	private JButton btnSolveme;
	private JComboBox themeComboBox;
	private JPanel panel;
	private JScrollPane wordlistScrollPane;
	private JTextArea wordlistTextArea;
	private final int DEFAULT_XY = 10;
	private final int DEFAULT_NUM_WORDS = 5;
	private JTextField numWordsTextField;
	private final int DEFAULT_MIN_WORD_LEN = 5;
	private JTextField minWordLengthTextField;
	private JLabel lblMinWordLength;
	private JButton btnGeneratepuzzle;
	private JLabel lblWordsInTheme;



	/**
	 * Create the frame.
	 */
	public View(WordCollection words, int width, int height, boolean allowCrosses)
	{
		
		wordList = words;
		this.setTitle("WordSearch Puzzle");
		try {
			board = new WordSearcher(wordList.getSomeUniqueRandomWords(DEFAULT_NUM_WORDS, DEFAULT_MIN_WORD_LEN, "NONE"), width, height , allowCrosses);
		}catch (OutOfMemoryError e)
		{
			throw e;
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1501, 851);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		puzzlePanel = new JPanel();
		puzzlePanel.setBounds(5, 5, 711, 530);
		puzzlePanel.setBorder(new TitledBorder(null, "GameBoard", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(puzzlePanel);

		solutionPanel = new JPanel();
		solutionPanel.setBounds(728, 5, 701, 530);
		solutionPanel.setBorder(new TitledBorder(null, "Solution Board", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(solutionPanel);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Word Source", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(15, 547, 302, 228);
		contentPane.add(panel);
		panel.setLayout(null);

		btnChooseWordInput = new JButton("Choose Word Input");
		btnChooseWordInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser(new File("user.dir"));
				int returnVal = fc.showOpenDialog(puzzlePanel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					generateNewWordCollection(file.getAbsolutePath());
					btnGeneratepuzzle.setEnabled(true);
					btnSolveme.setEnabled(false);
					generateAPuzzle();
					clearSolution();

				} else {
					showMessage("File Selection Cancelled...", (Color.RED), (Color.RED));
				}
			}
		});
		btnChooseWordInput.setBounds(6, 22, 164, 29);
		panel.add(btnChooseWordInput);

		filePathLabel = new JLabel("");
		filePathLabel.setBounds(16, 71, 268, 16);
		panel.add(filePathLabel);

		wordlistScrollPane = new JScrollPane();
		wordlistScrollPane.setBounds(728, 557, 701, 217);
		contentPane.add(wordlistScrollPane);

		wordlistTextArea = new JTextArea();
		wordlistScrollPane.setViewportView(wordlistTextArea);

		JPanel puzzleOptionsPanel = new JPanel();
		puzzleOptionsPanel.setBorder(new TitledBorder(null, "Puzzle Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		puzzleOptionsPanel.setBounds(329, 547, 387, 228);
		contentPane.add(puzzleOptionsPanel);
		puzzleOptionsPanel.setLayout(null);

		btnGeneratepuzzle = new JButton("GeneratePuzzle");
		btnGeneratepuzzle.setBounds(6, 20, 139, 29);
		puzzleOptionsPanel.add(btnGeneratepuzzle);
		btnGeneratepuzzle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateAPuzzle();
				
			}
		});


		btnSolveme = new JButton("Solve");
		btnSolveme.setBounds(167, 20, 95, 29);
		puzzleOptionsPanel.add(btnSolveme);

		width_Field = new JTextField();
		width_Field.setBounds(127, 61, 88, 28);
		puzzleOptionsPanel.add(width_Field);
		width_Field.setColumns(10);

		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(85, 107, 42, 16);
		puzzleOptionsPanel.add(lblWidth);

		height_Field = new JTextField();
		height_Field.setBounds(127, 101, 88, 28);
		puzzleOptionsPanel.add(height_Field);
		height_Field.setColumns(10);

		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(78, 67, 49, 16);
		puzzleOptionsPanel.add(lblHeight);

		chckbxAllowCrossings = new JCheckBox("Allow Crossings");
		chckbxAllowCrossings.setSelected(true);
		chckbxAllowCrossings.setBounds(227, 120, 139, 23);
		puzzleOptionsPanel.add(chckbxAllowCrossings);

		themeComboBox = new JComboBox();
		themeComboBox.setBounds(227, 61, 139, 27);
		updateComboBox(wordList.uniqueThemes());
		puzzleOptionsPanel.add(themeComboBox);

		numWordsTextField = new JTextField();
		numWordsTextField.setBounds(127, 139, 88, 28);
		puzzleOptionsPanel.add(numWordsTextField);
		numWordsTextField.setColumns(10);

		JLabel lblNumWords = new JLabel("Num Words");
		lblNumWords.setBounds(47, 145, 80, 16);
		puzzleOptionsPanel.add(lblNumWords);

		minWordLengthTextField = new JTextField();
		minWordLengthTextField.setBounds(127, 180, 88, 28);
		puzzleOptionsPanel.add(minWordLengthTextField);
		minWordLengthTextField.setColumns(10);

		lblMinWordLength = new JLabel("Min Word Length");
		lblMinWordLength.setBounds(16, 186, 111, 16);
		puzzleOptionsPanel.add(lblMinWordLength);
		
		lblWordsInTheme = new JLabel("");
		lblWordsInTheme.setBounds(237, 155, 129, 16);
		puzzleOptionsPanel.add(lblWordsInTheme);
		btnSolveme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solve(board.getWordList(), board.getPuzzle());
			}
		});
		headers = board.getHeaders();
		setThePuzzle();
	}
	/**
	 * Generate a new word collection form a file
	 * @param absolutePath the path to the file
	 */
	protected void generateNewWordCollection(String absolutePath)
	{
		setWordCollectionFilePath(absolutePath);
		filePathLabel.setText(wordCollectionFilePath);
		wordList = new WordCollection(absolutePath);
		updateComboBox(wordList.uniqueThemes());
	}
	/**
	 * set the word collection file path for future use
	 * @param a_wordCollectionFilePath path to the current word collection file
	 */
	public void setWordCollectionFilePath(String a_wordCollectionFilePath)
	{
		wordCollectionFilePath = a_wordCollectionFilePath;
	}
	/**
	 * Generate a new puzzle so it can be displayed in the puzzle grid
	 */
	private void generateAPuzzle()
	{
		try {
			WordCollection sender = wordList.getSomeUniqueRandomWords(getNumWords(), getMinWordLength(), getComboBox());
			board = new WordSearcher(sender, getPuzzleHeight(), getPuzzleWidth(), chckbxAllowCrossings.isSelected());
			btnSolveme.setEnabled(true);
			updateThePuzzle();
			updateWordsInTheme(sender.size());
			}catch(IllegalArgumentException e)
		{
			showMessage("Not enough words in file. Try another file.", Color.RED, Color.RED);
			clearPuzzle();
		}
		
	}
	/**
	 * get the height requested by the user
	 * @return an integer of the height requested, or if the input is bad, the default height
	 */
	private int getPuzzleWidth()
	{
		try
		{
			int returner = Integer.parseInt(width_Field.getText());
			if(returner == 0 || returner > 100)
			{
				return DEFAULT_XY;
			}
			return returner;
		}catch(NumberFormatException e)
		{
			return DEFAULT_XY;
		}
	}
	/**
	 * get the width requested by the user.
	 * @return an integer of the width requested or the default width in the case of bad input
	 */
	private int getPuzzleHeight()
	{
		try
		{
			int returner = Integer.parseInt(height_Field.getText());

			if(returner == 0 || returner > 100)
			{
				return DEFAULT_XY;
			}
			return returner;
		}catch(NumberFormatException e)
		{
			return DEFAULT_XY;
		}
	}
	/**
	 * Set the puzzle into the grid
	 */
	private void setThePuzzle()
	{
		puzzlePanel.setLayout(new BorderLayout(0, 0));
		puzzleModel = new DefaultTableModel(board.getPuzzle(), headers);
		puzzlePanel.add(puzzleScroll, BorderLayout.CENTER);
		puzzleTable = new JTable(puzzleModel);
		puzzleTable.setBounds(6, 23, 482, 668);
		puzzleScroll.setViewportView(puzzleTable);
		puzzleTable.setTableHeader(null);
		solutionPanel.setLayout(new BorderLayout(0, 0));
		solutionTable = new JTable();
		solutionTable.setBounds(6, 23, 482, 668);
		solutionTable.setTableHeader(null);
		solutionScroll.setViewportView(solutionTable);
		solutionPanel.add(solutionScroll, BorderLayout.CENTER);
		setWordListText();
	}
	/**
	 * update the grid with a new puzzle
	 */
	private void updateThePuzzle()
	{
		puzzleModel = new DefaultTableModel(board.getPuzzle(), board.getHeaders());
		puzzleTable = new JTable(puzzleModel);
		puzzleTable.setEnabled(false);
		puzzleTable.setTableHeader(null);
		puzzleScroll.setViewportView(puzzleTable);
		setWordListText();
		clearSolution();

	}
	/**
	 * Update the num words in theme selection text field
	 * @param numWords an int to set in the UI
	 */
	private void updateWordsInTheme(int numWords)
	{
		lblWordsInTheme.setText(numWords + " Words in selection");
	}
	/**
	 * tell the puzzle to solve itself
	 * @param list is a dictionary of words to find
	 * @param grid is the grid to look for the words in
	 */
	private void solve(ArrayList<String> list, String[][] grid)
	{
		if(grid!=null) {
			board.solvePuzzle(grid, list);
			solutionModel = new DefaultTableModel(board.getSolution(), board.getHeaders());
			solutionTable = new JTable(solutionModel);
			solutionTable.setTableHeader(null);	
			solutionScroll.setViewportView(solutionTable);
		}

	}
	/**
	 * clear the solution from the view
	 */
	private void clearSolution()
	{
		solutionModel = null;
		solutionTable = new JTable(solutionModel);
		solutionTable.setTableHeader(null);	
		solutionScroll.setViewportView(solutionTable);

	}
	/**
	 * clear the puzzle from the view
	 */
	public void clearPuzzle()
	{
		puzzleModel = null;
		puzzleTable = new JTable(puzzleModel);
		puzzleTable.setTableHeader(null);	
		puzzleScroll.setViewportView(puzzleTable);
	}
	/**
	 * Show a message to the user about some event
	 * @param message the message to show
	 * @param messageColor the color of the inner area
	 * @param borderColor the color of the border
	 */
	public static void showMessage(String message, Color messageColor, Color borderColor)
	{
		Growler myGrowler = new Growler(message , messageColor , borderColor);
		myGrowler.setLocationRelativeTo(null);
		myGrowler.setVisible(true);
	}
	/**
	 * Update the theme combobox with the list of unique themes in the input file
	 * @param themes array list of themes to put in the combo box
	 */
	private void updateComboBox(ArrayList<String> themes)
	{
		themeComboBox.setModel(new DefaultComboBoxModel(themes.toArray()));
	}
	/**
	 * Get the value from the combo box
	 * @return a string of the selected item in the combo box
	 */
	public String getComboBox()
	{
		return themeComboBox.getSelectedItem().toString();
	}
	/**
	 * Display the list of words that are hidden in the current puzzle
	 */
	private void setWordListText()
	{
		wordlistTextArea.setText(board.getMiniWordCollection().getWordList());
	}
	/**
	 * Get the number of words the user would like in the puzzle
	 * @return an integer of the number of words requested or the default number of words
	 */
	private int getNumWords()
	{

		try {
			int answer = Integer.parseInt(numWordsTextField.getText());
			if (answer == 0) {
				return DEFAULT_NUM_WORDS;
			}
			return answer;
		}catch(NumberFormatException e)
		{
			return DEFAULT_NUM_WORDS;
		}
	}
	/**
	 * Get the minimum word length that the user would like in the puzzle
	 * @return an integer of the min length of words requested by the user or the default length.
	 */
	private int getMinWordLength()
	{

		try {
			int answer = Integer.parseInt(minWordLengthTextField.getText());
			if (answer == 0) {
				return DEFAULT_MIN_WORD_LEN;
			}
			return answer;
		}catch(NumberFormatException e)
		{
			return DEFAULT_MIN_WORD_LEN;
		}
	}
}
