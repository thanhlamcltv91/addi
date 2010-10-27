package com.addi.toolbox.string;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.*;


/**An external function for changing strings into numbers */
public class str2num extends ExternalFunction
{
    /**returns a matrix of numbers 
    * @param operands[0] = string (e.g. ["hello"]) 
    * @return a matrix of numbers                                */
    public OperandToken evaluate(Token[] operands, GlobalValues globals)
    {

        // one operand 
        if (getNArgIn(operands)!=1)
            throwMathLibException("str2num: number of input arguments != 1");

        if ( !(operands[0] instanceof CharToken))
            throwMathLibException("str2num: works only on strings");

        // get data from arguments
        String stringValue = ((CharToken)operands[0]).getValue();

        // convert string to array of bytes
        byte[] b = stringValue.getBytes();
        
        double[][] X = new double[1][b.length];
        
        // convert array of byte to array of double
        for (int i=0; i<b.length; i++)
        {
            X[0][i]= (double)b[i];
        } 

        return new DoubleNumberToken( X );

    } // end eval
}


/*
@GROUP
char
@SYNTAX
number = str2num( string )
@DOC
Convert strings into numbers
@EXAMPLES
str2num("hello 12")  returns [104, 101, 108, 108, 111, 32, 49, 50]
@NOTES
.
@SEE
num2str
*/