package com.damianvaz.chessgame.pieces;

import com.damianvaz.chessgame.Move;

import javafx.scene.control.Label;
/**
 * This Abstract class is the most basic implementation of a chess piece. 
 * Every piece should extend this one
 * @see //TODO 
 */
public abstract class Piece extends Label
{
	protected String name;
	protected int row, col;
	protected boolean isWhite;
	protected Move[] possibleMoves;
	/**
	 * This method doen't consider if a piece is being blocked on its way
	 * @return Returns every legal Move the piece can do in a Move object array.
	 * @see Move 
	 * 
	 */
	public abstract Move[] legalMoves();
	public abstract boolean canPieceMoveHere(int row, int col);
	
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
	public boolean isWhite()
	{
		return isWhite;
	}
	public void setWhite(boolean isWhite)
	{
		this.isWhite = isWhite;
	}
	public String getName()
	{
		return name;
	}
	public Move[] getPossibleMoves()
	{
		return possibleMoves;
	}
	public void setPossibleMoves(Move[] possibleMoves)
	{
		this.possibleMoves = possibleMoves;
	}
	
}
