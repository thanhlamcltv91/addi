package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**An external function which checks if the argument is a char*/
public class ischar extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1)
			throwMathLibException("ischar: number of arguments != 1");
        
		if (operands[0] instanceof CharToken)   return DoubleNumberToken.one;
		else                                    return DoubleNumberToken.zero;
	}
}

/*
@GROUP
general
@SYNTAX
answer = ischar(value)
@DOC
Returns 1 if the first operand is a struct, else it returns 0.
@EXAMPLES
<programlisting>
ischar("hello") returns 1 
ischar(55)  returns 0
</programlisting>
@NOTES
.
@SEE
ismatrix, isnumeric, isscalar, issquare
*/

