package com.addi.toolbox.trigonometric;

import com.addi.core.functions.ExternalElementWiseFunction;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


public class acot extends ExternalElementWiseFunction
{
    
    public acot()
    {
        name = "acot";
    }
    
    /**trigonometric functions -calculates the inverse cotangent
     * @param  double value array
     * @return the result as a double array
     */
    public double[] evaluateValue(double[] arg)
    {
        DoubleNumberToken num      = new DoubleNumberToken();
        atan        atanFunc = new atan();
        
        double[] temp   = num.divide(new double[]{1,0}, arg);
        
        double[] result = atanFunc.evaluateValue(temp);
        
        return result;
    }

}

/*
@GROUP
trigonometric
@SYNTAX
acot(value)
@DOC
calculates the inverse cotangent
@NOTES
.
@EXAMPLES
<programlisting>
y = acot(x)
</programlisting>
@SEE
sec, cot, csch, sech, coth
*/

