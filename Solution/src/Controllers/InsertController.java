package Controllers;

import DataStructures.AVLTree;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InsertController
{
	@FXML TextField iWord, iMeanings, iSyn, iAnt;

	private AVLTree dictTree;
	private ObservableList<String> itemsList;

	// Setters
	public void setDictTree(AVLTree dictTree) {
		this.dictTree = dictTree;
	}

	public void setItemsList(ObservableList<String> itemsList) {
		this.itemsList = itemsList;
	}

	// To insert a specific word (record)
	public void insertWord()
	{
		String word = iWord.getText();

		if(!word.equals(""))
		{
			String synonym = iSyn.getText();
			String antonym = iAnt.getText();
			String[] meanings = iMeanings.getText().split("[, ]+");

			dictTree.insert(word, meanings, synonym, antonym);

			iWord.setText("");
			iMeanings.setText("");
			iSyn.setText("");
			iAnt.setText("");
		}

		refreshData();
	}

	// To close the stage
	public void closeInsertWord()
	{
		iWord.getScene().getWindow().hide();
	}

	// To refresh the listView
	public void refreshData()
	{
		itemsList.clear();
		itemsList.add("Word: meaning1, meaning2, ... , meaningN / a synonym * an antonym");
		dictTree.inOrder(itemsList);
	}
}
