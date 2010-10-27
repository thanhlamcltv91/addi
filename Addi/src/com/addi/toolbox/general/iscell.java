package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**An external function which checks if the argument is numeric*/
public class iscell extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1)
			throwMathLibException("iscell: number of arguments != 1");
            
		if (operands[0] instanceof CellArrayToken) 	return new DoubleNumberToken(1.0);
		else										return new DoubleNumberToken(0.0);
	}
}

/*
@GROUP
general
@SYNTAX
answer = iscell(value)
@DOC
Returns 1 if the first operand is a cell array, else it returns 0.
@EXAMPLES
<programlisting>
a={4,5,'barfoo'}
iscell(a)=1 
iscell('hello')=0 
iscell([5,6,7])=0
</programlisting>
@NOTES
@SEE
ismatrix, isnumeric
*/

