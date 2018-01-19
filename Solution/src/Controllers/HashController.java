package Controllers;

import DataStructures.HashEntry;
import DataStructures.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.*;
import java.util.Scanner;

public class HashController
{
	@FXML private ListView<String> list;
	@FXML private TextField hFun, tSize, iWord, iMeanings, iSyn, iAnt, nWord, nMeanings, nSyn, nAnt;
	@FXML private Button delete;

	private HashMap dictHash;
	private HashEntry foundkey;

	// A method to initialize the UI objects
	public void initialize() throws IOException
	{
		delete.setDisable(true);
		fillList();
		tSize.setText(dictHash.getTableSize() + "");
		hFun.setText("hash = (hash + i*i) % tableSize");
	}

	// To fill the listView
	public void fillList() throws IOException
	{
		readData();
		list.getItems().add("Word: meaning1, meaning2, ... , meaningN / a synonym * an antonym");
		dictHash.inOrder(list.getItems());
	}

	// To read the data from the file
	public void readData() throws IOException
	{
		dictHash = new HashMap(nextPrime(NoOfEntries() * 2));
		File file = new File("src/resources/I_O Files/dictionary.data");

		Scanner input = new Scanner(file);
		input.nextLine();

		while(input.hasNextLine())
		{
			String str = input.nextLine();
			String[] data = str.split("[:,/* ]+");

			String word = data[0];

			String synonym = data[data.length - 2];
			String antonym = data[data.length - 1];

			String[] meanings = new String[data.length - 3];

			for(int i = 1; i < data.length - 2; i++)
				meanings[i - 1] = data[i];

			dictHash.insert(word, meanings, synonym, antonym);
		}

		input.close();
	}

	// to find a specific word (record)
	public void findWord()
	{
		foundkey = dictHash.find(iWord.getText().trim());

		if(foundkey != null)
		{
			String means = "";

			for(int i = 0; i < foundkey.getMeanings().length - 1; i++)
				means += foundkey.getMeanings()[i] + ", ";

			means += foundkey.getMeanings()[foundkey.getMeanings().length - 1];

			iMeanings.setText(means);
			iSyn.setText(foundkey.getSynonym().toString());
			iAnt.setText(foundkey.getAntonym().toString());

			delete.setDisable(false);
		}
		else if(iWord.getText().trim().equals(""))
		{
			iMeanings.setText("");
			iSyn.setText("");
			iAnt.setText("");

			delete.setDisable(true);
			iMeanings.setEditable(false);
			iSyn.setEditable(false);
			iAnt.setEditable(false);
		}
		else
		{
			iMeanings.setText("'" + iWord.getText().trim() + "' --> Was Not Found !");
			iSyn.setText("'" + iWord.getText().trim() + "' --> Was Not Found !");
			iAnt.setText("'" + iWord.getText().trim() + "' --> Was Not Found !");

			delete.setDisable(true);
		}
	}

	// to delete a specific word (record)
	public void deleteWord()
	{
		dictHash.remove(foundkey.getWordKey());

		iWord.setText("");
		iMeanings.setText("");
		iSyn.setText("");
		iAnt.setText("");

		refreshData();
	}

	// to insert a specific word (record)
	public void insertWord()
	{
		String word = nWord.getText().trim();

		if(dictHash.find(word) == null && !word.equals(""))
		{
			String synonym = nSyn.getText();
			String antonym = nAnt.getText();

			String[] meanings = nMeanings.getText().split("[, ]+");

			dictHash.insert(word, meanings, synonym, antonym);

			nWord.setText("");
			nMeanings.setText("");
			nSyn.setText("");
			nAnt.setText("");

			iWord.setText("");
			iMeanings.setText("");
			iSyn.setText("");
			iAnt.setText("");

			delete.setDisable(true);

			refreshData();
		}
		else if(dictHash.find(word) != null)
		{
			nWord.setText("");
			nMeanings.setText("");
			nSyn.setText("");
			nAnt.setText("");
		}
	}

	// to export the hashmap to a file
	public void export() throws FileNotFoundException
	{
		File file = new File("src/resources/I_O Files/dictionary.data");
		PrintWriter outData = new PrintWriter(file);

		for(String x: list.getItems())
			if(!x.equals("Empty Slot"))
				outData.println(x);

		outData.close();
	}

	// To refresh the listView
	public void refreshData()
	{
		list.getItems().clear();
		list.getItems().add("Word: meaning1, meaning2, ... , meaningN / a synonym * an antonym");

		dictHash.inOrder(list.getItems());

		tSize.setText(dictHash.getTableSize() + "");
	}

	// To find the number of lines (entries) in the file
	public int NoOfEntries() throws IOException
	{
		int counter = 0;
		BufferedReader reader = new BufferedReader(new FileReader("src/resources/I_O Files/dictionary.data"));

		reader.readLine();
		while (reader.readLine() != null)
			counter++;

		reader.close();

		return counter;
	}

	// To find the next prime to a number
	private static int nextPrime( int n )
	{
		if( n % 2 == 0 )
			n++;
		for( ; !isPrime( n ); n += 2 );

		return n;
	}

	// To check if a number is prime or not
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

	// To close the stage
	public void closeHashWorld()
	{
		iWord.getScene().getWindow().hide();
	}
}
