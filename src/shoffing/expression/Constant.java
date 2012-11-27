package shoffing.expression;

import shoffing.binarytree.*;

public class Constant extends Operand
{
	private double value;
	
	public Constant(double value)
	{
		this.value = value;
	}
	
	public double eval( double[] vars, BinaryTree<ExpressionPart> subTree )
	{
		return value;
	}
	
	public double getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return "" + value;
	}
	
	public Type getType()
	{
		return ExpressionPart.Type.CONSTANT;
	}

	@Override
	public boolean equals(Object obj)
	{
		Constant other = (Constant) obj;

		boolean result = false;
		if(other.getType() == ExpressionPart.Type.CONSTANT)
			result = ((Constant) other).value == value;

		return result;
	}
}