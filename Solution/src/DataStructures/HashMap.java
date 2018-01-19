package DataStructures;
import javafx.collections.ObservableList;

public class HashMap
{
	private int tableSize = 128;
	private HashEntry[] table;
	private int currentSize = 0;

	// Constructor
	public HashMap(int size)
	{
		table = new HashEntry[size];

		for (int i = 0; i < size; i++)
			table[i] = null;
		tableSize = size;

		currentSize = 0;
	}

	// A method to empty the hashmap
	public void makeEmpty( )
	{
		for( int i = 0; i < table.length; i++ )
			table[ i ] = null;

		currentSize = 0;
	}

	// A method to return the current size
	public int getCurrentSize()
	{
		return currentSize;
	}

	// A method to return the table size
	public int getTableSize()
	{
		return tableSize;
	}

	// A method to check if a hashEntry is present
	public boolean contains(String key)
	{
		return find(key) != null;
	}

	// A method to find a hashEntry and return it
	public HashEntry find(String key)
	{
		int i = 1;
		int location = getHash(key);

		while ((table[location] != null) && (table[location].getStatus() == 1))
		{
			if (table[location].getWordKey().equals(key))
				return table[location];

			location = (location + i*i) % tableSize;
		}
		return null;
	}

	// A method to remove a hashEntry
	public void remove(String key)
	{
		int i = 1;

		if (!contains(key))
			return;

		int hash = getHash(key);

		while ((table[hash] != null) && (table[hash].getStatus() == 1) && (!table[hash].getWordKey().equals(key)))
			hash = (hash + i*i) % tableSize;

		currentSize--;
		table[hash].setDeleteStatus();
	}

	// A method to insert a hashEntry to the hashmap
	public void insert(String wordKey, String[] meanings, String synonym, String antonym)
	{
		if ( currentSize >= tableSize / 2)
			rehash();

		int hash = getHash(wordKey), i = 1;

		while ((table[hash] != null) && (table[hash].getStatus() != 0) && (table[hash].getStatus() != 2))
			hash = (hash + i*i) % tableSize; // Quadratic

		currentSize++;
		table[hash] = new HashEntry(wordKey, meanings, synonym, antonym,(byte)1);
	}

	// A method to get a suitable hash
	private int getHash( String key)
	{
		int hashVal = 0;

		for( int i = 0; i < key.length( ); i++ )
			hashVal = (2<<5) * hashVal + key.charAt(i);

		hashVal %= tableSize;

		if( hashVal < 0 )
			hashVal += tableSize;

		return hashVal;
	}

	// A method to rehash and expand the hashmap
	private void rehash( )
	{
		HashMap newList;
		newList = new HashMap(nextPrime( 2 * table.length ));

		for( int i = 0; i < table.length; i++ )
			if ((table[i] != null) && (table[i].getStatus() == 1))
				newList.insert( table[ i ].getWordKey(), table[ i ].getMeanings(), table[ i ].getSynonym(), table[ i ].getAntonym());

		table = newList.table;
		tableSize = newList.tableSize;
	}

	// A method to return the next prime after a number
	private static int nextPrime( int n )
	{
		if( n % 2 == 0 )
			n++;
		for( ; !isPrime( n ); n += 2 );

		return n;
	}

	// A method to check if a number is a prime
	private static boolean isPrime( int n )
	{
		if( n == 2 || n == 3 )
			return true;
		if( n == 1 || n % 2 == 0 )
			return false;
		for( int i = 3; i * i <= n/2; i += 2 )
			if( n % i == 0 )
				return false;
		return true;
	}

	// A method to add the hashmap entries to the ListView ObservableList in order
	public void inOrder(ObservableList<String> itemsList)
	{
		for( int i = 0; i < table.length; i++ )
			if ((table[ i ] != null) && (table[i].getStatus() == 1))
				itemsList.add(table[ i ].toString());
		else
				itemsList.add("Empty Slot");
	}
}