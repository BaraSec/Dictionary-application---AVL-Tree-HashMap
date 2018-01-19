package Controllers;

import DataStructures.AVLNode;
import DataStructures.AVLTree;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class UpdateController
{
	@FXML TextField iWord, iMeanings, iSyn, iAnt;
	@FXML Button update;

	private AVLTree dictTree;
	private ObservableList<String> itemsList;
	private AVLNode foundNode;

	// A method to initialize the UI objects
	public void initialize()
	{
		update.setDisable(true);
		iMeanings.setEditable(false);
		iSyn.setEditable(false);
		iAnt.setEditable(false);
	}

	// Setters
	public void setDictTree(AVLTree dictTree) {
		this.dictTree = dictTree;
	}

	public void setItemsList(ObservableList<String> itemsList) {
		this.itemsList = itemsList;
	}

	// To find a specific word (record)
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

			update.setDisable(false);
			iMeanings.setEditable(true);
			iSyn.setEditable(true);
			iAnt.setEditable(true);
		}
		else if(iWord.getText().trim().equals(""))
		{
			iMeanings.setText("");
			iSyn.setText("");
			iAnt.setText("");

			update.setDisable(true);
			iMeanings.setEditable(false);
			iSyn.setEditable(false);
			iAnt.setEditable(false);
		}
		else
		{
			iMeanings.setText("'" + iWord.getText().trim() + "' --> Was Not Found !");
			iSyn.setText("'" + iWord.getText().trim() + "' --> Was Not Found !");
			iAnt.setText("'" + iWord.getText().trim() + "' --> Was Not Found !");

			update.setDisable(true);
			iMeanings.setEditable(false);
			iSyn.setEditable(false);
			iAnt.setEditable(false);
		}
	}

	// To update a specific word (record)
	public void updateWord()
	{
		String[] means = iMeanings.getText().trim().split("[ ,]+");

		foundNode.setWord(iWord.getText().trim());
		foundNode.setSynonym(iSyn.getText().trim());
		foundNode.setAntonym(iAnt.getText().trim());
		foundNode.setMeanings(means);

		iWord.setText("");
		iMeanings.setText("");
		iSyn.setText("");
		iAnt.setText("");
		update.setDisable(true);
		iMeanings.setEditable(false);
		iSyn.setEditable(false);
		iAnt.setEditable(false);

		refreshData();
	}

	// To close the stage
	public void closeUpdateWord()
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
