package com.damianvaz.chessgame.pieces;

import java.util.ArrayList;

import com.damianvaz.chessgame.Move;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Queen extends Piece
{
	public Queen(int row, int col, boolean isWhite)
	{
		this.name = "Queen";
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
		// TODO check if isWhite then add image accordingly
		Image image = new Image(getClass().getResourceAsStream("/images/queenW.png"));
		setGraphic(new ImageView(image));
	}

	/**
	 * This method gets all the possible moves for a Queen Piece, that is on the
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
		ArrayList<Move> legalMoves = new ArrayList<Move>();

		// Get all the files

		// Get all the moves possible from the current col and up
		for (int i = this.col + 1; i < 8; i++)
		{
			legalMoves.add(new Move(row, i));
			System.out.println("Legal Move: " + row + ", " + i);
			// TODO delete the syso
		}
		// Get all the moves possible from the current col and down
		for (int i = this.col - 1; i >= 0; i--)
		{
			legalMoves.add(new Move(row, i));
			System.out.println("Legal Move: " + row + ", " + i);
			// TODO delete the syso
		}

		// Get all the moves possible from the current row and up
		for (int i = this.row + 1; i < 8; i++)
		{
			legalMoves.add(new Move(i, col));
			System.out.println("Legal Move: " + i + ", " + col);
			// TODO delete the syso
		}
		// Get all the moves possible from the current row and down
		for (int i = this.row - 1; i >= 0; i--)
		{
			legalMoves.add(new Move(i, col));
			System.out.println("Legal Move: " + i + ", " + col);
			// TODO delete the syso
		}

		// Get the diagonals

		int rowCount = this.row;
		int colCount = this.col;
		// get all the possible moves from the current square and the upper right
		// diagonal
		while (rowCount > 0 && colCount < 7)
		{
			legalMoves.add(new Move(--rowCount, ++colCount));
		}
		rowCount = this.row;
		colCount = this.col;
		// get all the possible moves from the current square and the upper left
		// diagonal
		while (rowCount > 0 && colCount > 0)
		{
			legalMoves.add(new Move(--rowCount, --colCount));
		}
		rowCount = this.row;
		colCount = this.col;
		// get all the possible moves from the current square and the bottom left
		// diagonal
		while (rowCount < 7 && colCount > 0)
		{
			legalMoves.add(new Move(++rowCount, --colCount));
		}
		rowCount = this.row;
		colCount = this.col;
		// get all the possible moves from the current square and the bottom right
		// diagonal
		while (rowCount < 7 && colCount < 7)
		{
			legalMoves.add(new Move(++rowCount, ++colCount));
		}

		legalMoves.trimToSize();
		Move[] moves = legalMoves.toArray(new Move[legalMoves.size()]);
		return moves;
	}

	@Override
	public boolean canPieceMoveHere(int row, int col)
	{
		// testing for the board boundaries
		if (row < 0 || row > 7 || col < 0 || col > 7)
		{
			return false;
		}
		if (this.row == row || this.col == col)
		{
			return true;
		}

		int delta1 = this.row - row;
		int delta2 = this.col - col;
		if (delta1 < 0)
		{
			delta1 *= -1;
		}
		if (delta2 < 0)
		{
			delta2 *= -1;
		}
		if (delta1 == delta2)
		{
			return true;
		}
		return false;
	}

}
