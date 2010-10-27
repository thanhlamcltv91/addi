package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.*;


public class angle extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1 )
			throwMathLibException("angle: number of arguments !=1");
        
        if (!(operands[0] instanceof DoubleNumberToken))
            throwMathLibException("angle: only works on numbers");

		// get data from arguments
        double[][] a_r     = ((DoubleNumberToken)operands[0]).getValuesRe();
		double[][] a_i     = ((DoubleNumberToken)operands[0]).getValuesIm();
		int        dy      = ((DoubleNumberToken)operands[0]).getSizeY();
        int        dx      = ((DoubleNumberToken)operands[0]).getSizeX();
        double[][] ret     = new double[dy][dx];

        for (int y=0; y<dy ; y++)
        {
            for (int x=0; x<dx ; x++)
            {
                if ((a_r[y][x]<0) && (a_i[y][x]>=0))
                {
                    ret[y][x] = Math.atan( a_i[y][x] / a_r[y][x]) + Math.PI;
                }
                else if ((a_r[y][x]<0) && (a_i[y][x]<0))
                {
                    ret[y][x] = Math.atan( a_i[y][x] / a_r[y][x]) - Math.PI;
                }
                else
                {
                    ret[y][x] = Math.atan( a_i[y][x] / a_r[y][x]);
                }
            }
        }   

        return new DoubleNumberToken(ret);
        
	} // end eval
}

/*
@GROUP
general
@SYNTAX
angle(complex)
@DOC
Returns the angle of a complex number
@EXAMPLES
<programlisting>
angle(2i) = pi/2
real(1 + i) = pi/4
</programlisting>
@SEE
real, conj, imag, abs
*/

