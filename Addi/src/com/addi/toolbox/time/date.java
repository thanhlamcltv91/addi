package com.addi.toolbox.time;

/* This file is part or JMATHLIB */

import java.util.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;


/*
author: Stefan Mueller (stefan@held-mueller.de) 2002
*/


/**An external function for computing a mesh of a matrix  */
public class date extends ExternalFunction
{
	/**returns a string 
	* @return the current date as a string */
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

		Calendar date = Calendar.getInstance();
        
		return new CharToken( date.toString() );

	} // end eval
}


/*
@GROUP
time
@SYNTAX
data = date()
@DOC
Returns the current date.
@EXAMPLES
<programlisting>
d=date()
returns 
d= 7-7-2002
</programlisting>
@NOTES
@SEE
tic, toc, pause, time
*/
