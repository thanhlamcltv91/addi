package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**An external function which checks if the argument is numeric*/
public class isnumeric extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1)
			throwMathLibException("isNumeric: number of arguments != 1");
            
	    //if (!(operands[0] instanceof DoubleNumberToken)) 
        //	throwMathLibException("isNumeric: argument must be a number");
        // code above is useless: it prevents returning "0" for isnumeric("abc")
        
		if (operands[0] instanceof DoubleNumberToken) 	return new DoubleNumberToken(1.0);
		else										return new DoubleNumberToken(0.0);
	}
}

/*
@GROUP
general
@SYNTAX
answer = isnumeric(value)
@DOC
Returns 1 if the first operand is a number, else it returns 0.
@EXAMPLES
<programlisting>
isnumeric(2) = 1
isnumeric("test") = 0
</programlisting>
@NOTES
.
@SEE
ismatrix, isscalar, isprime, issquare
*/

