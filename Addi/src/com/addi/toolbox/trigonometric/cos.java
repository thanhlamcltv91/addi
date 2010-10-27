package com.addi.toolbox.trigonometric;

import com.addi.core.functions.ExternalElementWiseFunction;

public class cos extends ExternalElementWiseFunction
{
    
    public cos()
    {
        name = "cos";
    }
    
    /**trigonometric functions - calculate the cosine of this token
     * @param array of a double value
     * @return the result as an OperandToken
     * */
    public double[] evaluateValue(double[] arg)
    {
        double result[] = new double[2];
        double scalar;
        double iz_re, iz_im;
        double _re1, _im1;
        double _re2, _im2;

        // iz:      i.Times(z) ...
        iz_re =  -arg[IMAG];
        iz_im =   arg[REAL];

        // _1:      iz.exp() ...
        scalar =  Math.exp(iz_re);
        _re1 =  scalar * Math.cos(iz_im);
        _im1 =  scalar * Math.sin(iz_im);

        // _2:      iz.neg().exp() ...
        scalar =  Math.exp(-iz_re);
        _re2 =  scalar * Math.cos(-iz_im);
        _im2 =  scalar * Math.sin(-iz_im);

        // _1:      _1.Plus(_2) ...
        _re1 = _re1 + _re2;                 // !!!
        _im1 = _im1 + _im2;                 // !!!

        // result:  _1.scale(0.5) ...
        result[REAL] = 0.5*_re1;
        result[IMAG] = -0.5*_im1;
        
        return result;
    }

}

/*
@GROUP
trigonometric
@SYNTAX
cos(angle)
@DOC
Returns the cosine of angle.
@EXAMPLES
cos(0) = 1
cos(1.5707963267948966) = 0
@SEE
acos, cosh
*/

