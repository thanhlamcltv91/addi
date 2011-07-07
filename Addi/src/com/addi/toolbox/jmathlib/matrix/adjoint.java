// Copyright (C) 2011 Free Software Foundation FSF
//
// This file is part of Addi.
//
// Addi is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 3 of the License, or (at
// your option) any later version.
//
// Addi is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Addi. If not, see <http://www.gnu.org/licenses/>.

package com.addi.toolbox.jmathlib.matrix;

import com.addi.core.functions.ExternalFunction;
import com.addi.core.functions.Function;
import com.addi.core.interpreter.GlobalValues;
import com.addi.core.tokens.FunctionToken;
import com.addi.core.tokens.OperandToken;
import com.addi.core.tokens.Token;
import com.addi.core.tokens.numbertokens.DoubleNumberToken;


/**An external function for determining the determinant of a matrix*/
public class adjoint extends ExternalFunction
{
	/**Check that the parameter is a square matrix then claculate
	it's determinant*/
	public OperandToken evaluate(Token[] operands, GlobalValues globals)
	{
        OperandToken result = null;
		
        if (getNArgIn(operands) != 1)
			throwMathLibException("Adjoint: number of arguments != 1");

		if(!(operands[0] instanceof DoubleNumberToken))
            throwMathLibException("Adjoint: works only on numbers");

        DoubleNumberToken matrix = ((DoubleNumberToken)operands[0]);
		
		if(matrix.getSizeX() == matrix.getSizeY())
		{
			int size = matrix.getSizeX();
			
			result = new DoubleNumberToken(calcAdjoint(matrix.getReValues(), size, globals));
		}
        else
        {
            com.addi.core.interpreter.Errors.throwMathLibException(ERR_NOT_SQUARE_MATRIX);
        }			
		
		return result;
	}
	
	/**Calculates the adjoint of the matrix
	It uses the Determinant class to calculate the determinants of the sub matrices
	values = array of values
	size   = the size of the matrix
	result = the adjoint as a size * size array of double*/
	private double[][] calcAdjoint(double[][] values, int size, GlobalValues globals)
	{
		FunctionToken token = null;
		Function function   = null;
        
		try
		{
			token = new FunctionToken("determinant");
			function = globals.getFunctionManager().findFunction(token);
		}
		catch(java.lang.Exception e)
		{}

		double[][] result = new double[size][size];
		for(int rowNumber = 0; rowNumber < size; rowNumber++)
		{
			for(int colNumber = 0; colNumber < size; colNumber++)
			{
				DoubleNumberToken subMatrix = new DoubleNumberToken(constructMatrix(values, size, rowNumber, colNumber));
				
				OperandToken[] operands = new OperandToken[1];
				operands[0] = subMatrix;

								
				double minor = ((DoubleNumberToken)function.evaluate(operands, globals)).getValueRe();
				
				int modifier = -1;
				if((rowNumber + colNumber) % 2 == 0)
					modifier = 1;
					
				result[rowNumber][colNumber] = modifier * minor;
			}
		}
		
		//transpose the array of cofactors to produce the result
		double[][] transResult = new double[size][size];
		
		for(int colno = 0; colno < size; colno++)
		{
			for(int rowno = 0; rowno < size; rowno++)
			{
				transResult[colno][rowno] = result[rowno][colno];
			}
		}
			
		return transResult;
	}

	/**constructs a sub matrix
	values = the array of values
	size   = the size of the origional matrix
	column = the column to remove
	result = the [size-1][size-1] array of values after reomoving
			  row 1 and the specified column*/
	private double[][] constructMatrix(double[][]values, int size, int row, int column)
	{
		double newMatrix[][] = new double[size-1][size-1];
		
		for(int rowNumber = 0; rowNumber < size; rowNumber++)
		{
			for(int colNumber = 0; colNumber < size; colNumber++)
			{
				if(rowNumber < row)
				{
					if(colNumber < column)
						newMatrix[rowNumber][colNumber] = values[rowNumber][colNumber];
					else if(colNumber > column)
						newMatrix[rowNumber][colNumber-1] = values[rowNumber][colNumber];
				}
				else if(rowNumber > row)
				{
					if(colNumber < column)
						newMatrix[rowNumber-1][colNumber] = values[rowNumber][colNumber];
					else if(colNumber > column)
						newMatrix[rowNumber-1][colNumber-1] = values[rowNumber][colNumber];
				}
					
			}
		}
		return newMatrix;
	}
}

/*
@GROUP
matrix
@SYNTAX
answer = adjoint(square matrix)
@DOC
Returns the adjoint matrix for the supplied matrix.
@NOTES
@EXAMPLES
<programlisting>
</programlisting>
@SEE
inversematrix, inv

*/
