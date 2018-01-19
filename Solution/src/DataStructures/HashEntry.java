package DataStructures;

public class HashEntry
{
	private String wordKey;
	private String[] meanings;
	private String synonym;
	private String antonym;
	private byte status; // inserted: 1, deleted: 2, empty: 0

	// Constructor
	public HashEntry(String wordKey, String[] meanings, String synonym, String antonym, byte status)
	{
		this.wordKey = wordKey;
		this.meanings = meanings;
		this.synonym = synonym;
		this.antonym = antonym;
		this.status = status;
	}

	// Getters and Setters for the Data Fields
	public String getWordKey()
	{
		return wordKey;
	}

	public String[] getMeanings()
	{
		return meanings;
	}

	public String getSynonym()
	{
		return synonym;
	}

	public String getAntonym()
	{
		return antonym;
	}

	public byte getStatus()
	{
		return status;
	}

	public void setDeleteStatus()
	{
		status = 2;
	}

	// toString implementation
	public String toString()
	{
		String means = "";

		for(int i = 0; i < meanings.length - 1; i++)
			means += meanings[i] + ", ";

		means += meanings[meanings.length - 1];

		return wordKey + ": " + means + " / " + synonym + " * " + antonym;
	}
}