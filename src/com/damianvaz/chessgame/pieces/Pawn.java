package com.damianvaz.chessgame.pieces;

import java.util.ArrayList;

import com.damianvaz.chessgame.Move;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawn extends Piece
{
	public Pawn(int row, int col, boolean isWhite)
	{
		this.name = "Pawn";
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
		String img = isWhite ? "/images/pawnW.png" : "/images/pawnB.png";
		Image image = new Image(getClass().getResourceAsStream(img));
		setGraphic(new ImageView(image));
	}

	/**
	 * This method gets all the possible moves for a Pawn Piece, that is on the
	 * square with coordinates row and col (int instance variables). Please note
	 * that it returns all possible moves, without taking to consideration pieces on
	 * its way, or if it's a legal move. Those possibilities should be handle in the
	 * main App class. Also if there's diagonal pieces or en passant is possible,
	 * that should be handle on the App class.
	 * 
	 * @return - Move[]
	 * @see Move
	 */
	@Override
	public Move[] legalMoves()
	{
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		// if its Pawn's first move
		if (isWhite)
		{
			if (row == 6)
			{
				legalMoves.add(new Move(this.row - 2, this.col));
			}
			legalMoves.add(new Move(this.row - 1, this.col));
		} else
		{
			if (row == 1)
			{
				legalMoves.add(new Move(this.row + 2, this.col));
			}
			legalMoves.add(new Move(this.row + 1, this.col));
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
		if (isWhite)
		{
			if (col == this.col && row == this.row - 1)
			{
				return true;
			}
			if (this.row == 6 && row == 4)
			{
				return true;
			}
		} else
		{
			if (col == this.col && row == this.row + 1)
			{
				return true;
			}
			if (this.row == 1 && row == 3)
			{
				return true;
			}
		}
		return false;
	}

}
