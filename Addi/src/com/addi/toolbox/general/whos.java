package com.addi.toolbox.general;

import java.util.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.*;
import com.addi.core.tokens.*;

/**An external function for getting the stored variables*/
public class whos extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
        
        Iterator iter = globals.getLocalVariables().getIterator();

        // display header information
        globals.getInterpreter().displayText("\nYour variables are:\n");
        globals.getInterpreter().displayText("\nName: \t size: \t type \n");

        // check if global context is requested
        if (getNArgIn(operands) == 1)
        {
            if ((operands[0] instanceof CharToken))
            {
                String data = ((CharToken)operands[0]).getValue();
                if (data.equals("global"))
                    iter = globals.getGlobalVariables().getIterator();
            }
        }

        // iterate through the complete local variable list
		while(iter.hasNext())
		{
		    Map.Entry    next   = ((Map.Entry)iter.next());
		    Variable     var    = (Variable)next.getValue();
		    OperandToken op     = (OperandToken)var.getData();
		    Boolean      global = var.isGlobal();
		    String       name   = var.getName();
		    String       line   = "";
            
            line = name + "      \t";
            
            // if variable is global get data from global context
            if (global)
                op = (OperandToken)globals.getGlobalVariables().getVariable(name).getData();
            
            // check which type of variable
            if (op instanceof DataToken)
            {
                line += getSizeString(op);
                line += "  \t "+ ((DataToken)op).getDataType()+" ";
            }
            else
            {
                line += "  \t unknown";
            }
            
            //if (getLocalVariables().isVariable(var.getName()))
            if (global)
                   line += "(global)";
            
            globals.getInterpreter().displayText(line);
		}

		return null;		
	}
    
    private String getSizeString(OperandToken tok)
    {
        int[] s = ((DataToken)tok).getSize();

        String line ="";
        
        // for tokens with no size return ""
        if (s==null)
            return "";
        
        // build up "size"-string
        for (int i=0; i< s.length; i++)
        {
            line += s[i];
            if (i<s.length-1)
                line +="x";
        }
        
        return line;
    }
    
}

/*
@GROUP
general
@SYNTAX
whos
whos("global")
@DOC
Returns a list of all the variables in the system.
@EXAMPLES
@NOTES
@SEE
who, clear, global
*/

