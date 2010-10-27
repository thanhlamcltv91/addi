package com.addi.toolbox.io;


import java.io.*;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.*;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;

public class pwd extends ExternalFunction
{
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{

	    File f = globals.getWorkingDirectory();
	    
		return new CharToken(f.getAbsolutePath());		

	} // end eval
}

/*
@GROUP
IO
@SYNTAX
pwd
@DOC
displays the current working directory
@EXAMPLES
<programlisting>
pwd
</programlisting>
@SEE
cd, createnewfile, dir, exist, mkdir, rmdir, delete, isfile, ishidden, lastmodified
*/

