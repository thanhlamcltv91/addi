package com.addi.toolbox.trigonometric;

import com.addi.core.functions.ExternalElementWiseFunction;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


public class asech extends ExternalElementWiseFunction
{
    
    public asech()
    {
        name = "asech";
    }
    
    /**trigonometric functions -calculates the inverse hperbolic secant
     * @param  double value array
     * @return the result as a double array
     */
    public double[] evaluateValue(double[] arg)
    {
        DoubleNumberToken num       = new DoubleNumberToken();
        acosh       acoshFunc = new acosh();
        
        double[] temp   = num.divide(new double[]{1,0}, arg);
        
        double[] result = acoshFunc.evaluateValue(temp);
        
        return result;
    }

}

/*
@GROUP
trigonometric
@SYNTAX
asech(value)
@DOC
calculates the inverse hperbolic secant
@NOTES
@EXAMPLES
<programlisting>
y = asech(x)
</programlisting>
@SEE
sec, cot, csch, sech, coth
*/

