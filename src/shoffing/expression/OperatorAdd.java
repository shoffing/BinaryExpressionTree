package shoffing.expression;

import shoffing.binarytree.*;

public class OperatorAdd extends Operator
{
	public double eval( double[] vars, BinaryTree<ExpressionPart> subTree )
	{
		if(subTree.getLeft() != null && subTree.getRight() != null)
		{
			double LValue = subTree.getLeft().getRoot().eval( vars, subTree.getLeft() );
			double RValue = subTree.getRight().getRoot().eval( vars, subTree.getRight() );
			return LValue + RValue;
		} else
			return 0;
	}
	
	public BinaryTree<ExpressionPart> applyTo(BinaryTree<ExpressionPart> lhs, double rhs)
	{
		return new BinaryTree<ExpressionPart>(this, lhs, new Constant(rhs));
	}
	
	public String toString()
	{
		return "+";
	}
	
	public Type getType()
	{
		return ExpressionPart.Type.OPERATOR_ADD;
	}
	
	public boolean isUnary() { return false; }

	@Override
	public boolean equals(Object obj)
	{
		OperatorAdd other = (OperatorAdd) obj;
		return other.getType() == ExpressionPart.Type.OPERATOR_ADD;
	}
}