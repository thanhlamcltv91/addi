package com.addi.toolbox.io;


import java.io.*;
import java.util.Iterator;
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
public class fclose extends ExternalFunction
{
	/**                                                          
       @param operands[0] = string filename    
       @param operands[1] = string permissions
       @return integer number of the file handle*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
		
		Int32NumberToken result = new Int32NumberToken(-1,0);
	
        if (getNArgIn(operands) < 1)
			throwMathLibException("fclose: number of arguments must be > 0");
        
        if (getNArgIn(operands) > 1)
			throwMathLibException("fclose: number of arguments must be < 2");

		if(operands[0] instanceof CharToken)
		{
			String fileName = ((CharToken)operands[0]).toString();
			if (fileName.compareTo("all") == 0) {
				GlobalValues.fileNames.clear();
				GlobalValues.filePermissions.clear();
				for (BufferedReader value : GlobalValues.fileReaders.values()) {
					try {
						if (value != null) {
						   value.close();
						}
					} catch (IOException e) {
						throwMathLibException("fclose: unable to close file");
					}
				}
				for (BufferedWriter value : GlobalValues.fileWriters.values()) {
					try {
						if (value != null) {
							value.close();
						}
					} catch (IOException e) {
						throwMathLibException("fclose: unable to close file");
					}
				}
				GlobalValues.fileWriters.clear();
				GlobalValues.fileReaders.clear();
				result.setValue(0, 0, 0);
				GlobalValues.nextFileNum = 0;
			} else {
				throwMathLibException("fclose: requires either 'all' or an integer fileHandle");
			}
		} else if(operands[0] instanceof Int32NumberToken) {
			int handle = ((Int32NumberToken)operands[0]).getValueRe();
			if (GlobalValues.fileNames.containsKey(handle)) {	
				GlobalValues.fileNames.remove(handle);
				GlobalValues.filePermissions.remove(handle);
				BufferedReader reader = GlobalValues.fileReaders.get(handle);
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						throwMathLibException("fclose: unable to close file");
					}
				}
				BufferedWriter writer = GlobalValues.fileWriters.get(handle);
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						throwMathLibException("fclose: unable to close file");
					}
				}
				GlobalValues.fileWriters.remove(handle);
				GlobalValues.fileReaders.remove(handle);
				result.setValue(0, 0, 0);
			} else {
				throwMathLibException("fclose: no matching file is open");
			}
		} else {
			throwMathLibException("fclose: requires either 'all' or an integer fileHandle");
		}
		
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

