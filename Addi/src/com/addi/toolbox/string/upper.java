package com.addi.toolbox.string;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;


/**An external function for creating random numbers*/
public class upper extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

		// one operand
		if (operands[0]==null)  return null;

		// operand must be a string
		if (!(operands[0] instanceof CharToken)) return null;

		String data = ((CharToken)operands[0]).getValue().toUpperCase();
		
		return new CharToken(data);		
	}
}

/*
@GROUP
char
@SYNTAX
answer = upper(string)
@DOC
Converts a string to upper case.
@NOTES
.
@EXAMPLES
.
@SEE
lower
*/

