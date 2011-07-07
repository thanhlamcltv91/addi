// Copyright (C) 2011 Free Software Foundation FSF
//
// This file is part of Addi.
//
// Addi is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 3 of the License, or (at
// your option) any later version.
//
// Addi is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Addi. If not, see <http://www.gnu.org/licenses/>.

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

