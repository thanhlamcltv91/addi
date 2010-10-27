package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.LogicalToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**An external function which checks if the argument is a char*/
public class islogical extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1)
			throwMathLibException("islogical: number of arguments != 1");
        
		if (operands[0] instanceof LogicalToken)  
            return DoubleNumberToken.one;
		else
            return DoubleNumberToken.zero;
	}
}

/*
@GROUP
general
@SYNTAX
answer = islogical(value)
@DOC
Returns 1 if the first operand is a logical array, else it returns 0.
@EXAMPLES
<programlisting>
a=logical([1,2,0])
islogical(a)  -> 1
</programlisting>
@NOTES
.
@SEE
ismatrix, isnumeric, isscalar, issquare
*/

