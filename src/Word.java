

/*======================================================================
 Author     : JasthiGUI.java Program
 Class Name : Word
 Date       : 1/14/2013      
 Course     : ICS141 Programming with Objects (Siva Jasthi)
 Purpose    : Starter Assignment for ICS340 - Spring 2013
======================================================================*/

import java.util.ArrayList;

/**
 * This class encapsulates a word consisting of different attributes
 */

public class Word
{	

	//English Word written in ENGLISH
	private String englishInEnglish;	

	//English Word written in <LANG> Language
	private String englishInLang;	

	//<LANG> Word written in <LANG> Language
	private String langInLang;	

	//<LANG> Word written in ENGLISH Language
	private String langInEnglish;	

	//Some hint about the Word written in ENGLISH Language
	private String hintInEnglish;	

	//Some hint about the word written in <LANG> language
	private String hintInLang;	

	//URI for the sound file for ENGLISH sound
	private String soundURIOfEnglish;	

	//URI for the sound file for <LANG> sound
	private String soundURIOfLang;	

	//URI 1 for the image file for the Word
	private String imageOneURI;	

	//URI 2 for the image file for the Word
	private String imageTwoURI;	

	//Description of the Word in ENGLISH
	private String descriptionInEnglish;	

	//Description of the Word in <LANG>
	private String descriptionInLang;	

	//Themes (separated by spaces) to which this word belongs to written in ENGLISH
	private ArrayList<String> themesinEnglish;	

	//Themes (separated by spaces) to which this words belongs to written in <LANG>
	private ArrayList<String> themesinLang;	

	//Reserved Field 1 for future use
	private String reservedOne;	

	//Reserved Field 2 for future use
	private String reservedTwo;

	/**
	 * Default Constructor For Word Class
	 */
	 public Word()
	 {
 
	 };

	/**
	* Overloded Constructor For Word Class
	*/
	 public Word(String a_english_in_english, String a_english_in_lang, String a_lang_in_lang, String a_lang_in_english, String a_hint_in_english, String a_hint_in_lang, String a_sound_u_r_i_of_english, String a_sound_u_r_i_of_lang, String a_image_one_u_r_i, String a_image_two_u_r_i, String a_description_in_english, String a_description_in_lang, ArrayList<String> a_themesin_english, ArrayList<String> a_themesin_lang, String a_reserved_one, String a_reserved_two)
	 { 
		englishInEnglish = a_english_in_english;
		englishInLang = a_english_in_lang;
		langInLang = a_lang_in_lang;
		langInEnglish = a_lang_in_english;
		hintInEnglish = a_hint_in_english;
		hintInLang = a_hint_in_lang;
		soundURIOfEnglish = a_sound_u_r_i_of_english;
		soundURIOfLang = a_sound_u_r_i_of_lang;
		imageOneURI = a_image_one_u_r_i;
		imageTwoURI = a_image_two_u_r_i;
		descriptionInEnglish = a_description_in_english;
		descriptionInLang = a_description_in_lang;
		themesinEnglish = a_themesin_english;
		themesinLang = a_themesin_lang;
		reservedOne = a_reserved_one;
		reservedTwo = a_reserved_two;
	 }

	/**
	 * Set method for the variable englishInEnglish
	 */
	public void setEnglishInEnglish(String a_english_in_english)
	{
		englishInEnglish = a_english_in_english;
	}

	/**
	 * Set method for the variable englishInLang
	 */
	public void setEnglishInLang(String a_english_in_lang)
	{
		englishInLang = a_english_in_lang;
	}

	/**
	 * Set method for the variable langInLang
	 */
	public void setLangInLang(String a_lang_in_lang)
	{
		langInLang = a_lang_in_lang;
	}

	/**
	 * Set method for the variable langInEnglish
	 */
	public void setLangInEnglish(String a_lang_in_english)
	{
		langInEnglish = a_lang_in_english;
	}

	/**
	 * Set method for the variable hintInEnglish
	 */
	public void setHintInEnglish(String a_hint_in_english)
	{
		hintInEnglish = a_hint_in_english;
	}

	/**
	 * Set method for the variable hintInLang
	 */
	public void setHintInLang(String a_hint_in_lang)
	{
		hintInLang = a_hint_in_lang;
	}

	/**
	 * Set method for the variable soundURIOfEnglish
	 */
	public void setSoundURIOfEnglish(String a_sound_u_r_i_of_english)
	{
		soundURIOfEnglish = a_sound_u_r_i_of_english;
	}

	/**
	 * Set method for the variable soundURIOfLang
	 */
	public void setSoundURIOfLang(String a_sound_u_r_i_of_lang)
	{
		soundURIOfLang = a_sound_u_r_i_of_lang;
	}

	/**
	 * Set method for the variable imageOneURI
	 */
	public void setImageOneURI(String a_image_one_u_r_i)
	{
		imageOneURI = a_image_one_u_r_i;
	}

	/**
	 * Set method for the variable imageTwoURI
	 */
	public void setImageTwoURI(String a_image_two_u_r_i)
	{
		imageTwoURI = a_image_two_u_r_i;
	}

	/**
	 * Set method for the variable descriptionInEnglish
	 */
	public void setDescriptionInEnglish(String a_description_in_english)
	{
		descriptionInEnglish = a_description_in_english;
	}

	/**
	 * Set method for the variable descriptionInLang
	 */
	public void setDescriptionInLang(String a_description_in_lang)
	{
		descriptionInLang = a_description_in_lang;
	}

