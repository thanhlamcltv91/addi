package com.addi.toolbox.trigonometric;

import com.addi.core.functions.ExternalElementWiseFunction;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


public class cot extends ExternalElementWiseFunction
{
    
    public cot()
    {
        name = "cot";
    }
    
    /**trigonometric functions - calculate the cotangent of this token
     * @param  double value
     * @return the result as a double array
     */
    public double[] evaluateValue(double[] arg)
    {

        DoubleNumberToken num     = new DoubleNumberToken();
        tan         tanFunc = new tan();
        
        double[] temp = tanFunc.evaluateValue(arg);

        
        double[] result = num.divide(new double[]{1,0}, temp);
        
        
        return result;
    }


}

/*
@GROUP
trigonometric
@SYNTAX
cot(value)
@DOC
.
@NOTES
@EXAMPLES
.
@SEE
sec, csc, csch, sech, coth
*/

