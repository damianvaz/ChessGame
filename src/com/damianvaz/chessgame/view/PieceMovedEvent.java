package com.damianvaz.chessgame.view;

import com.damianvaz.chessgame.pieces.Piece;

/**
 * This is a bean class to transfer data between listeners
 * 
 */
public class PieceMovedEvent
{
	private Piece piece;
	private int newSquareRow, newSquareCol;
	double originalTranslateX, originalTranslateY, newTranslateX, newTranslateY;
	
	public PieceMovedEvent(Piece piece, int newSquareRow, int newSquareCol, double originalTranslateX,
			double originalTranslateY, double newTranslateX, double newTranslateY)
	{
		this.piece = piece;
		this.newSquareRow = newSquareRow;
		this.newSquareCol = newSquareCol;
		this.originalTranslateX = originalTranslateX;
		this.originalTranslateY = originalTranslateY;
		this.newTranslateX = newTranslateX;
		this.newTranslateY = newTranslateY;
	}

	public Piece getPiece()
	{
		return piece;
	}

	public int getNewSquareRow()
	{
		return newSquareRow;
	}

	public int getNewSquareCol()
	{
		return newSquareCol;
	}

	public double getOriginalTranslateX()
	{
		return originalTranslateX;
	}

	public double getOriginalTranslateY()
	{
		return originalTranslateY;
	}

	public double getNewTranslateX()
	{
		return newTranslateX;
	}

	public double getNewTranslateY()
	{
		return newTranslateY;
	}
}