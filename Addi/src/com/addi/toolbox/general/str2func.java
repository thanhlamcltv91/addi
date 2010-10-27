package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.FunctionHandleToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;


/**External function */
public class str2func extends ExternalFunction
{
	/**@param operands[0] = a matrix of numbers
	@return a matrix the same size with 1 if the number is a prime*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
        if (getNArgIn(operands) != 1)
			throwMathLibException("str2func: number of arguments != 1");
            
	    if (!(operands[0] instanceof CharToken)) 
            throwMathLibException("str2func: argument no char");

	    String s = ((CharToken)operands[0]).getValue();
	    
	    return new FunctionHandleToken(s);
	    

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

