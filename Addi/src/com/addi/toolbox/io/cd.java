package com.addi.toolbox.io;


import java.io.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.*;
import com.addi.core.tokens.*;

/**An external function for changing to another directory         */
public class cd extends ExternalFunction
{

    /* @param operands[0] string which specifies the directory               *
     *   to change to (optional).                                            *
	 *   If invoked with now paramenter the current directory is returned.   */
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

		String path=".";

		// at least one operand
        if (getNArgIn(operands) > 1)
			throwMathLibException("cd: number of arguments > 1");

        if (getNArgIn(operands) == 1)
        {
    		// check if a directory is specified
    		if ((operands[0] instanceof CharToken)) 
    		{
    			path = ((CharToken)operands[0]).toString();
    		}
    		else
            	throwMathLibException("cd: argument must be a string");
        }
        
		try
		{
			File dir;
			if (path.startsWith("/")) {
				dir = new File(path);
			} else {
			    dir = new File(globals.getWorkingDirectory(), path);
			}
			//getInterpreter().displayText("canonical path = "+dir.getCanonicalPath());		

			if (dir.isDirectory())
			{
			    globals.setWorkingDirectory(dir);		

				if (getNoOfLeftHandArguments()==1)
					return new CharToken(dir.getCanonicalPath());
				else 
				    globals.getInterpreter().displayText(dir.getCanonicalPath());

			}
		}
		catch (Exception e)
		{
			ErrorLogger.debugLine("cd: IO exception");
		}		    

		return null;		

	} // end eval
}

/*
@GROUP
IO
@SYNTAX
cd(directory)
@DOC
Sets the working directory to directory. Also switches between directories.
@EXAMPLES
<programlisting>
cd("C:\barfoo");
</programlisting>
@SEE
dir, cd, isdirectory
*/

