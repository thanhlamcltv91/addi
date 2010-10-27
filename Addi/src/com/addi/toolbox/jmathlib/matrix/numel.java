package com.addi.toolbox.jmathlib.matrix;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.DataToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**number of elements  */
public class numel extends ExternalFunction
{
	/**  */
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

		// two operands (e.g. not(A) )
        if (getNArgIn(operands) != 1)
			throwMathLibException("numel: number of arguments != 1");
        
        if (!(operands[0] instanceof DataToken))
            throwMathLibException("numel: no data token");

        int x = ((DataToken)operands[0]).getSizeX();
        int y = ((DataToken)operands[0]).getSizeY();
        
		return new DoubleNumberToken(x*y);		

	} // end eval
}

/*
@GROUP
matrix
@SYNTAX
numel(array)
@DOC
returns the number of elements in array
@NOTES
@EXAMPLES
numel([2,5,3]) -> 3
@SEE
*/

