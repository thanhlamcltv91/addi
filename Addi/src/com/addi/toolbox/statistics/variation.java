package com.addi.toolbox.statistics;

import com.addi.core.functions.*;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;




/**
 * An external function for calculating the variation of
 */
public class variation extends ExternalFunction
{

    public OperandToken evaluate(Token[] operands, GlobalValues globals)
    {
        // Check if there's only one or zero parameters
        if (operands.length < 2)
        {
            // The variation of a number is 0
            return (OperandToken)(DoubleNumberToken.zero);
        }
        int i;
        OperandToken ot1 = (OperandToken) new DoubleNumberToken(0);
        OperandToken ot2 = (OperandToken) new DoubleNumberToken(0);
        OperandToken ot;

        for (i=0; i<operands.length; i++)
            {
                ot2.add((OperandToken)operands[i]);
                ot = (OperandToken)operands[i];
                ot.multiply((OperandToken)operands[i]);
                ot1.add(ot);
            }
        ot2.divide(new DoubleNumberToken(i));
        ot2.multiply(ot2);
        // Now ot2 is the square of the average of the parameters.
        ot1.divide(new DoubleNumberToken(i));
        // Now ot1 is the average of the squares of parameters

        return ot1.subtract(ot2);
    }
}



/*
@GROUP
statistics
@SYNTAX
Variation(1,2,3,...,n)
@DOC
Calculates the variation of the parameters.
@EXAMPLES
<programlisting>
Variation(0,x,y,z,3,t)
</programlisting>
@SEE
var, std
*/
