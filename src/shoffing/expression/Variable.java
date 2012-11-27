package shoffing.expression;

import shoffing.binarytree.*;

public class Variable extends Operand
{
	private int index;
	
	public Variable(int index)
	{
		this.index = index;
	}
	
	public double eval( double[] vars, BinaryTree<ExpressionPart> subTree )
	{
		if(index >= 0 && index < vars.length)
			return vars[index];
		else
			return 0; // should probably throw an error here
	}
	
	public String toString()
	{
		return "V" + index;
	}
	
	public Type getType()
	{
		return ExpressionPart.Type.VARIABLE;
	}

	@Override
	public boolean equals(Object obj)
	{
		Variable other = (Variable) obj;
		
		boolean result = false;
		if(other.getType() == ExpressionPart.Type.VARIABLE)
			result = ((Variable) other).index == index;

		return result;
	}
}