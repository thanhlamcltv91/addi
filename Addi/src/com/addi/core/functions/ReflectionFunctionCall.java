/* 
 * This file is part or JMathLib 
 * 
 * Check it out at http://www.jmathlib.de
 *
 * Author:   
 * (c) 2005-2009   
 */
package com.addi.core.functions;

//import MathLib.Tokens.FunctionToken;
//import MathLib.Tokens.VariableToken;
//import MathLib.Tokens.StringToken;
//import MathLib.Tokens.Expression;

//import java.applet.*;
//import java.util.Vector;
//import java.io.*;
//import java.net.*;
import java.lang.reflect.*;

import com.addi.core.interpreter.*;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;

/**Class for call a function from an external class using reflection*/
public class ReflectionFunctionCall extends ExternalFunction
{
    private Class externalClass;
    
    /**creates a reflection function call containing the class being called*/
    public ReflectionFunctionCall(Class _externalClass, String functionName)
    {
        super(functionName);
        
        externalClass = _externalClass;
    }
    
    public OperandToken evaluate(Token []operands, GlobalValues globals)
    {
        boolean found = false;
        ErrorLogger.debugLine("evaluating reflection function");
        Method[] methodList = externalClass.getMethods();
        
        ErrorLogger.debugLine(name);
        for(int methodNo = 0; methodNo < methodList.length && !found; methodNo++)
        {
            String funcName = methodList[methodNo].toString();
            funcName = funcName.substring(0, funcName.indexOf("(") );
            funcName = funcName.substring( funcName.lastIndexOf(".") + 1);
            ErrorLogger.debugLine("method " + methodNo + " = " + funcName);
            
            if(funcName.equalsIgnoreCase(name))
            {
                ErrorLogger.debugLine("found method*********************************************");                
                evaluateMethod(methodList[methodNo]);
                found = true;
            }
        }
        
        return null;
    }
    
    private void evaluateMethod(Method calledMethod)
    {
        Class[] paramaterList = calledMethod.getParameterTypes();

        for(int paramaterNo = 0; paramaterNo < paramaterList.length; paramaterNo++)
        {
            ErrorLogger.debugLine("method " + paramaterNo + " = " + paramaterList[paramaterNo].toString());            
        }                
    }
    
    public String toString()
    {
        return "reflection object";
    }
}
