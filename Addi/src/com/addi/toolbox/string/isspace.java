package com.addi.toolbox.string;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.*;


/**An external function for checking on whitespaces */
public class isspace extends ExternalFunction
{
    public OperandToken evaluate(Token[] operands, GlobalValues globals)
    {
        if (getNArgIn(operands)!=1)
            throwMathLibException("isspace: number of input arguments != 1");

        if ( !(operands[0] instanceof CharToken))
            throwMathLibException("isspace: works only on strings");

        // get data from arguments
        String str = ((CharToken)operands[0]).getValue();

        double[][] ret = new double[1][str.length()];
        
        // find all whitespaces
        for (int i=0; i<str.length(); i++)
        {
            char c = str.charAt(i);
            if ((c==' ')  || 
                (c=='\t') || 
                (c=='\r') ||
                (c=='\n')   )
                ret[0][i]= 1.0;
        } 

        return new DoubleNumberToken( ret );

    } // end eval
}


/*
@GROUP
char
@SYNTAX
isspace()
@DOC
@EXAMPLES
@NOTES
@SEE
letter
*/