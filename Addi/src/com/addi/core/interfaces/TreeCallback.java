package com.addi.core.interfaces;

import com.addi.core.tokens.DataToken;
import com.addi.core.tokens.Expression;
import com.addi.core.tokens.OperandToken;

public interface TreeCallback 
{
	public OperandToken evaluateFunction(OperandToken token, String options);

	public OperandToken evaluateFunction(Expression token, String options);

	public OperandToken evaluateFunction(DataToken token, String options);	
}

