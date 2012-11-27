package shoffing.expression;

import shoffing.binarytree.*;
import java.util.ArrayList;

public class Expression
{
	private double CHANCE_ADD = 1.0, CHANCE_SUB = 1.0, CHANCE_MULT = 1.0, CHANCE_DIV = 1.0, CHANCE_POW = 1.0, CHANCE_SIN = 1.0, CHANCE_COS = 1.0, CHANCE_TAN = 1.0;
	
	private BinaryTree<ExpressionPart> expression;
	private int numVars;
	private double MIN_VAL, MAX_VAL;
	public static int decimalPlaces;
	
	public Expression( int numVars, double minVal, double maxVal )
	{
		expression = new BinaryTree<ExpressionPart>(new Constant( (Math.random() * (maxVal - minVal) + minVal)));
		this.numVars = numVars;
		MIN_VAL = minVal;
		MAX_VAL = maxVal;
		decimalPlaces = -1;
	}
	
	public Expression( int numVars, double initConst, double minVal, double maxVal )
	{
		expression = new BinaryTree<ExpressionPart>(new Constant(initConst));
		this.numVars = numVars;
		MIN_VAL = minVal;
		MAX_VAL = maxVal;
		decimalPlaces = -1;
	}
	
	public Expression( int numVars, double initConst, double minVal, double maxVal, int roundTo )
	{
		expression = new BinaryTree<ExpressionPart>(new Constant(initConst));
		this.numVars = numVars;
		MIN_VAL = minVal;
		MAX_VAL = maxVal;
		decimalPlaces = roundTo;
	}
	
	public Expression( BinaryTree<ExpressionPart> initExpression, int numVars, double minVal, double maxVal )
	{
		expression = initExpression;
		this.numVars = numVars;
		MIN_VAL = minVal;
		MAX_VAL = maxVal;
		decimalPlaces = -1;
	}
	
	public Expression( BinaryTree<ExpressionPart> initExpression, int numVars, double minVal, double maxVal, int roundTo )
	{
		expression = initExpression;
		this.numVars = numVars;
		MIN_VAL = minVal;
		MAX_VAL = maxVal;
		decimalPlaces = -1;
	}
	
	// Copy constructor
	public Expression( Expression toCopy )
	{
		this
		(
			new BinaryTree<ExpressionPart>(toCopy.expression),
			toCopy.numVars,
			toCopy.MIN_VAL,
			toCopy.MAX_VAL,
			toCopy.decimalPlaces
		);
	}
	
	//------------------------------------
	//	[GENETIC] Cross two expressions
	//------------------------------------
	public static Expression cross(Expression e1, Expression e2)
	{
		return cross(e1, e2, 0.5f);
	}
	
	public static Expression cross(Expression e1, Expression e2, double bias)
	{
		BinaryTree<ExpressionPart> newExpTree_L = new BinaryTree<ExpressionPart>
		(
			new OperatorMultiply(),
			e1.expression,
			new Constant(1.0f - bias)
		);
		
		BinaryTree<ExpressionPart> newExpTree_R = new BinaryTree<ExpressionPart>
		(
			new OperatorMultiply(),
			e2.expression,
			new Constant(bias)
		);
		
		BinaryTree<ExpressionPart> newExpressionTree = new BinaryTree<ExpressionPart> 
		(
			new OperatorAdd(),
			newExpTree_L,
			newExpTree_R
		);
		
		return new Expression
		(
			newExpressionTree,
			Math.max(e1.numVars, e2.numVars),
			Math.min(e1.MIN_VAL, e2.MIN_VAL), Math.max(e1.MAX_VAL, e2.MAX_VAL)
		);
	}
	
	//------------------------------------
	//	[GENETIC] Mutate an expression (change it slightly)
	//------------------------------------
	public static Expression mutate(Expression e)
	{
		return mutate(e, 10);
	}
	
	public static Expression mutate(Expression exp, double constChange)
	{
		// Use copy constructor to make a result == exp
		Expression result = new Expression(exp);
		
		// Shift constants by a random amount
		for(int i = 0; i < result.expression.size(); i++)
		{
			if(result.expression.getValueAt(i).getType() == ExpressionPart.Type.CONSTANT)
			{
				double randChange = round2( (Math.abs(constChange) * (2 * Math.random() - 1)), decimalPlaces);
				double newVal = ((Constant) result.expression.getValueAt(i)).getValue() + randChange;
				result.expression.setValueAt( i, new Constant(newVal) );
			}
		}
		
		// Expand the expression between 0 - 1 times
		result.expand( (int) (Math.random() * 2) );
		
		return result;
	}
	
