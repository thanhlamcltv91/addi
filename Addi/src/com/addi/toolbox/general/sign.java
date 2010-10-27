package com.addi.toolbox.general;

import com.addi.core.functions.ExternalElementWiseFunction;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;
import com.addi.toolbox.jmathlib.matrix.abs;


/**External function to calculate sign of a number
for a matrix it returns a matrix of equal size
for a complex number x sign = x/abs x
*/
public class sign extends ExternalElementWiseFunction
{
	/**Calculate the sign
	@param operands[0] = value to calculate sign of
	@return a matrix of the same size as the operands*/
    public double[] evaluateValue(double[] arg)
	{
        double[] result = new double[2];

        if (arg[IMAG]==0)
        {
            if (arg[REAL]>0)
            {
                result[REAL] = 1.0;
            }
            else if (arg[REAL]<0)
            {
                result[REAL] = -1.0;
            }
            else
                result[IMAG] = 0;
        }
        else
        {
            // for complex number sign is determined 
            // like sign(X) = X./abs(X)
            abs         absFunc = new abs();
            DoubleNumberToken num     = new DoubleNumberToken();

            result = absFunc.evaluateValue(arg);
            result = num.divide(arg, result);
        }

        return  result;                      
	}	
}

/*
@GROUP
general
@SYNTAX
sign(value)
@DOC
Returns the sign of value.
@EXAMPLES
<programlisting>
sign(-10)  = -1
sign(10)   = 1
sign(3+2i) = 0.832 + 0.555i 
</programlisting>
@NOTES
The sign of a complex number is calculated as sign(x) = x./abs(x)
@SEE
abs
*/

/*
%!@testcase
%!  ml.executeExpression("a=sign(11);");
%!  assertTrue(1 == ml.getScalarValueRe("a"));
%!  assertTrue(0 == ml.getScalarValueIm("a"));
%!
%!@testcase
%!  ml.executeExpression("a=sign(-12);");
%!  assertTrue(-1 == ml.getScalarValueRe("a"));
%!  assertTrue(0  == ml.getScalarValueIm("a"));
%!
%!@testcase
%!  ml.executeExpression("a=sign(0);");
%!  assertTrue(0 == ml.getScalarValueRe("a"));
%!  assertTrue(0 == ml.getScalarValueIm("a"));
%!
%!
%!@testcase
%!  ml.executeExpression("a=sign(8i);");
%!  assertTrue(0 == ml.getScalarValueRe("a"));
%!  assertTrue(1 == ml.getScalarValueIm("a"));
%!
%!@testcase
%!  ml.executeExpression("a=sign(-5i);");
%!  assertTrue(0  == ml.getScalarValueRe("a"));
%!  assertTrue(-1 == ml.getScalarValueIm("a"));
%!
%!@testcase
%!  ml.executeExpression("a=sign(0i);");
%!  assertTrue(0 == ml.getScalarValueRe("a"));
%!  assertTrue(0 == ml.getScalarValueIm("a"));
%!
*/
