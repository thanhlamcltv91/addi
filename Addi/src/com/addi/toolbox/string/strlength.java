package com.addi.toolbox.string;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.*;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.*;



/**An external function for returning the length of a string*/
public class strlength extends ExternalFunction
{
	/**Calculate the length of the string
	@param operands[0] the string to get the length for*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		OperandToken result = null;

        if (getNArgIn(operands) !=1)
            throwMathLibException("strlength: number of arguments !=1");
		
		if(operands[0] instanceof CharToken)
		{
			int length = ((CharToken)operands[0]).toString().length();
			result = new DoubleNumberToken(length);
		}
		else
			Errors.throwMathLibException(ERR_INVALID_PARAMETER, new Object[] {"CharToken", operands[0].getClass().getName()});
		
		return result;

	}
}

/*
@GROUP
char
@SYNTAX
length=STRLENGTH(string)
@DOC
Returns the length of a string.
@NOTES
@EXAMPLES
STRLENGTH("HELLO WORLD") = 11
@SEE
*/

