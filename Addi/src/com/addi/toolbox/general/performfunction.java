package com.addi.toolbox.general;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.FunctionToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;


/**An external function for performing functions*/
public class performfunction extends ExternalFunction
{
	/**Perform the named function on the operands
	@param operand[0] = the name of the function*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		FunctionToken function = null;

		if(operands[0] instanceof CharToken)
		{
			String name = ((CharToken)operands[0]).toString();
			function = new FunctionToken(name);
		}
		else if(operands[0] instanceof FunctionToken)
		{
			function = ((FunctionToken)operands[0]);
		}
		else
		{
			//error, unsupported type
		}
		
        OperandToken[] op = new OperandToken[operands.length-1];
        
		for(int operandNo = 0; operandNo < operands.length -1; operandNo++)
		{
			op[operandNo] = (OperandToken)(operands[operandNo + 1].clone());
		}
		
        function.setOperands(op);
        
		return function.evaluate(null, globals);
	}
}

/*
@GROUP
general
@SYNTAX
PERFORMFUNCTION(function, parameters)
@DOC
Performs the function on the supplies parameters.
@EXAMPLES
PERFORMFUNCTION(ACOS, 1) = 0
PERFORMFUNCTION(MAX,1,3) = 3
@NOTES
@SEE
*/
