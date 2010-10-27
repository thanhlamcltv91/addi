package com.addi.toolbox.jmathlib.matrix;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.LogicalToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**An external function that return 1.0 is any element of the argument is nonzero  */
public class any extends ExternalFunction
{
	/**return a  matrix 
	* @param operands[0] = matrix 
	* @return 1.0 if any element of the argument is nonzero  */
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

		// two operands (e.g. not(A) )
        if (getNArgIn(operands) != 1)
			throwMathLibException("any: number of arguments != 1");

        // if boolean: convert LogicalToken to DoubleNumberToken
        if (operands[0] instanceof LogicalToken)
            operands[0]=((LogicalToken)operands[0]).getDoubleNumberToken(); 
        
        
        if (operands[0] instanceof DoubleNumberToken)
        {
            DoubleNumberToken num = (DoubleNumberToken)operands[0];
        
        	for (int i=0; i<num.getNumberOfElements(); i++)
        	{
    			if (num.getValueRe(i) != 0.0) 
                    return new DoubleNumberToken(1.0);
                if (num.getValueIm(i) != 0.0) 
                    return new DoubleNumberToken(1.0);
        	}	
        	return new DoubleNumberToken(0.0);	
        }

        throwMathLibException("any: works on numbers and booleans only");
        return null;
        
	} // end eval
}

/*
@GROUP
matrix
@SYNTAX
answer = any(matrix)
@DOC
Returns 1 if any of the elements of the supplied matrix are not zero.
@NOTES
@EXAMPLES
<programlisting>
any([0,0;0,0]) = 0
any([1,0;0,0]) = 1
</programlisting>
@SEE
and, eye, all
*/

