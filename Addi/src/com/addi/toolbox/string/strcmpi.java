package com.addi.toolbox.string;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.*;


/**An external function for comparing two strings*/
public class strcmpi extends ExternalFunction
{
	/**compares two strings
	@param operands[0] = first string
	@param operands[1] = second string*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		int result = 0;
		
		if(operands[0] instanceof CharToken)
		{
			if(operands[1] instanceof CharToken)
			{
				String string1 = ((CharToken)operands[0]).toString().toUpperCase();
				String string2 = ((CharToken)operands[1]).toString().toUpperCase();
				
				if(string1.equals(string2))
					result = 1;
			}
		}		
		
		return new DoubleNumberToken(result);
	}
}

/*
@GROUP
char
@SYNTAX
STRCMPI(string1, string2)
@DOC
Compares string1 to string2, ignoring case.
@NOTES
@EXAMPLES
STRCMPI("ABcd", "abce")
0
STRCMPI("ABc", "abc")
1
@SEE
strcmp, strncmpi, strncmp
*/

