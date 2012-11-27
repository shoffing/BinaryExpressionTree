package shoffing.expression;

import shoffing.binarytree.*;

public class OperatorTan extends Operator
{
	public double eval( double[] vars, BinaryTree<ExpressionPart> subTree )
	{
		if(subTree.getLeft() != null)
		{
			double value = subTree.getLeft().getRoot().eval( vars, subTree.getLeft() );
			return (double) Math.tan(value);
		} else
			return 0;
	}
	
	public BinaryTree<ExpressionPart> applyTo(BinaryTree<ExpressionPart> lhs, double rhs)
	{
		return new BinaryTree<ExpressionPart>(this, lhs);
	}
	
	public String toString()
	{
		return "tan";
	}
	
	public Type getType()
	{
		return ExpressionPart.Type.OPERATOR_TAN;
	}
	
	public boolean isUnary() { return true; }
	
	@Override
	public boolean equals(Object obj)
	{
		OperatorTan other = (OperatorTan) obj;
		return other.getType() == ExpressionPart.Type.OPERATOR_TAN;
	}
}