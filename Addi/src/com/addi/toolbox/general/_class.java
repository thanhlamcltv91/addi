package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.DataToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;


public class _class extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

        if (getNArgIn(operands) != 1)
			throwMathLibException("_class: number of arguments != 1");

        if (!(operands[0] instanceof DataToken))  
            throwMathLibException("_class: first operand must be a data token");
        
        String opDataType = ((DataToken)operands[0]).getDataType();
        
        return new CharToken(opDataType);
    }
}

/*
@GROUP
general
@SYNTAX
class(value)
@DOC
returns the class name of some array.
@EXAMPLES
<programlisting>
a=55
class(a)  -> double

b='asdf'
class(b) -> char
</programlisting>
@NOTES
.
@SEE
isa, ismatrix, isnumeric, isscalar, issquare, islogical
*/

