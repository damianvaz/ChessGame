package com.damianvaz.chessgame.pieces;

import java.util.ArrayList;

import com.damianvaz.chessgame.Move;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class King extends Piece
{
	private boolean isCheck;
	private boolean hasMoved;

	public King(int row, int col, boolean isWhite)
	{
		this.name = "King";
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
		this.hasMoved = false;
		// TODO check if isWhite then add image accordingly
		Image image = new Image(getClass().getResourceAsStream("/images/kingW.png"));
		setGraphic(new ImageView(image));
	}

	/**
	 * This method gets all the possible moves for a King Piece, that is on the
	 * square with coordinates row and col (int instance variables). Please note
	 * that it returns all possible moves, without taking to consideration pieces on
	 * its way, or if it's a legal move. Those possibilities should be handle in the
	 * main App class Also if the king is check the field isCheck should be set to
	 * true in the App class
	 * 
	 * @return - Move[]
	 * @see Move
	 */
	@Override
	public Move[] legalMoves()
	{
		ArrayList<Move> kingMoves = new ArrayList<Move>();
		// The king like the knight has up to 8 moves it can go
		// MOVE 1:
		int newRow = this.row - 1;
		int newCol = this.col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			kingMoves.add(new Move(newRow, newCol));
		}
		// MOVE 2:
		newRow = this.row - 1;
		newCol = this.col;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			kingMoves.add(new Move(newRow, newCol));
		}
		// MOVE 3:
		newRow = this.row - 1;
		newCol = this.col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			kingMoves.add(new Move(newRow, newCol));
		}
		// MOVE 4:
		newRow = this.row;
		newCol = this.col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			kingMoves.add(new Move(newRow, newCol));
		}
		// MOVE 5:
		newRow = this.row;
		newCol = this.col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			kingMoves.add(new Move(newRow, newCol));
		}
		// MOVE 6:
		newRow = this.row + 1;
		newCol = this.col;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			kingMoves.add(new Move(newRow, newCol));
		}
		// MOVE 7:
		newRow = this.row + 1;
		newCol = this.col;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			kingMoves.add(new Move(newRow, newCol));
		}
		// MOVE 8:
		newRow = this.row + 1;
		newCol = this.col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			kingMoves.add(new Move(newRow, newCol));
		}

		kingMoves.trimToSize();
		Move[] moves = kingMoves.toArray(new Move[kingMoves.size()]);
		return moves;
	}

	public boolean checkIsStillOnTheBoard(int row, int col)
	{
		if (row < 0 || row > 7 || col < 0 || col > 7)
		{
			return false;
		} else
		{
			return true;
		}
	}
	/**
	 * In the Board class should check if its a legal move and if it hasMoved is false, and if it can cantle.
	 */
	@Override
	public boolean canPieceMoveHere(int row, int col)
	{
		// testing for the board boundaries
		if (row < 0 || row > 7 || col < 0 || col > 7)
		{
			return false;
		}
	
		
		int deltaRow = this.row - row;
		int deltaCol = this.col - col;

		if (deltaRow < 0)
		{
			deltaRow *= -1;
		}
		if (deltaCol < 0)
		{
			deltaCol *= -1;
		}
		if (deltaRow <= 1 && deltaCol <= 1)
		{
			return true;
		}
		return false;
	}

	public boolean isCheck()
	{
		return isCheck;
	}

	public void setCheck(boolean isCheck)
	{
		this.isCheck = isCheck;
	}

}
