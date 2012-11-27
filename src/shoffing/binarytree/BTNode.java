package shoffing.binarytree;

import shoffing.binarytree.*;
import java.util.ArrayList;

public class BTNode<T>
{
	protected T element;
	protected BTNode<T> left, right;
	
	public BTNode(T elem)
	{
		element = elem;
		left = right = null;
	}
	
	public BTNode(T elem, BTNode<T> left, BTNode<T> right)
	{
		element = elem;
		
		this.left = this.right = null;
		
		if((left != null && left.getElement() != null))
			this.left = new BTNode<T>(left);
		if((right != null && right.getElement() != null))
			this.right = new BTNode<T>(right);
	}
	
	// copy
	public BTNode(BTNode<T> node)
	{
		this(node.getElement(), node.getLeft(), node.getRight());
	}
	
	//
	
	public T getElement()
	{
		return element;
	}
	
	public BTNode<T> getLeft()
	{
		return left;
	}
	
	public BTNode<T> getRight()
	{
		return right;
	}
	
	public void setElement(T val)
	{
		element = val;
	}
	
	public void setLeft(BTNode<T> val)
	{
		left = val;
	}
	
	public void setRight(BTNode<T> val)
	{
		right = val;
	}
	
	public void setValueAt(int index, T val)
	{
		if(index == 0)
			element = val;
		else
		{
			if((left != null && left.getElement() != null))
			{
				left.setValueAt(index - 1, val);
				
				if((right != null && right.getElement() != null))
					right.setValueAt(index - 1 - left.count(), val);
			} else if((right != null && right.getElement() != null)) {
				right.setValueAt(index - 1, val);
			}
		}
	}
	
	public void setTreeAt(int index, BTNode<T> val)
	{
		if(index == 0)
		{
			element = val.getElement();
			
			if(val.getLeft() != null)
				left = new BTNode(val.getLeft());
			else
				left = null;
			
			if(val.getRight() != null)
				right = new BTNode(val.getRight());
			else
				right = null;
		} else {
			if((left != null && left.getElement() != null))
			{
				left.setTreeAt(index - 1, val);
				
				if((right != null && right.getElement() != null))
					right.setTreeAt(index - 1 - left.count(), val);
			} else if((right != null && right.getElement() != null)) {
				right.setTreeAt(index - 1, val);
			}
		}
	}
	
	//
	
	public int count()
	{
		int result = 1;
		
		result += ((left != null && left.getElement() != null) ? left.count() : 0);
		result += ((right != null && right.getElement() != null) ? right.count() : 0);
		
		return result;
	}
	
	// Root Left Right
	public void preOrder(ArrayList<T> arr)
	{
		arr.add(element);
		
		if((left != null && left.getElement() != null))
			left.preOrder(arr);
		
		if((right != null && right.getElement() != null))
			right.preOrder(arr);
	}
	
	public void preOrderTree(ArrayList<BTNode<T>> arr)
	{
		arr.add(new BTNode(this));
		
		if((left != null && left.getElement() != null))
			left.preOrderTree(arr);
		
		if((right != null && right.getElement() != null))
			right.preOrderTree(arr);
	}
	
	// Left Root Right
	public void inOrder(ArrayList<T> arr)
	{
		if((left != null && left.getElement() != null))
			left.inOrder(arr);
		
		arr.add(element);
		
		if((right != null && right.getElement() != null))
			right.inOrder(arr);
	}
	
	public void inOrderTree(ArrayList<BTNode<T>> arr)
	{
		if((left != null && left.getElement() != null))
			left.inOrderTree(arr);
		
		arr.add(new BTNode(this));
		
		if((right != null && right.getElement() != null))
			right.inOrderTree(arr);
	}
	
	// Left Right Root
	public void postOrder(ArrayList<T> arr)
	{
		if((left != null && left.getElement() != null))
			left.postOrder(arr);
		
		if((right != null && right.getElement() != null))
			right.postOrder(arr);
		
		arr.add(element);
	}
	
	public void postOrderTree(ArrayList<BTNode<T>> arr)
	{
		if((left != null && left.getElement() != null))
			left.postOrderTree(arr);
		
		if((right != null && right.getElement() != null))
			right.postOrderTree(arr);
		
		arr.add(new BTNode(this));
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof BTNode))
			return false;

		boolean result = true;

		BTNode<T> other = (BTNode<T>) obj;
		if(other.element.getClass() != element.getClass())
			return false;
		
		// Check if root elements are equal
		if(element != null && other.element != null)
			result = result && element.equals(other.element);
		else
			result = result && (element == other.element);

		// Check if left BTNodes are equal
		if(left != null && other.left != null)
			result = result && left.equals(other.left);
		else
			result = result && (left == other.left);

		// Check if right BTNodes are equal
		if(right != null && other.right != null)
			result = result && right.equals(other.right);
		else
			result = result && (right == other.right);

		return result;
	}
}