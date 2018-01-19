package Controllers;

import DataStructures.AVLNode;
import DataStructures.AVLTree;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FindController
{
	@FXML TextField iWord, iMeanings, iSyn, iAnt;

	private AVLTree dictTree;

	// Setter
	public void setDictTree(AVLTree dictTree) {
		this.dictTree = dictTree;
	}

	// To find a specific word (record)
	public void findWord()
	{
		AVLNode foundNode = dictTree.searchNode(iWord.getText().trim());

		if(foundNode != null)
		{
			String means = "";

			for(int i = 0; i < foundNode.getMeanings().length - 1; i++)
				means += foundNode.getMeanings()[i] + ", ";

			means += foundNode.getMeanings()[foundNode.getMeanings().length - 1];

			iMeanings.setText(means);
			iSyn.setText(foundNode.getSynonym().toString());
			iAnt.setText(foundNode.getAntonym().toString());
		}
		else if(iWord.getText().trim().equals(""))
		{
			iMeanings.setText("");
			iSyn.setText("");
			iAnt.setText("");
		}
		else
		{
			iMeanings.setText("'" + iWord.getText().trim() + "' --> Was Not Found !");
			iSyn.setText("'" + iWord.getText().trim() + "' --> Was Not Found !");
			iAnt.setText("'" + iWord.getText().trim() + "' --> Was Not Found !");
		}
	}

	// To close the stage
	public void closeFindWord()
	{
		iWord.getScene().getWindow().hide();
	}
}
