package com.addi.toolbox.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.*;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;
import com.addi.core.tokens.numbertokens.Int32NumberToken;


/**An external function for changing strings into numbers */
public class fgets extends ExternalFunction
{
        
    /**returns a line of a file including the newline character 
    * @param line = fgets (fid) 
    * @return a string line fo the file including the newline character*/
    public OperandToken evaluate(Token[] operands, GlobalValues globals)
    {

    	BufferedReader inReader = null;
    	
        // one operand 
        if (getNArgIn(operands) != 1) {
            throwMathLibException("fgets: number of input arguments must be 1");
        }
            
        if ((operands[0] instanceof Int32NumberToken)) {
        	int fileHandle = ((Int32NumberToken)operands[0]).getValueRe();
        	if (GlobalValues.fileReaders.containsKey(fileHandle)) {
        		inReader = GlobalValues.fileReaders.get(fileHandle);
        	} else {
        		throwMathLibException("fgets: no matching file handle");
        	}
        } else {
        	throwMathLibException("fgets: must provide an integer file handle");
        }
        
        String line = null;
        try { 
        	line = inReader.readLine();
        	if (line == null) {
        		return new Int32NumberToken(-1,0);
        	} else {
        		return new CharToken( line + "\n" );
        	}
		} catch (IOException e) {
			throwMathLibException("fgets: failed to read from file");
			return new Int32NumberToken(-1,0);
		}

    } 
       
}


/*
@GROUP
char
@SYNTAX
string = fgets( fid )
@DOC
Returns a line of the file including the newline character
@EXAMPLES
CCX
@NOTES
.
@SEE
num2str
*/