package com.addi.toolbox.general;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileOutputStream;

import android.content.Context;
import android.content.Context.*;

import com.addi.core.functions.*;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.interpreter.Interpreter;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;

/**
 * An external function for sending data to AddiPlot
 */
public class ed extends ExternalFunction
{

    public OperandToken evaluate(Token[] operands, GlobalValues globals)
    {
    	String fileName = "";
    	
    	if (operands.length > 1)
    		throwMathLibException("ed: number of arguements > 1, only supports editing 1 file at a time");
    	
    	if (operands.length == 0)
    		throwMathLibException("ed: no file specified");
    	
        // Check if there's only parameters
    	if (getNArgIn(operands) == 1)
        {
    		// check if a file name is specified
    		if ((operands[0] instanceof CharToken)) 
    		{
    			fileName = ((CharToken)operands[0]).toString();
    			
    			if (fileName.endsWith(".m")) {
    				if (fileName.startsWith("/")) {
    					fileName = fileName;
    				} else {
    					fileName = globals.getWorkingDirectory() + "/" + fileName;
    				}
    			} else {
    				throwMathLibException("filename must end with .m");
    			}
    		}
    		else
    		{
            	throwMathLibException("ed: argument must be a string filename");
    		}
    	}
		globals.getInterpreter().displayText("STARTUPADDIEDITWITH="+fileName);
        
        return null;
    }
}

/*
@GROUP
general
@SYNTAX
ed(filename)
@DOC
Opens up window for editing file of name specified
@EXAMPLES
<programlisting>
ed("temp.txt")
</programlisting>
@SEE
ed
*/