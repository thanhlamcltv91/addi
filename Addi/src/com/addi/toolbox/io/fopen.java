package com.addi.toolbox.io;


import java.io.*;
import java.util.Stack;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.interpreter.ErrorLogger;
import com.addi.core.interpreter.Errors;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.CharToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;
import com.addi.core.tokens.numbertokens.Int16NumberToken;
import com.addi.core.tokens.numbertokens.Int32NumberToken;

/**An external function for opening a file*/
public class fopen extends ExternalFunction
{
	/**                                                          
       @param operands[0] = string filename    
       @param operands[1] = string permissions
       @return integer number of the file handle*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		BufferedWriter outWriter = null;
		BufferedReader inReader = null;

		String fileName = "";	
		String permissions = "";
		Int32NumberToken result = new Int32NumberToken(-1,0);
	
        if (getNArgIn(operands) < 1)
			throwMathLibException("fopen: number of arguments must be > 0");
        
        if (getNArgIn(operands) > 2)
			throwMathLibException("fopen: number of arguments must be < 3");

		if(operands[0] instanceof CharToken)
		{
			fileName = ((CharToken)operands[0]).toString();
			if (fileName.startsWith("/")) {
				fileName = fileName;
			} else {
				fileName = globals.getWorkingDirectory().getAbsolutePath() + "/" + fileName;
			}
			permissions = "";
			
			if (getNArgIn(operands) == 2) {
				if(operands[1] instanceof CharToken) {
					permissions = ((CharToken)operands[1]).toString();
					if (permissions.compareTo("w") == 0) {
						try {
							outWriter = new BufferedWriter(new FileWriter(fileName));
						} catch (IOException e) {
							throwMathLibException("fopen: file does not exist or cannot write to file");
						}
					} else if (permissions.compareTo("r") == 0) {
						try {
							inReader = new BufferedReader(new FileReader(fileName));
						} catch (FileNotFoundException e) {
							throwMathLibException("fopen: file does not exist or cannot read from file");
						}
					} else {
						throwMathLibException("fopen: only supports modes of 'w' or 'r' currently");
					}
				} else {
					Errors.throwMathLibException(ERR_INVALID_PARAMETER, new Object[] {"CharToken", operands[1].getClass().getName()});		
				}
			} else {
				permissions = "r";
				try {
					inReader = new BufferedReader(new FileReader(fileName));
				} catch (FileNotFoundException e) {
					throwMathLibException("fopen: file does not exist or cannot read from file");
				}
			}
		} else {
			Errors.throwMathLibException(ERR_INVALID_PARAMETER, new Object[] {"CharToken", operands[0].getClass().getName()});		
		}
		result.setValue(0, GlobalValues.nextFileNum, 0);
		GlobalValues.fileNames.put(GlobalValues.nextFileNum, fileName);
		GlobalValues.filePermissions.put(GlobalValues.nextFileNum, permissions);
		GlobalValues.fileWriters.put(GlobalValues.nextFileNum, outWriter);
		GlobalValues.fileReaders.put(GlobalValues.nextFileNum, inReader);
		GlobalValues.nextFileNum++;
		
		return result;
	}
	
}

/*
@GROUP
IO
@SYNTAX
matrix=csvread(filename, startrow, endrow)
@DOC
Reads in a matrix from a comma seperated value file.
@EXAMPLES
<programlisting>
cvsreac("testfile.csv", 0, 0)=[1,2;3,4]
</programlisting>
@SEE
csvwrite, urlread, dir, cd
*/

