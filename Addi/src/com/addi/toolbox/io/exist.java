package com.addi.toolbox.io;


import java.io.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;

/**An external function used to check if a file exists*/
public class exist extends ExternalFunction
{
	/**Check if file exists
	@param 0 = filename
	@return 1 if the file exists*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
    
    	// at least one operand
        if (getNArgIn(operands) != 1)
			throwMathLibException("exist: number of arguments != 1");

    
		String fileName = operands[0].toString();
		File testFile = null;
		if((fileName.indexOf(":") > -1))
			testFile = new File(fileName);
		else
		{
			if (fileName.startsWith("/")) {
				testFile = new File(fileName);
			} else {
				testFile = new File(globals.getWorkingDirectory(), fileName);
			}
		}
		
		OperandToken result = null;
		
		if(testFile.exists())
			result = new DoubleNumberToken(1);
		else
			result = new DoubleNumberToken(0);
			
		return result;
	}
}

/*
@GROUP
IO
@SYNTAX
exist(filename)
@DOC
Checks if a file exists
@EXAMPLES
<programlisting>
exist("bar.txt")
</programlisting>
@SEE
cd, createnewfile, dir, mkdir, rmdir, delete, isfile, isdirectory, ishidden, lastmodified
*/

