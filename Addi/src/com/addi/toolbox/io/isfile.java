package com.addi.toolbox.io;


import java.io.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;

public class isfile extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1)
			throwMathLibException("isfile: number of arguments != 1");

		if (!(operands[0] instanceof CharToken)) 
            throwMathLibException("isfile: argument must be a string");

        String name = ((CharToken)operands[0]).toString();

		try
		{
			File file;
			if (name.startsWith("/")) {
				file = new File(name);
			} else {
				file = new File(globals.getWorkingDirectory(), name);
			}

			if (file.isFile())
			    return new DoubleNumberToken(1);
            else
                return new DoubleNumberToken(0);
		}
		catch (Exception e)
		{
            throwMathLibException("isfile: IO exception");
		}		    

		return null;		

	} // end eval
}

/*
@GROUP
IO
@SYNTAX
isfile(filename)
@DOC
Checks if filename is a valid file in the current working directory.
@EXAMPLES
<programlisting>
isfile("bar.txt");
</programlisting>
@NOTES
@SEE
cd, createnewfile, dir, exist, mkdir, rmdir, delete, isdirectory, ishidden, lastmodified
*/

