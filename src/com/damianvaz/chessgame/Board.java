package com.damianvaz.chessgame;

import java.util.ArrayList;

import com.damianvaz.chessgame.pieces.Bishop;
import com.damianvaz.chessgame.pieces.King;
import com.damianvaz.chessgame.pieces.Knight;
import com.damianvaz.chessgame.pieces.Pawn;
import com.damianvaz.chessgame.pieces.Piece;
import com.damianvaz.chessgame.pieces.Queen;
import com.damianvaz.chessgame.pieces.Rook;

public class Board
{
	private Piece[] whitePieces;
	private Piece[] blackPieces;

	private Piece[][] board = new Piece[8][8];

	public Board(Piece[] whitePieces, Piece[] blackPieces)
	{
		this.whitePieces = whitePieces;
		this.blackPieces = blackPieces;

		for (Piece piece : whitePieces)
		{
			board[piece.getRow()][piece.getCol()] = piece;
		}

		for (Piece piece : blackPieces)
		{
			board[piece.getRow()][piece.getCol()] = piece;
		}
	}

	public void setPieces(Piece[] whitePieces, Piece[] blackPieces)
	{
		this.whitePieces = whitePieces;
		this.blackPieces = blackPieces;
	}

	/**
	 * We get all the pieces legal Moves here, instead of doing methods in the
	 * pieces class Because here we can test if a piece is on the way. Or if the
	 * king is Checked...
	 * 
	 * @param isWhitesMove if its white's move or not
	 * @return - Array of legal Moves
	 * @see Board#getPieceMove(Piece)
	 * @see Move
	 * @see Piece
	 * @see Bishop
	 * @see King
	 * @see Knight
	 * @see Pawn
	 * @see Queen
	 * @see Rook
	 */
	public Move[] getAllPossibleMoves(boolean isWhitesMove)
	{
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		Piece[] piecesToMove = isWhitesMove ? whitePieces : blackPieces;
		for (int i = 0; i < piecesToMove.length; i++)
		{
			legalMoves.addAll(getPieceMove(piecesToMove[i]));
		}

		legalMoves.trimToSize();
		Move[] moves = legalMoves.toArray(new Move[legalMoves.size()]);
		return moves;
	}

	/**
	 * 
	 * @param piece piece to get all legal Moves
	 * @return ArrayList of all the pieces legal moves
	 * @see Board#getKingMoves(Piece piece)
	 * @see Board#getBishopMoves(Piece piece)
	 * @see Board#getKnightMoves(Piece piece)
	 * @see Board#getRookMoves(Piece piece)
	 * @see Board#getQueenMoves(Piece piece)
	 * @see Board#getPawnMoves(Piece piece)
	 */
	private ArrayList<Move> getPieceMove(Piece piece)
	{
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		String pieceName = piece.getName();
		switch (pieceName)
		{
		case "Pawn":
		{
			legalMoves.addAll(getPawnMoves(piece));
			break;
		}
		case "Knight":
		{
			legalMoves.addAll(getKnightMoves(piece));
			break;
		}
		case "Bishop":
		{
			legalMoves.addAll(getBishopMoves(piece));
			break;
		}
		case "Rook":
		{
			legalMoves.addAll(getRookMoves(piece));
			break;
		}
		case "Queen":
		{
			legalMoves.addAll(getQueenMoves(piece));
			break;
		}
		case "King":
		{
			legalMoves.addAll(getKingMoves(piece));
			break;
		}
		default:
		{
			break;
		}
		}

		// TODO Auto-generated method stub
		legalMoves.trimToSize();
		return legalMoves;
	}

	private ArrayList<Move> getPawnMoves(Piece piece)
	{
		ArrayList<Move> pawnMoves = new ArrayList<Move>();
		int row = piece.getRow();
		int col = piece.getCol();
		// if its Pawn's first move
		if (piece.isWhite())
		{
			// if there's no piece above it
			if (this.board[row - 1][col] == null)
			{
				// if it's on row 6 it hasn't moved and it can go 2 squares up
				if (row == 6 && board[row - 2][col] == null)
				{
					pawnMoves.add(new Move(row - 2, col));
				}
				pawnMoves.add(new Move(row - 1, col));
			}
			// TODO make it possible so it can take pieces diagonally
		} else
		{
			if (this.board[row + 1][col] == null)
			{
				// if it's on row 1 it hasn't moved and it can go 2 squares up
				if (row == 1 && board[row + 2][col] == null)
				{
					pawnMoves.add(new Move(row + 2, col));
				}
				pawnMoves.add(new Move(row + 1, col));
			}
			// TODO make it possible so it can take pieces diagonally
		}
		pawnMoves.trimToSize();
		piece.setPossibleMoves(pawnMoves.toArray(new Move[pawnMoves.size()]));
		return pawnMoves;
	}

