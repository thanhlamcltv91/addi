package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.FunctionHandleToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**External function to check if an argument is of type function hanlde*/
public class isfunctionhandle extends ExternalFunction
{
	/**@param operands[0] = a matrix of numbers
	@return a matrix the same size with 1 if the number is a prime*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
        if (getNArgIn(operands) != 1)
			throwMathLibException("isfunctionhandle: number of arguments != 1");
            
	    if (operands[0] instanceof FunctionHandleToken) 
	        return new DoubleNumberToken(1.0);
	    else
            return new DoubleNumberToken(0.0);

	}
	
}

/*
@GROUP
general
@SYNTAX
answer=isfunctionhandle(value)
@DOC
Checks if value is a function handle. Returning 1 if it is.
@EXAMPLES
<programlisting>
isfunctionhandle( @sin ) -> 1
</programlisting>
@NOTES
@SEE
isa
*/

