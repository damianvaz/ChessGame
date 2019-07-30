package com.damianvaz.chessgame.pieces;

import java.util.ArrayList;

import com.damianvaz.chessgame.Move;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bishop extends Piece
{
	public Bishop(int row, int col, boolean isWhite)
	{
		this.name = "Bishop";
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
		String img = isWhite ? "/images/bishopW.png" : "/images/bishopB.png";
		Image image = new Image(getClass().getResourceAsStream(img));
		setGraphic(new ImageView(image));
	}
	/**
	 * This method gets all the possible moves for a Bishop Piece, that is on the
	 * square with coordinates row and col (int instance variables). Please note
	 * that it returns all possible moves, without taking to consideration pieces on
	 * its way, or if it's a legal move. Those possibilities should be handle in the
	 * main App class
	 * 
	 * @return - Move[]
	 * @see Move
	 */
	@Override
	public Move[] legalMoves()
	{
		ArrayList<Move> bishopMoves = new ArrayList<Move>();
		int rowCount = this.row;
		int colCount = this.col;
		//get all the possible moves from the current square and the upper right diagonal
		while(rowCount > 0 && colCount < 7)
		{
			bishopMoves.add(new Move(--rowCount, ++colCount));
		}
		rowCount = this.row;
		colCount = this.col;
		//get all the possible moves from the current square and the upper left diagonal
		while(rowCount > 0 && colCount > 0)
		{
			bishopMoves.add(new Move(--rowCount, --colCount));
		}
		rowCount = this.row;
		colCount = this.col;
		//get all the possible moves from the current square and the bottom left diagonal
		while(rowCount < 7 && colCount > 0)
		{
			bishopMoves.add(new Move(++rowCount, --colCount));
		}
		rowCount = this.row;
		colCount = this.col;
		//get all the possible moves from the current square and the bottom right diagonal
		while(rowCount < 7 && colCount < 7)
		{
			bishopMoves.add(new Move(++rowCount, ++colCount));
		}
		
		
		bishopMoves.trimToSize();
		Move[] moves = bishopMoves.toArray(new Move[bishopMoves.size()]);  
		return moves;
	}

	@Override
	public boolean canPieceMoveHere(int newRow, int newCol)
	{
		// testing for the board boundaries
		if(newRow < 0 && newRow > 7 && newCol < 0 && newCol  > 7)
		{
			return false;
		}
		int delta1 = this.row - newRow;
		int delta2 = this.col - newCol;
		if(delta1 < 0)
		{
			delta1 *= -1;
		}
		if(delta2 < 0)
		{
			delta2 *= -1;
		}
		if(delta1 == delta2)
		{
			return true;
		}
		return false;
	}

}
