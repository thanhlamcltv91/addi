package com.addi.toolbox.io;


import java.io.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;

/**An external function used to execute an external program
in a seperate process*/
public class systemcommand extends ExternalFunction
{
	/**execute a program
	@param operands[0] = filename
	@param operands[n] = arguments
	@return 1*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		String[] cmdarray = new String[operands.length];
		
        if (getNArgIn(operands) < 1)
			throwMathLibException("SystemCommand: number of arguments > 0");

        
        for(int index = 0; index < operands.length; index++)
		{
			cmdarray[index] = operands[index].toString();
		}
		
		try
		{
			Runtime.getRuntime().exec(cmdarray);
		}
		catch(IOException exception)
		{
			throwMathLibException("SystemCommand");			
		}
		
		return null;
	}
}

/*
@GROUP
IO
@SYNTAX
systemcommand(command name)
@DOC
A function for running external commands
@EXAMPLES
<programlisting>
systemcommand("EMACS")
</programlisting>
@SEE
runfile
*/

