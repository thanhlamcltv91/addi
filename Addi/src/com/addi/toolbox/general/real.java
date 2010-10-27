package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


public class real extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1 )
			throwMathLibException("real: number of arguments !=1");
        
        if (!(operands[0] instanceof DoubleNumberToken))
            throwMathLibException("real: only works on numbers");


		// get data from arguments
		double[][] a_r     = ((DoubleNumberToken)operands[0]).getReValues();
        
        return new DoubleNumberToken(a_r);
        
	} // end eval
}

/*
@GROUP
general
@SYNTAX
real(complex)
@DOC
Returns the real part of a complex number
@EXAMPLES
<programlisting>
real(2) = 2
real(5 + 3i) = 5
</programlisting>
@SEE
imag, conj, angle, abs
*/


