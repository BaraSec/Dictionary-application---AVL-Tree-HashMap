package Controllers;

import DataStructures.AVLTree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainController
{
	@FXML private ListView<String> list, list2;
	@FXML private TextField character;
	@FXML private Button deleteLC, listC;
	@FXML private MenuItem genHash, load, export;
	@FXML private Menu avOPMenu, htMenu;

	private AVLTree dictTree;

	// A method to initialize the UI objects
	public void initialize() throws FileNotFoundException
	{
		deleteLC.setDisable(true);
		genHash.setDisable(true);
		listC.setDisable(true);
		export.setDisable(true);
		avOPMenu.setDisable(true);
		htMenu.setDisable(true);

		readData();
	}

	// To fill the listView
	public void fillList()
	{
		list.getItems().add("Word: meaning1, meaning2, ... , meaningN / a synonym * an antonym");
		dictTree.inOrder(list.getItems());

		load.setDisable(true);
		listC.setDisable(false);
		export.setDisable(false);
		avOPMenu.setDisable(false);
		htMenu.setDisable(false);
	}

	// To read the data from the file
	public void readData() throws FileNotFoundException
	{
		dictTree = new AVLTree();

		File file = new File("src/resources/I_O Files/inputWords.txt");
		Scanner input = new Scanner(file);

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

			dictTree.insert(word, meanings, synonym, antonym);
		}

		input.close();
	}

	// to find the words (records) that start with a specific character
	public void findListC()
	{
		char listC = ' ';

		if(!character.getText().equals(""))
		{
			listC = character.getText().trim().charAt(0);

			list2.getItems().clear();
			list2.getItems().add("Word: meaning1, meaning2, ... , meaningN / a synonym * an antonym");
			dictTree.inOrderC(list2.getItems(), listC);

			if(list2.getItems().size() > 1)
				deleteLC.setDisable(false);
			else
				deleteLC.setDisable(true);
		}
	}

	// to delete the words (records) that start with a specific character
	public void deleteListC()
	{
		boolean flag = true;

		for(String x: list2.getItems())
		{
			if(flag)
				flag = false;
			else
				dictTree.remove(x.split("[: ]+")[0]);
		}

		list2.getItems().clear();
		refreshList();

		deleteLC.setDisable(true);
	}

	// To refresh the listView
	public void refreshList()
	{
		list.getItems().clear();
		list.getItems().add("Word: meaning1, meaning2, ... , meaningN / a synonym * an antonym");
		dictTree.inOrder(list.getItems());
	}

	// to export the tree to a file
	public void export() throws FileNotFoundException
	{
		File file = new File("src/resources/I_O Files/dictionary.data");
		PrintWriter outData = new PrintWriter(file);

		for(String x: list.getItems())
			outData.println(x);

		outData.close();

		genHash.setDisable(false);
	}

	// to close the program
	public void close()
	{
		System.exit(0);
	}

	// to open the insert window
	public void openInsertWord() throws Exception
	{
		Stage primaryStage2 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/UIs.FXML/InsertUI.fxml"));
		Parent root = loader.load();

		InsertController controller = loader.getController();
		controller.setDictTree(dictTree);
		controller.setItemsList(list.getItems());

		primaryStage2.setTitle("Insert a word");
		primaryStage2.getIcons().add(new Image("file:src/resources/Images/insert.png"));
		primaryStage2.setScene(new Scene(root));
		primaryStage2.setResizable(false);
		primaryStage2.show();
	}

	// to open the update window
	public void updateWord() throws Exception
	{
		Stage primaryStage3 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/UIs.FXML/UpdateUI.fxml"));
		Parent root = loader.load();

		UpdateController controller = loader.getController();
		controller.setDictTree(dictTree);
		controller.setItemsList(list.getItems());

		primaryStage3.setTitle("Update a word");
		primaryStage3.getIcons().add(new Image("file:src/resources/Images/update.png"));
		primaryStage3.setScene(new Scene(root));
		primaryStage3.setResizable(false);
		primaryStage3.show();
	}

	// to open the find window
	public void findWord() throws Exception
	{
		Stage primaryStage4 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/UIs.FXML/FindUI.fxml"));
		Parent root = loader.load();

		FindController controller = loader.getController();
		controller.setDictTree(dictTree);

		primaryStage4.setTitle("Find a word");
		primaryStage4.getIcons().add(new Image("file:src/resources/Images/find.png"));
		primaryStage4.setScene(new Scene(root));
		primaryStage4.setResizable(false);
		primaryStage4.show();
	}

	// to open the delete window
	public void deleteWord() throws Exception
	{
		Stage primaryStage5 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/UIs.FXML/DeleteUI.fxml"));
		Parent root = loader.load();

		DeleteController controller = loader.getController();
		controller.setDictTree(dictTree);
		controller.setItemsList(list.getItems());

		primaryStage5.setTitle("Delete a word");
		primaryStage5.getIcons().add(new Image("file:src/resources/Images/delete.png"));
		primaryStage5.setScene(new Scene(root));
		primaryStage5.setResizable(false);
		primaryStage5.show();
	}

	// to open the hashMap window
	public void openHashWorld() throws Exception
	{
		Stage primaryStage6 = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/UIs.FXML/HashUI.fxml"));
		Parent root = loader.load();

		primaryStage6.setTitle("HashWorld");
		primaryStage6.getIcons().add(new Image("file:src/resources/Images/DS_Hash.png"));
		primaryStage6.setScene(new Scene(root));
		primaryStage6.setResizable(false);
		primaryStage6.show();

		genHash.setDisable(true);
	}
}