package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;


//CCX import java.awt.Toolkit;

/**An external function for emitting a beep sound*/
public class beep extends ExternalFunction
{
	/**Emits a beeping sound*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		/*Toolkit.getDefaultToolkit().beep();CCX*/
		
		return null;
	}
}

/*
@GROUP
general
@SYNTAX
beep();
@DOC
this sounds an audible beep
@NOTES
@EXAMPLES
<programlisting>
beep();
</programlisting>
@SEE
*/
