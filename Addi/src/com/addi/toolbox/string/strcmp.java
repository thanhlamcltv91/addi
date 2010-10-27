package com.addi.toolbox.string;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.*;


/**An external function for comparing two strings*/
public class strcmp extends ExternalFunction
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
				String string1 = ((CharToken)operands[0]).toString();
				String string2 = ((CharToken)operands[1]).toString();
				
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
STRCMP(string1, string2)
@DOC
Compares string1 to string2.
@NOTES
@EXAMPLES
STRCMP("ABcd", "abce")
0
STRCMP("ABc", "abc")
0
STRCMP("abc", "abc")
1
@SEE
strcmpi, strncmpi, strncmp
*/

