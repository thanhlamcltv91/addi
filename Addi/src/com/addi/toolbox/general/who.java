package com.addi.toolbox.general;

import java.util.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.*;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;

/**An external function for getting the stored variables*/
public class who extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

		//if (operands != null)
   		//    throwMathLibException("who requires no arguments");

		Variable var;

		globals.getInterpreter().displayText("\nYour variables are:\n");

		Iterator iter = globals.getLocalVariables().getIterator();
		while(iter.hasNext())
		{
		    Map.Entry next = ((Map.Entry)iter.next());
		    var = ((Variable)next.getValue());
		    globals.getInterpreter().displayText(var.getName());
		}

		return null;		
	}
}

/*
@GROUP
general
@SYNTAX
who
@DOC
Returns a list of all the variables in the system.
@EXAMPLES
<programlisting>
who 
ans 
x 
y
</programlisting>
@NOTES
@SEE
whos
*/

