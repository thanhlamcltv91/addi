package com.addi.toolbox.general;

import com.addi.core.functions.*;
import com.addi.core.interpreter.Errors;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.*;


/**An external function to shift the bit pattern of a number*/
public class bitshift extends ExternalFunction
{
	/**Returns the number with it bit pattern shifted
	@param operands[0] - The number to shift
	@param operands[1] - The number of binary places to shift
	@return the result of the function as an OperandToken*/	
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
        DoubleNumberToken result = DoubleNumberToken.zero;
        if (getNArgIn(operands) != 2)
			throwMathLibException("BitShift: number of arguments != 2");

        if(operands[0] instanceof DoubleNumberToken)
        {
            if(operands[1] instanceof DoubleNumberToken)
            {
                int value1 = ((DoubleNumberToken)operands[0]).getIntValue(0,0);   
                int value2 = ((DoubleNumberToken)operands[1]).getIntValue(0,0);   
                
                if(value2 > 0) 
                    result = new DoubleNumberToken(value1 << value2);
                else
                    result = new DoubleNumberToken(value1 >> -value2);                    
            }
            else
			     Errors.throwMathLibException(ERR_INVALID_PARAMETER, new Object[] {"DoubleNumberToken", operands[1].getClass().getName()});
        }
		else
			Errors.throwMathLibException(ERR_INVALID_PARAMETER, new Object[] {"DoubleNumberToken", operands[0].getClass().getName()});

        return result;
	}
}

/*
@GROUP
general
@SYNTAX
bitshift(number1, number2);
@DOC
Shifts the bit pattern of number1 by number2 bits.
@EXAMPLES
<programlisting>
bitshift(5, 2) = 1
bitshift(5, -2) = 20
</programlisting>
@SEE
bitand, bitor, bitxor
*/
