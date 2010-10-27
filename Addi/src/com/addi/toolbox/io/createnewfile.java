package com.addi.toolbox.io;


import java.io.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;

public class createnewfile extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1)
			throwMathLibException("createnewfile: number of arguments != 1");

		if (!(operands[0] instanceof CharToken)) 
            throwMathLibException("createnewfile: argument must be a string");

        String name = ((CharToken)operands[0]).toString();

        File file=null;

        try
		{
			if (name.startsWith("/")) {
				file = new File(name);
			} else {
				file = new File(globals.getWorkingDirectory(),name);
			}

            if(!file.createNewFile())
                throwMathLibException("create new file: did not work");
		}
		catch (Exception e)
		{
            throwMathLibException("create new file: exception");
		}		    

		return null;		

	} // end eval
}

/*
@GROUP
IO
@SYNTAX
createnewfile(filename)
@DOC
use this script to create a new file.
@EXAMPLES
<programlisting>
createnewfile("bar.txt");
</programlisting>
@SEE
cd, dir, exist, mkdir, rmdir, delete, isfile, isdirectory, ishidden, lastmodified
*/

