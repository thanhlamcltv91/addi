package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.*;


public class int64 extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1 )
			throwMathLibException("int64: number of arguments !=1");
        
        if (!(operands[0] instanceof DoubleNumberToken))
            throwMathLibException("int64: only works on numbers");

        DoubleNumberToken num = (DoubleNumberToken)operands[0];
        
        int[] size = num.getSize();
        
        int n = num.getNumberOfElements();
        
        Int64NumberToken int64 = new Int64NumberToken(size, null, null);

        double re  = 0;
        double im  = 0;
        long   reI = 0;
        long   imI = 0;
        for (int i=0; i<n; i++)
        {

            re = num.getValueRe(i);
            im = num.getValueIm(i);
                
            if (re > Long.MAX_VALUE)
                reI = Long.MAX_VALUE;
            else if (re < Long.MIN_VALUE)
                reI = Long.MIN_VALUE;
            else
                reI = (int)re;

            if (im > Long.MAX_VALUE)
                imI = Long.MAX_VALUE;
            else if (im < Long.MIN_VALUE)
                imI = Long.MIN_VALUE;
            else
                imI = (int)im;

            int64.setValue(i, reI, imI);
            
        }
        
        return int64;
        
	} // end eval
}

/*
@GROUP
general
@SYNTAX
int16(x)
@DOC
converts a double array into an array of int16 (range -32768=-2^25 up to +32767=+2^15-1)
@EXAMPLES
<programlisting>

</programlisting>
@SEE
double, int8, uint8, uint16
*/

/*
%!@testcase
%!  ml.executeExpression("a=int64(8);");
%!  ml.executeExpression("b=class(a);");
%!  assertEquals( "int64", ml.getString("b"));
%!
*/
