package com.damianvaz.chessgame.pieces;

import java.util.ArrayList;

import com.damianvaz.chessgame.Move;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Knight extends Piece
{
	public Knight(int row, int col, boolean isWhite)
	{
		this.name = "Knight";
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
		String img = isWhite ? "/images/knightW.png" : "/images/knightB.png";
		Image image = new Image(getClass().getResourceAsStream(img));
		setGraphic(new ImageView(image));
	}

	/**
	 * This method gets all the possible moves for a Knight Piece, that is on the
	 * square with coordinates row and col (int instance variables).
	 * 
	 * @return - Move[]
	 * @see Move
	 */
	@Override
	public Move[] legalMoves()
	{
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		// the knight can move up to 8 squares
		// getting all the 8 and checking if they did not leave the board
		// MOVE 1:
		int newRow = this.row - 1;
		int col = this.col - 2;
		if (checkIsStillOnTheBoard(newRow, col))
		{
			legalMoves.add(new Move(newRow, col));
			System.out.println("Knight move: (" + newRow + ", " + col + ")");
			// TODO delete syso
		}
		// MOVE 2:
		newRow = this.row - 2;
		col = this.col - 1;
		if (checkIsStillOnTheBoard(newRow, col))
		{
			legalMoves.add(new Move(newRow, col));
			System.out.println("Knight move: (" + newRow + ", " + col + ")");
			// TODO delete syso
		}
		// MOVE 3:
		newRow = this.row - 2;
		col = this.col + 1;
		if (checkIsStillOnTheBoard(newRow, col))
		{
			legalMoves.add(new Move(newRow, col));
			System.out.println("Knight move: (" + newRow + ", " + col + ")");
			// TODO delete syso
		}
		// MOVE 4:
		newRow = this.row - 1;
		col = this.col + 2;
		if (checkIsStillOnTheBoard(newRow, col))
		{
			legalMoves.add(new Move(newRow, col));
			System.out.println("Knight move: (" + newRow + ", " + col + ")");
			// TODO delete syso
		}
		// MOVE 5:
		newRow = this.row + 1;
		col = this.col - 2;
		if (checkIsStillOnTheBoard(newRow, col))
		{
			legalMoves.add(new Move(newRow, col));
			System.out.println("Knight move: (" + newRow + ", " + col + ")");
			// TODO delete syso
		}
		// MOVE 6:
		newRow = this.row + 2;
		col = this.col - 1;
		if (checkIsStillOnTheBoard(newRow, col))
		{
			legalMoves.add(new Move(newRow, col));
			System.out.println("Knight move: (" + newRow + ", " + col + ")");
			// TODO delete syso
		}
		// MOVE 7:
		newRow = this.row + 2;
		col = this.col + 1;
		if (checkIsStillOnTheBoard(newRow, col))
		{
			legalMoves.add(new Move(newRow, col));
			System.out.println("Knight move: (" + newRow + ", " + col + ")");
			// TODO delete syso
		}
		// MOVE 8:
		newRow = this.row + 1;
		col = this.col + 2;
		if (checkIsStillOnTheBoard(newRow, col))
		{
			legalMoves.add(new Move(newRow, col));
			System.out.println("Knight move: (" + newRow + ", " + col + ")");
			// TODO delete syso
		}

		legalMoves.trimToSize();
		Move[] moves = legalMoves.toArray(new Move[legalMoves.size()]);
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

	@Override
	public boolean canPieceMoveHere(int row, int col)
	{
		if(row < 0 || row > 7 || col < 0 || col > 7)
		{
			return false;
		}
	
		int deltaRow = this.row - row;
		int deltaCol = this.col - col;
		
		if(deltaRow < 0)
		{
			deltaRow *= -1;
		}
		if(deltaCol < 0)
		{
			deltaCol *= -1;
		}
		if((deltaRow == 2 && deltaCol == 1) || (deltaRow == 1 && deltaCol == 2))
		{
			return true;
		}
		
		return false;
	}

}
