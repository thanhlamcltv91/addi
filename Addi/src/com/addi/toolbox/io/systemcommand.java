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

package com.addi.toolbox.io;


import java.io.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;

/**An external function used to execute an external program
in a seperate process*/
public class systemcommand extends ExternalFunction
{
	/**execute a program
	@param operands[0] = filename
	@param operands[n] = arguments
	@return 1*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		String[] cmdarray = new String[operands.length];
		
        if (getNArgIn(operands) < 1)
			throwMathLibException("SystemCommand: number of arguments > 0");

        
        for(int index = 0; index < operands.length; index++)
		{
			cmdarray[index] = ((CharToken)operands[index]).getElementString(0);
		}
		
		try
		{
			Runtime.getRuntime().exec(cmdarray);
		}
		catch(IOException exception)
		{
			throwMathLibException("SystemCommand");			
		}
		
		return null;
	}
}

/*
@GROUP
IO
@SYNTAX
systemcommand(command name)
@DOC
A function for running external commands
@EXAMPLES
<programlisting>
systemcommand("EMACS")
</programlisting>
@SEE
runfile
*/

