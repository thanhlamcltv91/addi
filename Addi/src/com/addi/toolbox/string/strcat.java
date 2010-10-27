package com.addi.toolbox.string;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;


/**An external function for concatenating strings*/
public class strcat extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		String result = "";
		
		for(int index = 0; index < operands.length; index++)
		{
			result += operands[index].toString().trim();
		}
		
		return new CharToken(result);
	}
}

/*
@GROUP
char
@SYNTAX
STRCAT(string1, string2, ...)
@DOC
Concatenates a set of strings together.
@NOTES
@EXAMPLES
STRCAT("Hello", "World")
HelloWorld
@SEE
strvcat
*/

