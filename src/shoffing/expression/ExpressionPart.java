package shoffing.expression;

import shoffing.binarytree.*;

public abstract class ExpressionPart
{
	enum Type {
		VARIABLE,
		CONSTANT,
		OPERATOR_ADD,
		OPERATOR_SUB,
		OPERATOR_MULT,
		OPERATOR_DIV,
		OPERATOR_POW,
		OPERATOR_SIN,
		OPERATOR_COS,
		OPERATOR_TAN
	}
	
	public abstract Type getType();
	public abstract boolean isOperand();
	public abstract boolean isOperator();
	
	public abstract double eval( double[] vars, BinaryTree<ExpressionPart> subTree );
	public abstract String toString();

	@Override
	public abstract boolean equals(Object obj);
}