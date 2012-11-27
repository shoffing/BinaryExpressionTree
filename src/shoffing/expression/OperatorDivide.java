package shoffing.expression;

import shoffing.binarytree.*;

public class OperatorDivide extends Operator
{
	public double eval( double[] vars, BinaryTree<ExpressionPart> subTree )
	{
		if(subTree.getLeft() != null && subTree.getRight() != null)
		{
			double RValue = subTree.getRight().getRoot().eval( vars, subTree.getRight() ); // do right value first to check if divide by 0
			double LValue = subTree.getLeft().getRoot().eval( vars, subTree.getLeft() );
			if(RValue != 0)
				return LValue / RValue;
			else
				return LValue / (RValue + 0.0000000001f); // should throw divide by 0 exception here
		} else
			return 0;
	}
	
	public BinaryTree<ExpressionPart> applyTo(BinaryTree<ExpressionPart> lhs, double rhs)
	{
		return new BinaryTree<ExpressionPart>(this, lhs, new Constant(rhs));
	}
	
	public String toString()
	{
		return "/";
	}
	
	public Type getType()
	{
		return ExpressionPart.Type.OPERATOR_DIV;
	}
	
	public boolean isUnary() { return false; }
	
	@Override
	public boolean equals(Object obj)
	{
		OperatorDivide other = (OperatorDivide) obj;
		return other.getType() == ExpressionPart.Type.OPERATOR_DIV;
	}
}