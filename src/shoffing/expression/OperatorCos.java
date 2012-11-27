package shoffing.expression;

import shoffing.binarytree.*;

public class OperatorCos extends Operator
{
	public double eval( double[] vars, BinaryTree<ExpressionPart> subTree )
	{
		if(subTree.getLeft() != null)
		{
			double value = subTree.getLeft().getRoot().eval( vars, subTree.getLeft() );
			return  Math.cos(value);
		} else
			return 0;
	}
	
	public BinaryTree<ExpressionPart> applyTo(BinaryTree<ExpressionPart> lhs, double rhs)
	{
		return new BinaryTree<ExpressionPart>(this, lhs);
	}
	
	public String toString()
	{
		return "cos";
	}
	
	public Type getType()
	{
		return ExpressionPart.Type.OPERATOR_COS;
	}
	
	public boolean isUnary() { return true; }
	
	@Override
	public boolean equals(Object obj)
	{
		OperatorCos other = (OperatorCos) obj;
		return other.getType() == ExpressionPart.Type.OPERATOR_COS;
	}
}