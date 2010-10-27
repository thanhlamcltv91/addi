/* 
 * This file is part or JMathLib 
 * 
 * Check it out at http://www.jmathlib.de
 *
 * Author:   
 * (c) 2005-2009   
 */
package com.addi.core.interpreter;

import java.io.*;

import com.addi.core.tokens.OperandToken;


/**This universal the base class for all class define by JMathLib. 
It defines Global values as class variables and also defines functions for creating and accessing the working environment.*/
public class RootObject implements java.io.Serializable, 
                                   java.lang.Cloneable, 
                                   com.addi.core.constants.ErrorCodes, 
                                   com.addi.core.constants.TokenConstants
{
    
	/**
	 * 
	 */
    public RootObject()
    {
    }

    /**Converts the object to a string based on the operand list
     *  @param operands = operands for the expression
     */
    public String toString(OperandToken[] operands)
    {
    	return "root object";
    }

    /**Duplicates the object by serialising it to a piped stream then reading it back into
     * the new object
     */
    public Object clone() 
    {

        /* reference: Core Java Volume 2 Advanced Features p.66-67 */
        /*            Use a ByteArrayOutputStream                  */
        /* stefan: I implemented the ByteArray in order to get rid         
        of the output.txt file, because as an applet MathLib will           
        crash when it tries to open a file on the local disc.   */        
    	Object copy = null;
    	try
    	{
	    	//create streams, uses a byte array stream
	    	
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            
            //create object stream
	    	ObjectOutputStream objectOutput = new ObjectOutputStream(output);
	    	
	    	objectOutput.writeObject(this);

            ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());			

            ObjectInputStream objectInput   = new ObjectInputStream(input);
                  	
	    	copy = objectInput.readObject();

			//close output objects	    	
	    	objectOutput.close();
	    	output.close();

			//close input objects
	    	objectInput.close();
	    	input.close();	    	
	 	}
	 	catch(java.io.IOException except)
	 	{
            except.printStackTrace();
			ErrorLogger.debugLine("RootObject: IO exception");
			ErrorLogger.debugLine(except.getMessage());
	 	}
	 	catch(java.lang.ClassNotFoundException except)
	 	{
			ErrorLogger.debugLine("RootObject: Class not found exception");
	 	}
    	
    	return copy;
    }


} 
