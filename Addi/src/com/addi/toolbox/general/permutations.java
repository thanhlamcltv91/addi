package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**External function to calculate the number of permutations
when k objects are taken from a set of k*/
public class permutations extends ExternalFunction
{
	/**calculate the number of permutations
	@param operand[0] = The the number of objects taken
	@param operand[1] = The total number of objects
	@return the number of permutations
	*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		OperandToken result = new DoubleNumberToken(0);

        if (getNArgIn(operands) != 2)
			throwMathLibException("permutations: number of arguments != 2");

		if(operands.length >= 2 && operands[0] instanceof DoubleNumberToken && operands[1] instanceof DoubleNumberToken)
		{
			//perm(x y) = y!/(y-x)!
			DoubleNumberToken objects = ((DoubleNumberToken)operands[0]);
			DoubleNumberToken total   = ((DoubleNumberToken)operands[1]);
			
			//temp = y-x
			OperandToken temp = ((OperandToken)total.clone());
			temp = temp.subtract(objects);

			//temp = (y-x)!
			temp = temp.factorial();

			//result = y! / (y-x)!
			result = total.factorial();

			result = result.divide(temp);
		}
		return result;
	}
}

/*
@GROUP
general
@SYNTAX
answer = PERMUTATIONS(items, total)
@DOC
Returns the number of permutations when taking count items from a set of total items.
@EXAMPLES
PERMUTATIONS(3,5) = 60
PERMUTATIONS(3,6) = 120
@NOTES

@SEE
combinations

*/

