import shoffing.binarytree.*;
import shoffing.expression.*;

public class Driver
{
	public static void main(String[] args)
	{
		Expression exp;

		for(int i = 0; i < 1000; i++)
		{
			exp = new Expression(
				1,
				(int) (Math.random() * 10),
				0, 10,
				0
			);
	    	exp.setChances(0, 0, 0, 1, 1, 0, 0, 1);
			exp.addAllVars();
			exp.expand(5);
			System.out.println(("Unsimplified [" + exp.getTreeSize() + "]:\n\t" + exp).replace("V0", "x"));
			exp.simplify();
			System.out.println(("Simplified [" + exp.getTreeSize() + "]:\n\t" + exp).replace("V0", "x"));

			System.out.println();
		}

		//BinaryTree<ExpressionPart> bt, bt_c;
		//Expression exp;

		/*
		bt.setLeft(new BinaryTree<ExpressionPart>(bt));
		bt.setRight(new BinaryTree<ExpressionPart>(bt.getLeft()));
		*/

		/*
		// (3 - x) + x
		bt = new BinaryTree<ExpressionPart>(new OperatorAdd());

		bt_c = new BinaryTree<ExpressionPart>(new OperatorSubtract());
		bt_c.setLeft(new BinaryTree<ExpressionPart>(new Constant(3)));
		bt_c.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		bt.setLeft(bt_c);
		bt.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));


		System.out.println();


		// x + (3 - x)
		bt = new BinaryTree<ExpressionPart>(new OperatorAdd());

		bt_c = new BinaryTree<ExpressionPart>(new OperatorSubtract());
		bt_c.setLeft(new BinaryTree<ExpressionPart>(new Constant(3)));
		bt_c.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		bt.setLeft(new BinaryTree<ExpressionPart>(new Variable(0)));
		bt.setRight(bt_c);

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));


		System.out.println();
		

		// (x + 3) - x
		bt = new BinaryTree<ExpressionPart>(new OperatorSubtract());

		bt_c = new BinaryTree<ExpressionPart>(new OperatorAdd());
		bt_c.setLeft(new BinaryTree<ExpressionPart>(new Variable(0)));
		bt_c.setRight(new BinaryTree<ExpressionPart>(new Constant(3)));

		bt.setLeft(bt_c);
		bt.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));


		System.out.println();
		

		// (3 + x) - x
		bt = new BinaryTree<ExpressionPart>(new OperatorSubtract());

		bt_c = new BinaryTree<ExpressionPart>(new OperatorAdd());
		bt_c.setLeft(new BinaryTree<ExpressionPart>(new Constant(3)));
		bt_c.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		bt.setLeft(bt_c);
		bt.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));


		System.out.println();
		

		// x * (3 / x)
		bt = new BinaryTree<ExpressionPart>(new OperatorMultiply());

		bt_c = new BinaryTree<ExpressionPart>(new OperatorDivide());
		bt_c.setLeft(new BinaryTree<ExpressionPart>(new Constant(3)));
		bt_c.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		bt.setLeft(new BinaryTree<ExpressionPart>(new Variable(0)));
		bt.setRight(bt_c);

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));


		System.out.println();
		

		// (3 / x) * x
		bt = new BinaryTree<ExpressionPart>(new OperatorMultiply());

		bt_c = new BinaryTree<ExpressionPart>(new OperatorDivide());
		bt_c.setLeft(new BinaryTree<ExpressionPart>(new Constant(3)));
		bt_c.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		bt.setLeft(bt_c);
		bt.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));


		System.out.println();
		

		// x - x
		bt = new BinaryTree<ExpressionPart>(new OperatorSubtract());

		bt.setLeft(new BinaryTree<ExpressionPart>(new Variable(0)));
		bt.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));


		System.out.println();


		// x + x
		bt = new BinaryTree<ExpressionPart>(new OperatorAdd());

		bt.setLeft(new BinaryTree<ExpressionPart>(new Variable(0)));
		bt.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));


		System.out.println();


		// x * x
		bt = new BinaryTree<ExpressionPart>(new OperatorMultiply());

		bt.setLeft(new BinaryTree<ExpressionPart>(new Variable(0)));
		bt.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));


		System.out.println();


		// (x * x) + (x * x)
		bt = new BinaryTree<ExpressionPart>(new OperatorMultiply());

		bt.setLeft(new BinaryTree<ExpressionPart>(new Variable(0)));
		bt.setRight(new BinaryTree<ExpressionPart>(new Variable(0)));

		bt.setLeft(new BinaryTree<ExpressionPart>(bt));
		bt.setRight(new BinaryTree<ExpressionPart>(bt.getLeft()));

		bt.setRoot(new OperatorAdd());

		exp = new Expression(bt, 1, 0, 10);
		System.out.println(("Unsimplified:\t" + exp).replace("V0", "X"));
		exp.simplify();
		System.out.println(("Simplified:\t" + exp).replace("V0", "X"));
		*/
	}
}