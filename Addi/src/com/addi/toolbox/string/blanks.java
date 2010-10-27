package com.addi.toolbox.string;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.*;


/**An external function for changing numbers into strings*/
public class blanks extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		OperandToken result = null;
		
        if (getNArgIn(operands) != 1 )
            throwMathLibException("blanks: number of arguments !=1");
        
        if (!(operands[0] instanceof DoubleNumberToken))
            throwMathLibException("blanks: only works on numbers");
            
		int length = (new Double(((DoubleNumberToken)operands[0]).getValueRe())).intValue();
		
		StringBuffer buffer = new StringBuffer(length);
		
		for(int index = 0; index < length; index++)
		{
			buffer.append(' ');
		}
		String temp = new String(buffer);
		
		result = new CharToken(temp);

		return result;	
	}
}

/*
@GROUP
char
@SYNTAX
BLANKS(number)
@DOC
Outputs a number of spaces equal to number.
@NOTES
@EXAMPLES
"-" + BLANKS(5) + "-" = "-     -"
@SEE
deblank
*/

