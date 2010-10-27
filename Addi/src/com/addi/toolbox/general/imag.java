package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


public class imag extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1 )
			throwMathLibException("imag: number of arguments !=1");
        
        if (!(operands[0] instanceof DoubleNumberToken))
            throwMathLibException("imag: only works on numbers");

		// get data from arguments
		double[][] a_i     = ((DoubleNumberToken)operands[0]).getValuesIm();
        
        return new DoubleNumberToken(a_i);
        
	} // end eval
}

/*
@GROUP
general
@SYNTAX
imag(complex)
@DOC
Returns the imaginary part of a complex number
@EXAMPLES
<programlisting>
imag(2i) = 2
imag(5 + 3i) = 3
</programlisting>
@SEE
real, conj, angle, abs
*/

