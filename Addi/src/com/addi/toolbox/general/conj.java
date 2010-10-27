package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


public class conj extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1 )
			throwMathLibException("conj: number of arguments !=1");
        
        if (!(operands[0] instanceof DoubleNumberToken))
            throwMathLibException("conj: only works on numbers");

		// create conjugate
		DoubleNumberToken conj     = (DoubleNumberToken)((DoubleNumberToken)operands[0]).conjugate();
        
        return conj;
        
	} // end eval
}

/*
@GROUP
general
@SYNTAX
conj(complex)
@DOC
Returns the conjugate of a complex number.
@EXAMPLES
<programlisting>
conj(5) = 5
conj(2 + i)  = 2 - i
</programlisting>
@SEE
imag, real, angle, abs
*/

