package com.damianvaz.chessgame.pieces;

import java.util.ArrayList;

import com.damianvaz.chessgame.Move;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Rook extends Piece
{
	public Rook(int row, int col, boolean isWhite)
	{
		this.name = "Rook";
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
		String img = isWhite ? "/images/rookW.png" : "/images/rookB.png";
		Image image = new Image(getClass().getResourceAsStream(img));
		setGraphic(new ImageView(image));
	}
	/**
	 * This method gets all the possible moves for a Rook Piece, that is on the
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
		// Get all the moves possible from the current col and up
		for (int i = this.col + 1; i < 8; i++)
		{
			legalMoves.add(new Move(row, i));
			System.out.println("Legal Move: " + row + ", " + i);
			//TODO delete the syso
		}
		// Get all the moves possible from the current col and down
		for (int i = this.col - 1; i >= 0; i--)
		{
			legalMoves.add(new Move(row, i));
			System.out.println("Legal Move: " + row + ", " + i);
			//TODO delete the syso
		}
		
		// Get all the moves possible from the current row and up
		for (int i = this.row + 1; i < 8; i++)
		{
			legalMoves.add(new Move(i, col));
			System.out.println("Legal Move: " + i + ", " + col);
			//TODO delete the syso
		}
		// Get all the moves possible from the current row and down
		for (int i = this.row - 1; i >= 0; i--)
		{
			legalMoves.add(new Move(i, col));
			System.out.println("Legal Move: " + i + ", " + col);
			//TODO delete the syso
		}
		
		legalMoves.trimToSize();
		Move[] moves = legalMoves.toArray(new Move[legalMoves.size()]);  
		return moves;
	}
	
	@Override
	public boolean canPieceMoveHere(int row, int col)
	{
		if((this.row == row && col < 8 && col >= 0) || (this.col == col && row < 8 && row >= 0))
		{
			System.out.println("Possible move");
			return true;
		}
		else
		{
			System.out.println("Not possible move");
		}
		return false;
	}

}
