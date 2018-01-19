package DataStructures;

import javafx.collections.ObservableList;

public class AVLTree
{
	private AVLNode root;
	private static final int ALLOWED_IMBALANCE = 1;

	// Constructor
	public AVLTree( )
	{
		root = null;
	}

	// A method to empty the tree
	public void makeEmpty( )
	{
		root = null;
	}

	// A method to return if the tree is empty or not
	public boolean isEmpty( )
	{
		return root == null;
	}

	// A method to add the tree nodes to the ListView ObservableList in order
	public void inOrder(ObservableList<String> itemsList)
	{
		if( !isEmpty( ) )
			inOrder(itemsList, root);
	}

	public void inOrder(ObservableList<String> itemsList, AVLNode t)
	{
		if (t != null) {
			inOrder(itemsList, t.getLeft());
			itemsList.add(t.toString());
			inOrder(itemsList, t.getRight());
		}
	}

	// A method to add the tree nodes that start with a specific character to the ListView ObservableList in order
	public void inOrderC(ObservableList<String> itemsList, char listC)
	{
		if( !isEmpty( ) )
			inOrderC(itemsList, root, listC);
	}

	public void inOrderC(ObservableList<String> itemsList, AVLNode t, char listC)
	{
		if (t != null)
		{
			inOrderC(itemsList, t.getLeft(), listC);
			if(listC == t.getWord().charAt(0))
				itemsList.add(t.toString());
			inOrderC(itemsList, t.getRight(), listC);
		}
	}

	// A method to insert a record to the tree
	public void insert( String word, String[] meanings, String synonym, String antonym )
	{
		root = insert( word, meanings, synonym, antonym, root );
	}

	private AVLNode insert( String word, String[] meanings, String synonym, String antonym, AVLNode t )
	{
		if( t == null )
			return new AVLNode( word, meanings, synonym, antonym, null, null );

		if( word.compareToIgnoreCase(t.getWord()) < 0)
		{
			t.setLeft(insert( word, meanings, synonym, antonym, t.getLeft() ));

			if( height( t.getLeft() ) - height( t.getRight() ) == 2 )
				if( word.compareToIgnoreCase(t.getLeft().getWord()) < 0 )
					t = rotateLeft( t );
				else
					t = doubleLeft( t );
		}
		else if( word.compareToIgnoreCase(t.getWord()) > 0 )
		{
			t.setRight(insert( word, meanings, synonym, antonym, t.getRight() ));

			if( height( t.getRight() ) - height( t.getLeft() ) == 2 )
			{
				if( t.getLeft() != null && word.compareToIgnoreCase(t.getLeft().getWord()) > 0 )
					t = rotateRight( t );
				else if( t.getLeft() != null)
					t = doubleRight( t );
			}
		}

		t.setHeight(Math.max( height( t.getLeft() ), height( t.getRight() )) + 1);

		return t;
	}

	// A method to search for a specific record
	public AVLNode searchNode( String word )
	{
		return searchNode( word, root );
	}

	private AVLNode searchNode( String word, AVLNode t )
	{
		while( t != null )
		{
			if( word.compareToIgnoreCase(t.getWord()) < 0 )
				t = t.getLeft();
			else if( word.compareToIgnoreCase(t.getWord()) > 0 )
					t = t.getRight();
			else
				return t;
		}

		return null;
	}

	// A method to remove for a specific record
	public void remove( String word )
	{
		root = remove( word, root );
	}

	private AVLNode remove( String x, AVLNode t )
	{
		if( t == null )
			return t;

		int compareResult = x.compareTo( t.getWord() );

		if( compareResult < 0 )
			t.setLeft(remove( x, t.getLeft() ));
		else if( compareResult > 0 )
			t.setRight(remove( x, t.getRight() ));
		else if( t.getLeft() != null && t.getRight() != null )
		{
			AVLNode min = findMin( t.getRight() );

			t.setWord(min.getWord());
			t.setMeanings(min.getMeanings());
			t.setSynonym(min.getSynonym());
			t.setAntonym( min.getAntonym());

			t.setRight(remove( t.getWord(), t.getRight() ));
		}
		else
			t = ( t.getLeft() != null ) ? t.getLeft() : t.getRight();

		return balance( t );
	}

	// A method to find the smallest record in the tree
	private AVLNode findMin( )
	{
		if(isEmpty( ) )
			return null;

		return findMin( root );
	}

	private AVLNode findMin( AVLNode t )
	{
		if( t == null )
			return t;

		while( t.getLeft() != null )
			t = t.getLeft();

		return t;
	}

	// A method to balance the tree
	private AVLNode balance( AVLNode t )
	{
		if( t == null )
			return t;

		if( height( t.getLeft() ) - height( t.getRight() ) > ALLOWED_IMBALANCE )
			if( height( t.getLeft().getLeft() ) >= height( t.getLeft().getRight() ) )
				t = doubleLeft( t );
			else
				t = doubleLeft( t );
		else if( height( t.getRight() ) - height( t.getLeft() ) > ALLOWED_IMBALANCE )
				if( height( t.getRight().getRight() ) >= height( t.getRight().getLeft() ) )
					t = doubleRight( t );
				else
					t = doubleRight( t );

		t.setHeight(Math.max( height( t.getLeft() ), height( t.getRight() ) ) + 1);

		return t;
	}

	// A method to return the height of node t, or -1, if null
	private int height( AVLNode t )
	{
		return t == null ? -1 : t.getHeight();
	}

	// Rotate binary tree node with left child.
	private AVLNode rotateLeft( AVLNode node2 )
	{
		AVLNode node1 = null;

		node1 = node2.getLeft();
		node2.setLeft(node1.getRight());
		node1.setRight(node2);
		node2.setHeight(Math.max(height(node2.getLeft()), height(node2.getRight()))+1);
		node1.setHeight(Math.max(height(node1.getLeft()), node2.getHeight())+1);

		return node1;
	}

	//Rotate binary tree node with right child.
	private AVLNode rotateRight( AVLNode node1 )
	{
		AVLNode node2 = null;

		node2 = node1.getRight();
		node1.setRight(node2.getLeft());
		node2.setLeft(node1);
		node1.setHeight(Math.max(height(node1.getLeft()), height(node1.getRight())) + 1);
		node2.setHeight(Math.max(height(node2.getRight()), node1.getHeight()) + 1);

		return node2;
	}

	// Double rotate binary tree node
	private AVLNode doubleLeft( AVLNode node3 )
	{
		if(node3.getLeft().getRight() != null)
			node3.setLeft(rotateRight( node3.getLeft() ));
		if(node3.getLeft() != null)
			return rotateLeft( node3 );
		else
			return node3;
	}

	// Double rotate binary tree node
	private AVLNode doubleRight( AVLNode node1 )
	{
		if(node1.getRight().getLeft() != null)
			node1.setRight(rotateLeft( node1.getRight() ));
		if(node1.getRight() != null)
			return rotateRight( node1 );
		else
			return node1;
	}
}
