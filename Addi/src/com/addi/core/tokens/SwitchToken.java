package com.addi.core.tokens;


import java.util.Vector;

import com.addi.core.interpreter.ErrorLogger;
import com.addi.core.interpreter.GlobalValues;


/**Used to implement if-then-else operations within an expression*/
public class SwitchToken extends CommandToken
{

	/**test value*/
	OperandToken value;
	
	/**condition */
	Vector cases;

	/**Constructor setting cases
	 * @param _value = an OperandToken containing the value to test
	 * @param _cases = a vector of case tokens
	 */
	public SwitchToken(OperandToken _value, Vector _cases)
	{
		value = _value;
		cases = _cases;
	}

	/**
	 * 
	 * @return
	 */
	public OperandToken getData()
	{
		return value;
	}

	/**
	 * 
	 * @return
	 */
	public Vector getCases()
	{
		return cases;
	}

    /**evaluates the operator
     * @param operands = the operators operands
     * @param
     * @return the result of the test as an OperandToken
     */
    public OperandToken evaluate(Token[] operands, GlobalValues globals)
    {
    	OperandToken result = null;
    	
    	int pos = 0;
    	int count = cases.size();
    	while((result == null) && (pos < count)) 
    	{
    		CaseToken caseToken = ((CaseToken)cases.elementAt(pos));
    		ErrorLogger.debugLine("switch "+caseToken.toString());
    		
    		result = caseToken.evaluate(new OperandToken[]{value}, globals);
    		
    		pos++;
    	}
    	
    	return null; // switch-case does not return any data
    }
    

    /**Convert the operator to a string
     * @return the operator as a string
     */
    public String toString()
    {
        return "switch"; //( "+value.toString()+" )";
    }
    
}