	/**
	 * Set method for the variable themesinEnglish
	 */
	public void setThemesinEnglish(ArrayList<String> a_themesin_english)
	{
		themesinEnglish = a_themesin_english;
	}

	/**
	 * Set method for the variable themesinLang
	 */
	public void setThemesinLang(ArrayList<String> a_themesin_lang)
	{
		themesinLang = a_themesin_lang;
	}

	/**
	 * Set method for the variable reservedOne
	 */
	public void setReservedOne(String a_reserved_one)
	{
		reservedOne = a_reserved_one;
	}

	/**
	 * Set method for the variable reservedTwo
	 */
	public void setReservedTwo(String a_reserved_two)
	{
		reservedTwo = a_reserved_two;
	}

	/**
	 * Get method for the variable englishInEnglish
	 */
	public String getEnglishInEnglish( )
	{
		return englishInEnglish;
	}

	/**
	 * Get method for the variable englishInLang
	 */
	public String getEnglishInLang( )
	{
		return englishInLang;
	}

	/**
	 * Get method for the variable langInLang
	 */
	public String getLangInLang( )
	{
		return langInLang;
	}

	/**
	 * Get method for the variable langInEnglish
	 */
	public String getLangInEnglish( )
	{
		return langInEnglish;
	}

	/**
	 * Get method for the variable hintInEnglish
	 */
	public String getHintInEnglish( )
	{
		return hintInEnglish;
	}

	/**
	 * Get method for the variable hintInLang
	 */
	public String getHintInLang( )
	{
		return hintInLang;
	}

	/**
	 * Get method for the variable soundURIOfEnglish
	 */
	public String getSoundURIOfEnglish( )
	{
		return soundURIOfEnglish;
	}

	/**
	 * Get method for the variable soundURIOfLang
	 */
	public String getSoundURIOfLang( )
	{
		return soundURIOfLang;
	}

	/**
	 * Get method for the variable imageOneURI
	 */
	public String getImageOneURI( )
	{
		return imageOneURI;
	}

	/**
	 * Get method for the variable imageTwoURI
	 */
	public String getImageTwoURI( )
	{
		return imageTwoURI;
	}

	/**
	 * Get method for the variable descriptionInEnglish
	 */
	public String getDescriptionInEnglish( )
	{
		return descriptionInEnglish;
	}

	/**
	 * Get method for the variable descriptionInLang
	 */
	public String getDescriptionInLang( )
	{
		return descriptionInLang;
	}

	/**
	 * Get method for the variable themesinEnglish
	 */
	public ArrayList<String> getThemesinEnglish( )
	{
		return themesinEnglish;
	}

	/**
	 * Get method for the variable themesinLang
	 */
	public ArrayList<String> getThemesinLang( )
	{
		return themesinLang;
	}

	/**
	 * Get method for the variable reservedOne
	 */
	public String getReservedOne( )
	{
		return reservedOne;
	}

	/**
	 * Get method for the variable reservedTwo
	 */
	public String getReservedTwo( )
	{
		return reservedTwo;
	}

	/** 
	 * Returns the String representation of Word object 
	 */
	 public String toString()
	{
		 String temp = 
			"\nenglishInEnglish = " + englishInEnglish +
			"\nenglishInLang = " + englishInLang +
			"\nlangInLang = " + langInLang +
			"\nlangInEnglish = " + langInEnglish +
			"\nhintInEnglish = " + hintInEnglish +
			"\nhintInLang = " + hintInLang +
			"\nsoundURIOfEnglish = " + soundURIOfEnglish +
			"\nsoundURIOfLang = " + soundURIOfLang +
			"\nimageOneURI = " + imageOneURI +
			"\nimageTwoURI = " + imageTwoURI +
			"\ndescriptionInEnglish = " + descriptionInEnglish +
			"\ndescriptionInLang = " + descriptionInLang +
			"\nthemesinEnglish = " + themesinEnglish +
			"\nthemesinLang = " + themesinLang +
			"\nreservedOne = " + reservedOne +
			"\nreservedTwo = " + reservedTwo;

		 return temp;
	}

	/**
	* main( ) method for  Word Class
	*/
	/*public static void main(String args[])
	{ 
		Word word_1 = new Word( );
		word_1.setEnglishInEnglish("englishInEnglish_dummy_string");
		word_1.setEnglishInLang("englishInLang_dummy_string");
		word_1.setLangInLang("langInLang_dummy_string");
		word_1.setLangInEnglish("langInEnglish_dummy_string");
		word_1.setHintInEnglish("hintInEnglish_dummy_string");
		word_1.setHintInLang("hintInLang_dummy_string");
		word_1.setSoundURIOfEnglish("soundURIOfEnglish_dummy_string");
		word_1.setSoundURIOfLang("soundURIOfLang_dummy_string");
		word_1.setImageOneURI("imageOneURI_dummy_string");
		word_1.setImageTwoURI("imageTwoURI_dummy_string");
		word_1.setDescriptionInEnglish("descriptionInEnglish_dummy_string");
		word_1.setDescriptionInLang("descriptionInLang_dummy_string");
		word_1.setThemesinEnglish(new ArrayList<String>( ));
		word_1.setThemesinLang(new ArrayList<String>( ));
		word_1.setReservedOne("reservedOne_dummy_string");
		word_1.setReservedTwo("reservedTwo_dummy_string");
		System.out.println(word_1);
	}*/
}