	//------------------------------------
	//	Evaluate the expression for given values of the variables
	//------------------------------------
	
	public double eval( double... vars )
	{
		return expression.getRoot().eval( vars, expression );
	}
	
	public void expand()
	{
		int randTreeIndex = (int) (Math.random() * expression.size());

		ArrayList<Operator> potOps = new ArrayList<Operator>();
		if(Math.random() < CHANCE_ADD) potOps.add(new OperatorAdd());
		if(Math.random() < CHANCE_SUB) potOps.add(new OperatorSubtract());
		if(Math.random() < CHANCE_MULT) potOps.add(new OperatorMultiply());
		if(Math.random() < CHANCE_DIV) potOps.add(new OperatorDivide());
		if(Math.random() < CHANCE_SIN) potOps.add(new OperatorSin());
		if(Math.random() < CHANCE_COS) potOps.add(new OperatorCos());
		if(Math.random() < CHANCE_TAN) potOps.add(new OperatorTan());
		if(Math.random() < CHANCE_POW) potOps.add(new OperatorPow());
		Operator newOp = (potOps.size() > 0 ? potOps.get((int) (Math.random() * potOps.size())) : new OperatorAdd());

		Operand rhs;
		if(Math.random() < 0.5)
		{
			// rhs is constant

			double rhsVal;

			// Protection against math errors
			switch(newOp.getType())
			{
				case OPERATOR_DIV:
					do { // N / 0
						rhsVal = MIN_VAL + Math.random() * (MAX_VAL - MIN_VAL);
					} while(round2(rhsVal, decimalPlaces) == 0);

					break;
				case OPERATOR_POW:
					if(expression.getValueAt(randTreeIndex).getType() == ExpressionPart.Type.CONSTANT && ((Constant) expression.getValueAt(randTreeIndex)).getValue() == 0) // 0 ^ -N
						rhsVal = Math.abs(MIN_VAL + Math.random() * (MAX_VAL - MIN_VAL));
					else
						rhsVal = MIN_VAL + Math.random() * (MAX_VAL - MIN_VAL);

					break;
				default:
					rhsVal = MIN_VAL + Math.random() * (MAX_VAL - MIN_VAL);
			}

			rhsVal = round2(rhsVal, decimalPlaces);
			rhs = new Constant(rhsVal);
		} else {
			// rhs is a variable
			rhs = new Variable((int)( Math.random() * numVars ));
		}
		
		
		expression.setTreeAt
		(
			randTreeIndex,
			(
				newOp.isUnary()
				?
				new BinaryTree<ExpressionPart>(newOp, expression.getTreeAt(randTreeIndex))
				:
				new BinaryTree<ExpressionPart>(newOp, expression.getTreeAt(randTreeIndex), rhs)
			)
		);
	}
	
	public void expand(int times)
	{
		for(int i = 0; i < times; i++)
			expand();
	}
	
	public void addAllVars()
	{
		for(int i = 0; i < numVars; i++)
		{
			ArrayList<Operator> potOps = new ArrayList<Operator>();
			if(Math.random() < CHANCE_ADD) potOps.add(new OperatorAdd());
			if(Math.random() < CHANCE_SUB) potOps.add(new OperatorSubtract());
			if(Math.random() < CHANCE_MULT) potOps.add(new OperatorMultiply());
			if(Math.random() < CHANCE_DIV) potOps.add(new OperatorDivide());
			Operator newOp = (potOps.size() > 0 ? potOps.get((int) (Math.random() * potOps.size())) : new OperatorAdd());
			Operand rhs = new Variable(i);
			
			int randTreeIndex = (int) (Math.random() * expression.size());
			expression.setTreeAt
			(
				randTreeIndex,
				new BinaryTree<ExpressionPart>(newOp, expression.getTreeAt(randTreeIndex), rhs)
			);
		}
	}
	
	// Set chances to have these in an expression
	public void setAddChance(double chance) { CHANCE_ADD = chance; }
	public void setSubChance(double chance) { CHANCE_SUB = chance; }
	public void setMultChance(double chance) { CHANCE_MULT = chance; }
	public void setDivChance(double chance) { CHANCE_DIV = chance; }
	public void setPowChance(double chance) { CHANCE_POW = chance; }
	public void setSinChance(double chance) { CHANCE_SIN = chance; }
	public void setCosChance(double chance) { CHANCE_COS = chance; }
	public void setTanChance(double chance) { CHANCE_TAN = chance; }
	
