package com.addi.toolbox.elfun;

import com.addi.core.functions.ExternalElementWiseFunction;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


public class tan extends ExternalElementWiseFunction
{
    
    public tan()
    {
        name = "tan";
    }
    
    /**trigonometric functions - calculate the tangent of this token
    @return the result as an OperandToken*/
    public double[] evaluateValue(double[] arg)
    {
        double[] temp1 = sin(arg);
        double[] temp2 = cos(arg);
        
        DoubleNumberToken t = new DoubleNumberToken();
        double[] result = t.divide(temp1, temp2);

        return result;
    }

    public double[] sin(double[] arg)
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

        // _1:      _1.Minus(_2) ...
        _re1 = _re1 - _re2;                       // !!!
        _im1 = _im1 - _im2;                       // !!!

        // result:  _1.Div(2*i) ...
        result[REAL] =  0.5*_im1;
        result[IMAG] = -0.5*_re1;
        
        return result;
    }

    public double[] cos(double[] arg)
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
answer = tan(angle)
@DOC
Returns the tangent of angle.
@EXAMPLES
tan(0) = 0
tan(1) = 1.55740772
@SEE
atan, tanh
*/

