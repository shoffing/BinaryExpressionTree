package shoffing.expression;

import shoffing.binarytree.*;

public abstract class Operand extends ExpressionPart
{
	public abstract Type getType();
	
	public abstract double eval( double[] vars, BinaryTree<ExpressionPart> subTree );
	
	public abstract String toString();
	
	@Override
	public abstract boolean equals(Object obj);
	
	public boolean isOperand() { return true; }
	public boolean isOperator() { return false; }
}