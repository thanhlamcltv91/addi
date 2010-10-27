package com.addi.toolbox.io;


import java.io.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;

public class ishidden extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1)
			throwMathLibException("isthidden: number of arguments != 1");

		if (!(operands[0] instanceof CharToken)) 
            throwMathLibException("ishidden: argument must be a string");

        String name = ((CharToken)operands[0]).toString();

		try
		{
			File file;
			if (name.startsWith("/")) {
				file = new File(name);
			} else {
				file = new File(globals.getWorkingDirectory(), name);
			}

            if (!file.exists())
                throwMathLibException("ishidden: file does not exist");
            
			if (file.isHidden())
			    return new DoubleNumberToken(1);
            else
                return new DoubleNumberToken(0);
		}
		catch (Exception e)
		{
            throwMathLibException("ishidden: IO exception");
		}		    

		return null;		

	} // end eval
}

/*
@GROUP
IO
@SYNTAX
ishidden(filename)
@DOC
Check the file attribute of filename and returns if the file is a hidden file.
@EXAMPLES
<programlisting>
ishidden("bar.txt");
</programlisting>
@NOTES
@SEE
cd, createnewfile, dir, exist, mkdir, rmdir, delete, isfile, isdirectory, lastmodified
*/

