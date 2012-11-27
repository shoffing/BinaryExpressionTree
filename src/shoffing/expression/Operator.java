package shoffing.expression;

import shoffing.binarytree.*;

public abstract class Operator extends ExpressionPart
{
	public abstract Type getType();
	
	public abstract double eval( double[] vars, BinaryTree<ExpressionPart> subTree );
	public abstract BinaryTree<ExpressionPart> applyTo( BinaryTree<ExpressionPart> lhs, double rhs );
	public abstract String toString();

	@Override
	public abstract boolean equals(Object obj);
	
	public boolean isOperand() { return false; }
	public boolean isOperator() { return true; }
	
	public abstract boolean isUnary();
}