package shoffing.expression;

import shoffing.binarytree.*;

public class OperatorSin extends Operator
{
	public double eval( double[] vars, BinaryTree<ExpressionPart> subTree )
	{
		if(subTree.getLeft() != null)
		{
			double value = subTree.getLeft().getRoot().eval( vars, subTree.getLeft() );
			return  Math.sin(value);
		} else
			return 0;
	}
	
	public BinaryTree<ExpressionPart> applyTo(BinaryTree<ExpressionPart> lhs, double rhs)
	{
		return new BinaryTree<ExpressionPart>(this, lhs);
	}
	
	public String toString()
	{
		return "sin";
	}
	
	public Type getType()
	{
		return ExpressionPart.Type.OPERATOR_SIN;
	}
	
	public boolean isUnary() { return true; }
	
	@Override
	public boolean equals(Object obj)
	{
		OperatorSin other = (OperatorSin) obj;
		return other.getType() == ExpressionPart.Type.OPERATOR_SIN;
	}
}