package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.MathLibObject;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**An external function which checks if the argument is a struct*/
public class isstruct extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1)
			throwMathLibException("isstruct: number of arguments != 1");
        
		if (operands[0] instanceof MathLibObject) 	return DoubleNumberToken.one;
		else										return DoubleNumberToken.zero;
	}
}

/*
@GROUP
general
@SYNTAX
answer = isstruct(value)
@DOC
Returns 1 if the first operand is a struct, else it returns 0.
@EXAMPLES
<programlisting>
bar.foo=7
isstruct(bar) returns 1 
isstruct(55)  returns 0
</programlisting>
@NOTES
@SEE
ismatrix, isnumeric, isscalar, issquare
*/