	// All-in-one convenience function
	public void setChances(double c_add, double c_sub, double c_mult, double c_div, double c_pow, double c_sin, double c_cos, double c_tan)
	{
		CHANCE_ADD = c_add;
		CHANCE_SUB = c_sub;
		CHANCE_MULT = c_mult;
		CHANCE_DIV = c_div;
		CHANCE_POW = c_pow;
		CHANCE_SIN = c_sin;
		CHANCE_COS = c_cos;
		CHANCE_TAN = c_tan;
	}
	
	//
	
	public void simplify()
	{
		System.out.println(expression);
		
		for(int i = 0; i < expression.size(); i++)
		{
			BinaryTree<ExpressionPart> ct = expression.getTreeAt(i);
			
			BinaryTree<ExpressionPart> prevExpression = new BinaryTree<ExpressionPart>(ct);

			switch(ct.getRoot().getType())
			{
				case OPERATOR_ADD:
					if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.OPERATOR_SUB )
					{
						if( ct.getLeft().getRight().equals(ct.getRight()) )
							// (N - E) + E, ROOT = ROOT.LEFT.LEFT
							expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getLeft().getLeft()));
					} else if(ct.getRight().getRoot().getType() == ExpressionPart.Type.OPERATOR_SUB) {
						if( ct.getLeft().equals(ct.getRight().getRight()) )
							// E + (N - E), ROOT = ROOT.RIGHT.LEFT
							expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getRight().getLeft()));
					} else if( ct.getLeft().equals(ct.getRight()) ) {
						BinaryTree<ExpressionPart> newTree = new BinaryTree<ExpressionPart>(
							new OperatorMultiply(),
							new BinaryTree<ExpressionPart>(new Constant(2)),
							new BinaryTree<ExpressionPart>(ct.getRight())
						);

						// E + E, ROOT = TREE(MULT, 2, ROOT.RIGHT)
						expression.setTreeAt(i, newTree);
					} else if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT ) {
						// C + C, ROOT = ROOT.LEFT + ROOT.RIGHT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(((Constant) ct.getLeft().getRoot()).getValue() + ((Constant) ct.getRight().getRoot()).getValue())));
					} else if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getLeft().getRoot()).getValue() == 0 ) {
						// 0 + N, ROOT = ROOT.RIGHT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getRight()));
					} else if( ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getRight().getRoot()).getValue() == 0 ) {
						// N + 0, ROOT = ROOT.LEFT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getLeft()));
					}
					break;
				case OPERATOR_SUB:
					if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.OPERATOR_ADD )
					{
						if( ct.getLeft().getLeft().equals(ct.getRight()) )
							// (N + E) - E, ROOT = ROOT.LEFT.RIGHT
							expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getLeft().getRight()));
						else if( ct.getLeft().getRight().equals(ct.getRight()) )
							// (E + N) - E, ROOT = ROOT.LEFT.LEFT
							expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getLeft().getLeft()));
					} else if( ct.getLeft().equals(ct.getRight()) ) {
						// E - E, ROOT = 0
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(0)));
					} else if(ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT) {
						// C - C, ROOT = ROOT.LEFT - ROOT.RIGHT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(((Constant) ct.getLeft().getRoot()).getValue() - ((Constant) ct.getRight().getRoot()).getValue())));
					} else if( ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getRight().getRoot()).getValue() == 0 ) {
						// N - 0, ROOT = ROOT.LEFT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getLeft()));
					}
					break;
				case OPERATOR_MULT:
					if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.OPERATOR_DIV )
					{
						if( ct.getRight().equals(ct.getLeft().getRight()) )
							// (N / E) * E, ROOT = ROOT.LEFT.LEFT
							expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getLeft().getLeft()));
					} else if(ct.getRight().getRoot().getType() == ExpressionPart.Type.OPERATOR_DIV) {
						if( ct.getLeft().equals(ct.getRight().getRight()) )
							// E * (N / E), ROOT = ROOT.RIGHT.LEFT
							expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getRight().getLeft()));
					} else if( ct.getLeft().equals(ct.getRight()) ) {
						BinaryTree<ExpressionPart> newTree = new BinaryTree<ExpressionPart>(
							new OperatorPow(),
							new BinaryTree<ExpressionPart>(ct.getLeft()),
							new BinaryTree<ExpressionPart>(new Constant(2))
						);

						// E * E, ROOT = TREE(POW, ROOT.LEFT, 2)
						expression.setTreeAt(i, newTree);
					} else if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT ) {
						// C * C, ROOT = ROOT.LEFT * ROOT.RIGHT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(((Constant) ct.getLeft().getRoot()).getValue() * ((Constant) ct.getRight().getRoot()).getValue())));
					} else if( (ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getLeft().getRoot()).getValue() == 0) || (ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getRight().getRoot()).getValue() == 0) ) {
						// N * 0, ROOT = 0
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(0)));
					} else if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getLeft().getRoot()).getValue() == 1 ) {
						// 1 * N, ROOT = ROOT.RIGHT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getRight()));
					} else if( ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getRight().getRoot()).getValue() == 1 ) {
						// N * 1, ROOT = ROOT.LEFT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getLeft()));
					}
					break;
				case OPERATOR_DIV:
					if( ct.getLeft().equals(ct.getRight()) ) {
						// E / E, ROOT = 1
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(1)));
					} else if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT ) {
						// C / C, ROOT = ROOT.LEFT / ROOT.RIGHT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(((Constant) ct.getLeft().getRoot()).getValue() / ((Constant) ct.getRight().getRoot()).getValue())));
					} else if( ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getRight().getRoot()).getValue() == 1 ) {
						// N / 1, ROOT = ROOT.LEFT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getLeft()));
					} else if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getLeft().getRoot()).getValue() == 0 ) {
						// 0 / N, ROOT = 0
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(0)));
					}
					break;
				case OPERATOR_POW:
					if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT ) {
						// C ^ C, ROOT = ROOT.LEFT ^ ROOT.RIGHT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(Math.pow(((Constant) ct.getLeft().getRoot()).getValue(), ((Constant) ct.getRight().getRoot()).getValue()))));
					} else if( ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getRight().getRoot()).getValue() == 0 ) {
						// N ^ 0, ROOT = 1
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(1)));
					} else if( ct.getRight().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getRight().getRoot()).getValue() == 1 ) {
						// N ^ 1, ROOT = ROOT.LEFT
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(ct.getLeft()));
					} else if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT && ((Constant) ct.getLeft().getRoot()).getValue() == 1 ) {
						// 1 ^ N, ROOT = 1
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(1)));
					}
					break;
				case OPERATOR_SIN:
					if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT ) {
						// SIN(C), ROOT = SIN(ROOT.LEFT)
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(Math.sin(((Constant) ct.getLeft().getRoot()).getValue()))));
					}
					break;
				case OPERATOR_COS:
					if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT ) {
						// COS(C), ROOT = COS(ROOT.LEFT)
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(Math.cos(((Constant) ct.getLeft().getRoot()).getValue()))));
					}
					break;
				case OPERATOR_TAN:
					if( ct.getLeft().getRoot().getType() == ExpressionPart.Type.CONSTANT ) {
						// TAN(C), ROOT = TAN(ROOT.LEFT)
						expression.setTreeAt(i, new BinaryTree<ExpressionPart>(new Constant(Math.tan(((Constant) ct.getLeft().getRoot()).getValue()))));
					}
					break;
			}

			i = !prevExpression.equals(expression.getTreeAt(i)) ? 0 : i;
		}

		System.out.println(expression);
	}
	
	//
	
	public String toString()
	{
		String result = getInfixExpression(expression);

		// Remove surrounding parenthesis if applicable
		if(result.startsWith("(") && result.endsWith(")"))
			result = result.substring(1, result.length() - 1);

		return result;
	}
	
	private String getInfixExpression(BinaryTree<ExpressionPart> tree)
	{
		String result = "";
		
		if(!tree.isEmpty())
		{
			if(tree.getRoot().isOperand())
				result += tree.getRoot();
			else
			{
				if( ((Operator) tree.getRoot()).isUnary() )
				{
					result += tree.getRoot() + "(";
					result += getInfixExpression(tree.getLeft());
					result += ")";
				} else {
					result += "(";
					result += getInfixExpression(tree.getLeft());
					result += " " + tree.getRoot() + " ";
					result += getInfixExpression(tree.getRight());
					result += ")";
				}
			}
		}

		return result;
	}
	
	public String toPrefixString()
	{
		return "" + expression;
	}
	
	private static double round2(double val, int places)
	{
		if(places >= 0)
			return Math.round(val * Math.pow(10, places)) /  Math.pow(10, places);
		else
			return val;
	}
	
	public void setDecimalPlaces(int places)
	{
		decimalPlaces = places;
	}

	public int getTreeSize()
	{
		return expression.size();
	}
}