	private ArrayList<Move> getKnightMoves(Piece piece)
	{
		ArrayList<Move> knightMoves = new ArrayList<Move>();
		int row = piece.getRow();
		int col = piece.getCol();

		// the knight can move up to 8 squares
		// getting all the 8 and checking if they did not leave the board
		// MOVE 1:
		int newRow = row - 1;
		int newCol = col - 2;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			knightMoves.add(new Move(newRow, newCol));
		}
		// MOVE 2:
		newRow = row - 2;
		newCol = col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			knightMoves.add(new Move(newRow, newCol));
		}
		// MOVE 3:
		newRow = row - 2;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			knightMoves.add(new Move(newRow, newCol));
		}
		// MOVE 4:
		newRow = row - 1;
		newCol = col + 2;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			knightMoves.add(new Move(newRow, newCol));
		}
		// MOVE 5:
		newRow = row + 1;
		newCol = col - 2;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			knightMoves.add(new Move(newRow, newCol));
		}
		// MOVE 6:
		newRow = row + 2;
		newCol = col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			knightMoves.add(new Move(newRow, newCol));
		}
		// MOVE 7:
		newRow = row + 2;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			knightMoves.add(new Move(newRow, newCol));
		}
		// MOVE 8:
		newRow = row + 1;
		newCol = col + 2;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			knightMoves.add(new Move(newRow, newCol));
		}

		knightMoves.trimToSize();
		piece.setPossibleMoves(knightMoves.toArray(new Move[knightMoves.size()]));
		return knightMoves;
	}

	private ArrayList<Move> getBishopMoves(Piece piece)
	{
		ArrayList<Move> bishopMoves = new ArrayList<Move>();
		int row = piece.getRow();
		int col = piece.getCol();

		int rowCount = row;
		int colCount = col;

		// get all the possible moves from the current square and the upper right
		// diagonal
		while (rowCount > 0 && colCount < 7 && this.board[rowCount][colCount] == null)
		{
			bishopMoves.add(new Move(--rowCount, ++colCount));
		}
		// TODO test if this works for the upper diagonal and then apply for the rest
		/*
		 * // if there's a piece there, and that piece is a different color, then it can
		 * go // there. also it should check if the king is checked after this move in
		 * the app // class if (this.board[rowCount][colCount] != null &&
		 * this.board[rowCount][colCount].isWhite()// or black make method canTake?) {
		 * bishopMoves.add(new Move(--rowCount, ++colCount)); }
		 */
		rowCount = row;
		colCount = col;
		// get all the possible moves from the current square and the upper left
		// diagonal
		while (rowCount > 0 && colCount > 0 && this.board[rowCount][colCount] == null)
		{
			bishopMoves.add(new Move(--rowCount, --colCount));
		}
		rowCount = row;
		colCount = col;
		// get all the possible moves from the current square and the bottom left
		// diagonal
		while (rowCount < 7 && colCount > 0 && this.board[rowCount][colCount] == null)
		{
			bishopMoves.add(new Move(++rowCount, --colCount));
		}
		rowCount = row;
		colCount = col;
		// get all the possible moves from the current square and the bottom right
		// diagonal
		while (rowCount < 7 && colCount < 7 && this.board[rowCount][colCount] == null)
		{
			bishopMoves.add(new Move(++rowCount, ++colCount));
		}

		// TODO make it so that it can take pieces
		bishopMoves.trimToSize();
		piece.setPossibleMoves(bishopMoves.toArray(new Move[bishopMoves.size()]));
		return bishopMoves;
	}

	private ArrayList<Move> getRookMoves(Piece piece)
	{
		ArrayList<Move> rookMoves = new ArrayList<Move>();
		int row = piece.getRow();
		int col = piece.getCol();

		// Get all the moves possible from the current col and up
		for (int i = col + 1; i < 8 && this.board[row][i] == null; i++)
		{
			rookMoves.add(new Move(row, i));
		}
		// Get all the moves possible from the current col and down
		for (int i = col - 1; i >= 0 && this.board[row][i] == null; i--)
		{
			rookMoves.add(new Move(row, i));
		}

		// Get all the moves possible from the current row and up
		for (int i = row + 1; i < 8 && this.board[i][col] == null; i++)
		{
			rookMoves.add(new Move(i, col));
		}
		// Get all the moves possible from the current row and down
		for (int i = row - 1; i >= 0 && this.board[i][col] == null; i--)
		{
			rookMoves.add(new Move(i, col));
		}

		// TODO make it so can take pieces
		rookMoves.trimToSize();
		piece.setPossibleMoves(rookMoves.toArray(new Move[rookMoves.size()]));
		return rookMoves;
	}

	private ArrayList<Move> getQueenMoves(Piece piece)
	{
		ArrayList<Move> queenMoves = new ArrayList<Move>();

		// A little trick we can do here, since the Queen legal moves is a
		// bishop legal moves + a rook legal moves
		queenMoves.addAll(getBishopMoves(piece));
		queenMoves.addAll(getRookMoves(piece));

		queenMoves.trimToSize();
		piece.setPossibleMoves(queenMoves.toArray(new Move[queenMoves.size()]));
		return queenMoves;
	}

	private ArrayList<Move> getKingMoves(Piece piece)
	{
		ArrayList<Move> kingMoves = new ArrayList<Move>();
		int row = piece.getRow();
		int col = piece.getCol();

		// The king like the knight has up to 8 moves it can go
		// MOVE 1:
		int newRow = row - 1;
		int newCol = col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if(this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));				
			}			
		}
		// MOVE 2:
		newRow = row - 1;
		newCol = col;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if(this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			}
		}
		// MOVE 3:
		newRow = row - 1;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if(this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			}
		}
		// MOVE 4:
		newRow = row;
		newCol = col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if(this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			}
		}
		// MOVE 5:
		newRow = row;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if(this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			}
		}
		// MOVE 6:
		newRow = row + 1;
		newCol = col;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if(this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			}
		}
		// MOVE 7:
		newRow = row + 1;
		newCol = col;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if(this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			}
		}
		// MOVE 8:
		newRow = row + 1;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if(this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			}
		}

		kingMoves.trimToSize();
		piece.setPossibleMoves(kingMoves.toArray(new Move[kingMoves.size()]));
		return kingMoves;
	}
	
	public boolean isMoveLegal(Piece piece, int row, int col)
	{
		Move[] possibleMoves = piece.getPossibleMoves();
		for(Move move: possibleMoves)
		{
			if(move.getRow() == row && move.getCol() == col)
			{
				return true;
			}
		}
		return false;
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
}
