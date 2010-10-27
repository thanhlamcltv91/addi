package com.addi.toolbox.general;

/* This file is part or JMATHLIB */

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**An external function for enabling/disabling of caching of p-files  */
public class getpfilecaching extends ExternalFunction
{
	/**status of caching of p-files 
	* @return whether or not caching of p-files is enabled/disabled 
    */
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		if (getNArgIn(operands)!=0)
			throwMathLibException("getPFileCaching: number of input arguments != 0");

		boolean cachingEnabled = globals.getFunctionManager().getPFileCaching();  	

		if (cachingEnabled)
		    return new DoubleNumberToken(1);
        else
            return new DoubleNumberToken(0);

	} // end eval
}


/*
@GROUP
General
@SYNTAX
x = getPFileCaching()
@DOC
Returns whether or not caching of p-files is enabled or disabled. 
Returns 1 if caching is enabled. 
Returns 0 if caching is disabled.
@EXAMPLES
x = getPFileCaching()
@NOTES
@SEE
setpfilecaching
*/
