package com.addi.toolbox.io;


import java.io.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;

public class loadvariables extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) > 1)
			throwMathLibException("loadvariables: number of arguments > 1");


        String file = "." + File.separator + "variables.mlf";
        
        if (getNArgIn(operands) == 1)
        {
            if (!(operands[0] instanceof CharToken)) 
                throwMathLibException("loadvariables: argument must be a string");

            file = ((CharToken)operands[0]).toString();
        }

        globals.getLocalVariables().loadVariables(file);
        
		return null;		

	} // end eval
}

/*
@GROUP
IO
@SYNTAX
loadvariables()
loadvariables("name.mlf")
@DOC
loads the variables from a serialized .mlf-file
@EXAMPLES
<programlisting>
loadvariables()
</programlisting>
@NOTES
The variables are written as a serialized stream of java objects. Therefor the
format of the .mlf-file is java-specific. The file is also specific to the
versions of MathLib token and classes.
@SEE
savevariables, load, dir, csvread, csvwrite
*/

