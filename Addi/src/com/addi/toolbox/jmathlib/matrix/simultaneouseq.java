package com.addi.toolbox.jmathlib.matrix;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;


/**An external function for determining the determinant of a matrix*/
public class simultaneouseq extends ExternalFunction
{
	/**Calculates the solution of a set of simultaneous equations
	It uses the InverseMatrix class
	operands[0] = m*m matrix of co-efficients
	operands[1] = m*1 matrix of sums
	result      = m*1 matrix of solutions*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		inversematrix inverse = new inversematrix();
		
		OperandToken inv = inverse.evaluate(new Token[] {operands[0]}, globals);
		
		Expression exp = new Expression(new MulDivOperatorToken('*'), ((OperandToken)operands[1]), inv);
		
		return exp.evaluate(null, globals);
	}
}

/*
@GROUP
matrix
@SYNTAX
solution = SIMULTANEOUSEQ(square matrix, results)
@DOC
Calculates the solution to a series of simultaneous equations.
@NOTES
@EXAMPLES
equations
2x+3y=7
5x+y=11

SIMULTANEOUSEQ([2,3;5,1],[7,11]) = [2;1]
@SEE

*/

