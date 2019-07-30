package com.damianvaz.chessgame;
/**
 * This is a Bean Object to store a possible Move
 * 
 */
public class Move
{
	private int row, col;
	/**
	 * 
	 * @param row - The int value to the row of the possible Movement
	 * @param col - The int value to the column of the possible Movement
	 */
	public Move(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getCol()
	{
		return col;
	}

	public void setCol(int col)
	{
		this.col = col;
	}
	
	
	
}
