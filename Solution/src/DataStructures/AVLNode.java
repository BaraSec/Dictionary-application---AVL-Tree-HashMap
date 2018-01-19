package DataStructures;

public class AVLNode
{
	private String word;
	private String[] meanings;
	private String synonym;
	private String antonym;
	private AVLNode left;
	private AVLNode right;
	private int height;

	// Constructors
	public AVLNode (String word, String[] meanings, String synonym, String antonym)
	{
		this(word, meanings, synonym, antonym, null, null);
	}

	public AVLNode (String word, String[] meanings, String synonym, String antonym, AVLNode lt, AVLNode rt)
	{
		this.word = word;
		this.meanings = meanings;
		this.synonym = synonym;
		this.antonym = antonym;
		left = lt;
		right = rt;
		height = 0;
	}

	// Getters and Setters for the Data Fields
	public String getWord()
	{
		return word;
	}

	public void setWord(String word)
	{
		this.word = word;
	}

	public String[] getMeanings()
	{
		return meanings;
	}

	public void setMeanings(String[] meanings)
	{
		this.meanings = meanings;
	}

	public String getSynonym()
	{
		return synonym;
	}

	public void setSynonym(String synonym)
	{
		this.synonym = synonym;
	}

	public String getAntonym()
	{
		return antonym;
	}

	public void setAntonym(String antonym)
	{
		this.antonym = antonym;
	}

	public AVLNode getLeft()
	{
		return left;
	}

	public void setLeft(AVLNode left)
	{
		this.left = left;
	}

	public AVLNode getRight()
	{
		return right;
	}

	public void setRight(AVLNode right)
	{
		this.right = right;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	// toString implementation
	public String toString()
	{
		String means = "";

		for(int i = 0; i < meanings.length - 1; i++)
			means += meanings[i] + ", ";

		means += meanings[meanings.length - 1];

		return word + ": " + means + " / " + synonym + " * " + antonym;
	}
}