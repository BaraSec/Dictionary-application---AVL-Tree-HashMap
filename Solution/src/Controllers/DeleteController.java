package Controllers;

import DataStructures.AVLNode;
import DataStructures.AVLTree;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeleteController
{
	@FXML TextField iWord, iMeanings, iSyn, iAnt;
	@FXML Button delete;

	private AVLTree dictTree;
	private ObservableList<String> itemsList;
	private AVLNode foundNode;

	// A method to initialize the UI objects
	public void initialize()
	{
		delete.setDisable(true);
	}

	// Setters for the Data Fields
	public void setDictTree(AVLTree dictTree) {
		this.dictTree = dictTree;
	}

	public void setItemsList(ObservableList<String> itemsList) {
		this.itemsList = itemsList;
	}

	// to find a specific word (record)
	public void findWord()
	{
		foundNode = dictTree.searchNode(iWord.getText().trim());

		if(foundNode != null)
		{
			String means = "";
			for(int i = 0; i < foundNode.getMeanings().length - 1; i++)
				means += foundNode.getMeanings()[i] + ", ";
			means += foundNode.getMeanings()[foundNode.getMeanings().length - 1];

			iMeanings.setText(means);
			iSyn.setText(foundNode.getSynonym().toString());
			iAnt.setText(foundNode.getAntonym().toString());

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

	// To delete a specific word (record)
	public void deleteWord()
	{
		dictTree.remove(foundNode.getWord());

		iWord.setText("");
		iMeanings.setText("");
		iSyn.setText("");
		iAnt.setText("");

		refreshData();
	}

	// To close the stage
	public void closeDeleteWord()
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
