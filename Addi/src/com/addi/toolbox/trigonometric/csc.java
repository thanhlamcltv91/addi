package com.addi.toolbox.trigonometric;

import com.addi.core.functions.ExternalElementWiseFunction;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


public class csc extends ExternalElementWiseFunction
{
    
    public csc()
    {
        name = "csc";
    }
    
    /**trigonometric functions - calculate the cosine of this token
    @param  double value
    @return the result as an OperandToken
    */
    public double[] evaluateValue(double[] arg)
    {

        DoubleNumberToken num     = new DoubleNumberToken();
        sin         sinFunc = new sin();
        
        double[] temp = sinFunc.evaluateValue(arg);

        
        double[] result = num.divide(new double[]{1,0}, temp);
        
        
        return result;
    }


}

/*
@GROUP
trigonometric
@SYNTAX
csc(value)
@DOC
.
@NOTES
@EXAMPLES
.
@SEE
sec, cot, csch, sech, coth
*/

