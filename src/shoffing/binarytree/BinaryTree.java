package shoffing.binarytree;

import shoffing.binarytree.*;
import java.util.ArrayList;

public class BinaryTree<T>
{
	protected BTNode<T> root;
	
	public BinaryTree()
	{
		root = null;
	}
	
	public BinaryTree(T elem)
	{
		root = new BTNode(elem);
	}
	
	public BinaryTree(T elem, BinaryTree<T> left, BinaryTree<T> right)
	{
		root = new BTNode(elem);
		root.setLeft(left.root);
		root.setRight(right.root);
	}
	
	public BinaryTree(T elem, T left, T right)
	{
		root = new BTNode(elem);
		root.setLeft(new BTNode(left));
		root.setRight(new BTNode(right));
	}
	
	public BinaryTree(T elem, BinaryTree<T> left, T right)
	{
		root = new BTNode(elem);
		root.setLeft(left.root);
		root.setRight(new BTNode(right));
	}
	
	public BinaryTree(T elem, T left, BinaryTree<T> right)
	{
		root = new BTNode(elem);
		root.setLeft(new BTNode(left));
		root.setRight(right.root);
	}
	
	private BinaryTree(BTNode<T> subtree)
	{
		root = new BTNode<T>(subtree);
	}
	
	// unary constructors
	public BinaryTree(T elem, T left)
	{
		root = new BTNode(elem);
		root.setLeft(new BTNode(left));
		root.setRight(null);
	}
	
	public BinaryTree(T elem, BinaryTree<T> left)
	{
		root = new BTNode(elem);
		root.setLeft(left.root);
		root.setRight(null);
	}
	
	// Copy constructor
	public BinaryTree(BinaryTree<T> toCopy)
	{
		this(toCopy.root);
	}
	
	//
	
	public T getRoot()
	{
		T result = null;
		
		if(root != null)
			result = root.getElement();
		
		return result;
	}
	
	public BinaryTree<T> getLeft()
	{
		BinaryTree<T> result = null;
		
		if(root != null)
		{
			result = new BinaryTree<T>();
			result.root = root.getLeft();
		}

		return result;
	}
	
	public BinaryTree<T> getRight()
	{
		BinaryTree<T> result = null;
		
		if(root != null)
		{
			result = new BinaryTree<T>();
			result.root = root.getRight();
		}

		return result;
	}

	public void setRoot(T val)
	{
		root.setElement(val);
	}
	
	public void setLeft(BinaryTree<T> val)
	{
		if(root != null)
			root.left = val.root;
	}
	
	public void setRight(BinaryTree<T> val)
	{
		if(root != null)
			root.right = val.root;
	}
	
	protected void setLeft(BTNode<T> val)
	{
		if(root != null)
			root.left = val;
	}
	
	protected void setRight(BTNode<T> val)
	{
		if(root != null)
			root.right = val;
	}
	
	//
	
	public int size()
	{
		int result = (root != null ? root.count() : 0);
		return result;
	}
	
	// Root Left Right
	public ArrayList<T> preOrder()
	{
		ArrayList<T> result = new ArrayList<T>();
		
		if(root != null)
			root.preOrder(result);
		
		return result;
	}
	
	public ArrayList<BinaryTree<T>> preOrderTree()
	{
		ArrayList<BinaryTree<T>> result = new ArrayList<BinaryTree<T>>();
		
		if(root != null)
		{
			ArrayList<BTNode<T>> nodes = new ArrayList<BTNode<T>>();
			root.preOrderTree(nodes);
			
			for(BTNode<T> n : nodes)
				result.add(new BinaryTree<T>(n));
		}
		
		return result;
	}
	
	// Left Root Right
	public ArrayList<T> inOrder()
	{
		ArrayList<T> result = new ArrayList<T>();
		
		if(root != null)
			root.inOrder(result);
		
		return result;
	}
	
	public ArrayList<BinaryTree<T>> inOrderTree()
	{
		ArrayList<BinaryTree<T>> result = new ArrayList<BinaryTree<T>>();
		
		if(root != null)
		{
			ArrayList<BTNode<T>> nodes = new ArrayList<BTNode<T>>();
			root.inOrderTree(nodes);
			
			for(BTNode<T> n : nodes)
				result.add(new BinaryTree<T>(n));
		}
		
		return result;
	}
	
	// Left Right Root
	public ArrayList<T> postOrder()
	{
		ArrayList<T> result = new ArrayList<T>();
		
		if(root != null)
			root.postOrder(result);
		
		return result;
	}
	
	public ArrayList<BinaryTree<T>> postOrderTree()
	{
		ArrayList<BinaryTree<T>> result = new ArrayList<BinaryTree<T>>();
		
		if(root != null)
		{
			ArrayList<BTNode<T>> nodes = new ArrayList<BTNode<T>>();
			root.postOrderTree(nodes);
			
			for(BTNode<T> n : nodes)
				result.add(new BinaryTree<T>(n));
		}
		
		return result;
	}
	
	public boolean isEmpty()
	{
		return root == null;
	}
	
	// Index is based on preorder traversal
	public void setTreeAt(int index, BinaryTree<T> val)
	{
		if(index >= 0 && index < size())
			root.setTreeAt(index, val.root);
	}
	
	public void setValueAt(int index, T val)
	{
		if(index >= 0 && index < size())
			root.setValueAt(index, val);
	}
	
	public BinaryTree<T> getTreeAt(int index)
	{
		BinaryTree<T> result = null;
		
		if(index >= 0 && index < size())
			result = preOrderTree().get(index);
		
		return result;
	}
	
	public T getValueAt(int index)
	{
		T result = null;
		
		if(index >= 0 && index < size())
			result = preOrder().get(index);
			
		return result;
	}
	
	public String toString()
	{
		String result = "";
		
		ArrayList<T> asArr = preOrder();
		for(T elem : asArr)
			result += elem + " ";
		
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof BinaryTree))
			return false;
		
		BinaryTree<T> other = (BinaryTree<T>) obj;
		if(root != null && other.root != null)
			return root.equals(other.root);
		else
			return root == other.root;
	}
}