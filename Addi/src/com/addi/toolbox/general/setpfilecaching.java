package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**An external function for enabling/disabling of caching of p-files  */
public class setpfilecaching extends ExternalFunction
{
	/**enable or disable caching of p-files 
	* @param operands[0] 1, 0 , 'on', 'off' 
    */
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		// one operand (e.g. setPFileCaching(1)
		// one operand (e.g. setPFileCaching('on')
		
		if (getNArgIn(operands)!=1)
			throwMathLibException("setPFileCaching: number of input arguments != 1");

		if (operands[0] instanceof DoubleNumberToken)
		{
			if ( ((DoubleNumberToken)operands[0]).getValueRe()==0)
			    globals.getFunctionManager().setPFileCaching(false);
			else
			    globals.getFunctionManager().setPFileCaching(true);  	
		}
		else if (operands[0] instanceof CharToken)
		{
			if ( ((CharToken)operands[0]).getValue().equals("on"))
			    globals.getFunctionManager().setPFileCaching(true);
			else
			    globals.getFunctionManager().setPFileCaching(false);  	
		}
       
		return null;

	} // end eval
}


/*
@GROUP
General
@SYNTAX
setPFileCaching(value)
@DOC
enables or disables caching of p-files
@EXAMPLES
<programlisting>
setPFileCaching(1)      enable caching 

setPFileCaching('on')   enable caching 

setPFileCaching(0)      disable caching 

setPFileCaching('off')  disable caching 
</programlisting>
@NOTES
@SEE
getpfilecaching
*/
