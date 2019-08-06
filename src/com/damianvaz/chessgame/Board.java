package com.damianvaz.chessgame;

import java.util.ArrayList;

import com.damianvaz.chessgame.pieces.Bishop;
import com.damianvaz.chessgame.pieces.King;
import com.damianvaz.chessgame.pieces.Knight;
import com.damianvaz.chessgame.pieces.Pawn;
import com.damianvaz.chessgame.pieces.Piece;
import com.damianvaz.chessgame.pieces.Queen;
import com.damianvaz.chessgame.pieces.Rook;
import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

public class Board
{
	private Piece[] whitePieces;
	private Piece[] blackPieces;
	private King whiteKing, blackKing;

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
		
		whiteKing = getWhiteKing();
		blackKing = getBlackKing();		
		
	}

	private King getBlackKing()
	{
		for (int i = 0; i < blackPieces.length; i++)
		{
			if(blackPieces[i].getName() == "King")
			{
				return (King) blackPieces[i];
			}
		}
		return null;
	}
	
	private King getWhiteKing()
	{
		for (int i = 0; i < whitePieces.length; i++)
		{
			if(whitePieces[i].getName() == "King")
			{
				return (King) whitePieces[i];
			}
		}
		return null;
	}

	public void setPieces(Piece[] whitePieces, Piece[] blackPieces)
	{
		this.whitePieces = whitePieces;
		this.blackPieces = blackPieces;
		whiteKing = (King) whitePieces[11];
		blackKing = (King) blackPieces[12];
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
		isKingCheck(moves, isWhitesMove);
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
			// testing if there's any piece it can take
			if (row > 0 && col < 7)
			{
				// if there's a piece on the right diagonal square
				if (this.board[row - 1][col + 1] != null)
				{
					Piece anotherPiece = this.board[row - 1][col + 1];
					if (canTakePiece(piece, anotherPiece))
					{
						pawnMoves.add(new Move(row - 1, col + 1));
					}
				}
			}
			if (row > 0 && col > 0)
			{
				// if there's a piece on the left diagonal square
				if (this.board[row - 1][col - 1] != null)
				{
					Piece anotherPiece = this.board[row - 1][col - 1];
					if (canTakePiece(piece, anotherPiece))
					{
						pawnMoves.add(new Move(row - 1, col - 1));
					}
				}
			}

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
			// testing if there's any piece it can take
			if (row < 7 && col < 7)
			{
				// if there's a piece on the right diagonal square
				if (this.board[row + 1][col + 1] != null)
				{
					Piece anotherPiece = this.board[row + 1][col + 1];
					if (canTakePiece(piece, anotherPiece))
					{
						pawnMoves.add(new Move(row + 1, col + 1));
					}
				}
			}
			if (row < 7 && col > 0)
			{
				// if there's a piece on the left diagonal square
				if (this.board[row + 1][col - 1] != null)
				{
					Piece anotherPiece = this.board[row + 1][col - 1];
					if (canTakePiece(piece, anotherPiece))
					{
						pawnMoves.add(new Move(row + 1, col - 1));
					}
				}
			}

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

	public void isKingCheck(Move[] moves, boolean isWhite)
	{
		for (Move move : moves)
		{
			if (isWhite)
			{
				//int row = blackKing.getRow
				if(blackKing.getRow() == move.getRow() && blackKing.getCol() == move.getCol())
				{
					blackKing.setCheck(true);
					System.out.println("Black king is checked");
					// TODO delete syso
					break;
				} else
				{
					blackKing.setCheck(false);
				}
			} else
			{
				if(whiteKing.getRow() == move.getRow() && whiteKing.getCol() == move.getCol())
				{
					whiteKing.setCheck(true);
					System.out.println("White King is checked");
					// TODO delete syso
					break;
				} else
				{
					whiteKing.setCheck(false);
				}
			}
		}
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
			if (this.board[newRow][newCol] == null)
			{
				knightMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					knightMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 2:
		newRow = row - 2;
		newCol = col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				knightMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					knightMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 3:
		newRow = row - 2;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				knightMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					knightMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 4:
		newRow = row - 1;
		newCol = col + 2;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				knightMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					knightMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 5:
		newRow = row + 1;
		newCol = col - 2;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				knightMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					knightMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 6:
		newRow = row + 2;
		newCol = col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				knightMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					knightMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 7:
		newRow = row + 2;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				knightMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					knightMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 8:
		newRow = row + 1;
		newCol = col + 2;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				knightMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					knightMoves.add(new Move(newRow, newCol));
				}
			}
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
		

		while (rowCount > 0 && colCount < 7)
		{
			if (this.board[--rowCount][++colCount] == null)
			{
				bishopMoves.add(new Move(rowCount, colCount));
			} else
			{
				Piece anotherPiece = this.board[rowCount][colCount]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					bishopMoves.add(new Move(rowCount, colCount));
				}
				break;
			}
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

		while (rowCount > 0 && colCount > 0)
		{
			if (this.board[--rowCount][--colCount] == null)
			{
				bishopMoves.add(new Move(rowCount, colCount));
			} else
			{
				Piece anotherPiece = this.board[rowCount][colCount]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					bishopMoves.add(new Move(rowCount, colCount));
				}
				break;
			}
		}

		rowCount = row;
		colCount = col;
		// get all the possible moves from the current square and the bottom left
		// diagonal

		while (rowCount < 7 && colCount > 0)
		{
			if (this.board[++rowCount][--colCount] == null)
			{
				bishopMoves.add(new Move(rowCount, colCount));
			} else
			{
				Piece anotherPiece = this.board[rowCount][colCount]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					bishopMoves.add(new Move(rowCount, colCount));
				}
				break;
			}
		}

		rowCount = row;
		colCount = col;
		// get all the possible moves from the current square and the bottom right
		// diagonal

		while (rowCount < 7 && colCount < 7)
		{
			if (this.board[++rowCount][++colCount] == null)
			{
				bishopMoves.add(new Move(rowCount, colCount));
			} else
			{
				Piece anotherPiece = this.board[rowCount][colCount]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					bishopMoves.add(new Move(rowCount, colCount));
				}
				break;
			}
		}

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
		for (int i = col + 1; i < 8; i++)
		{
			if (this.board[row][i] != null)
			{
				Piece anotherPiece = this.board[row][i];
				if (canTakePiece(piece, anotherPiece))
				{
					rookMoves.add(new Move(row, i));
				}
				break;
			}
			rookMoves.add(new Move(row, i));
		}
		// Get all the moves possible from the current col and down
		for (int i = col - 1; i >= 0; i--)
		{
			if (this.board[row][i] != null)
			{
				Piece anotherPiece = this.board[row][i];
				if (canTakePiece(piece, anotherPiece))
				{
					rookMoves.add(new Move(row, i));
				}
				break;
			}
			rookMoves.add(new Move(row, i));
		}

		// Get all the moves possible from the current row and up
		for (int i = row + 1; i < 8; i++)
		{
			if (this.board[i][col] != null)
			{
				Piece anotherPiece = this.board[i][col];
				if (canTakePiece(piece, anotherPiece))
				{
					rookMoves.add(new Move(i, col));
				}
				break;
			}
			rookMoves.add(new Move(i, col));
		}
		// Get all the moves possible from the current row and down
		for (int i = row - 1; i >= 0; i--)
		{
			if (this.board[i][col] != null)
			{
				Piece anotherPiece = this.board[i][col];
				if (canTakePiece(piece, anotherPiece))
				{
					rookMoves.add(new Move(i, col));
				}
				break;
			}
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
			if (this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					kingMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 2:
		newRow = row - 1;
		newCol = col;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					kingMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 3:
		newRow = row - 1;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					kingMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 4:
		newRow = row;
		newCol = col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					kingMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 5:
		newRow = row;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					kingMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 6:
		newRow = row + 1;
		newCol = col - 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					kingMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 7:
		newRow = row + 1;
		newCol = col;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					kingMoves.add(new Move(newRow, newCol));
				}
			}
		}
		// MOVE 8:
		newRow = row + 1;
		newCol = col + 1;
		if (checkIsStillOnTheBoard(newRow, newCol))
		{
			if (this.board[newRow][newCol] == null)
			{
				kingMoves.add(new Move(newRow, newCol));
			} else
			{
				Piece anotherPiece = this.board[newRow][newCol]; // there's maybe a piece here
				if (canTakePiece(piece, anotherPiece))
				{
					kingMoves.add(new Move(newRow, newCol));
				}
			}
		}

		kingMoves.trimToSize();
		piece.setPossibleMoves(kingMoves.toArray(new Move[kingMoves.size()]));
		return kingMoves;
	}

	public boolean isMoveLegal(Piece piece, int row, int col, boolean isWhitesMove)
	{
		int originalRow = piece.getRow();
		int originalCol = piece.getCol();
		Board maybeBoard = copyBoard(this);
		Piece maybeBoardPiece = maybeBoard.getPieceOnSquare(piece.getRow(), piece.getCol());
		
		Piece maybePiece = maybeBoard.board[row][col];
		if(maybePiece != null)
		{
			if(maybePiece.isWhite())
			{
				// TODO delete sysos
				System.out.println("PIECE TO BE TAKEN IS WHITE");
			} else
			{
				System.out.println("PIECE TO BE TAKEN IS BLACK");
			}
		}
		if(maybeBoardPiece.isWhite())
		{
			System.out.println("PIECE TO TAKE IS WHITE");
		} else
		{
			System.out.println("PIECE TO TAKE IS BLACK");
		}
		if(maybeBoard.board[row][col] != null && canTakePiece(maybeBoardPiece, maybePiece))
		{
			maybeBoard.removePiece(maybePiece);
			System.out.println("ALO LAO Marciano98");
		}
		
		
		maybeBoard.emptySquare(maybeBoardPiece.getRow(), maybeBoardPiece.getCol());
		
		maybeBoardPiece.setRow(row);
		maybeBoardPiece.setCol(col);
		maybeBoard.fillSquare(maybeBoardPiece);
		maybeBoard.getAllPossibleMoves(!isWhitesMove);
		
		if(maybeBoard.isKingChecked(isWhitesMove))
		{
			System.out.println("Because king would still be checked");
			return false;
		}
		maybeBoardPiece.setRow(originalRow);
		maybeBoardPiece.setCol(originalCol);
		
		//iece.setTranslateX(newTranslateX);
		//piece.setTranslateY(newTranslateY);
		
		
		Move[] possibleMoves = piece.getPossibleMoves();
	
		if (possibleMoves != null && piece.isWhite() == isWhitesMove)
		{
			for (Move move : possibleMoves)
			{
				if (move.getRow() == row && move.getCol() == col)
				{
					if (this.board[row][col] == null || this.board[row][col].isWhite() ^ piece.isWhite())
					{
						if ( this.board[row][col] != null)
						{
							Piece removePiece = this.board[row][col];
							removePiece(removePiece);
						}
						// TODO remove
						return true;
					} else
					{
						return false;
					}
				}
			}
		}
		
		return false;
	}
	
	public Board copyBoard(Board originalBoard)
	{
		int whitePiecesLenght = originalBoard.getWhitePieces().length;
		int blackPiecesLenght = originalBoard.getBlackPieces().length;
		Piece[] originalBoardWhitePieces = originalBoard.getWhitePieces();
		Piece[] originalBoardBlackPieces = originalBoard.getBlackPieces();
		Piece[] newBoardWhitePieces = new Piece[whitePiecesLenght];
		Piece[] newBoardBlackPieces = new Piece[blackPiecesLenght];
		for(int i = 0; i < whitePiecesLenght; i++)
		{
			newBoardWhitePieces[i] = createPiece(originalBoardWhitePieces[i]);
		}
		for(int i = 0; i < blackPiecesLenght; i++)
		{
			newBoardBlackPieces[i] = createPiece(originalBoardBlackPieces[i]);
		}
		
		return new Board(newBoardWhitePieces, newBoardBlackPieces);
	}
	private Piece createPiece(Piece piece)
	{
		int row = piece.getRow();
		int col = piece.getCol();
		boolean isWhite = piece.isWhite();
		String name = piece.getName();
		
		switch(name)
		{
			case "Pawn":
			{
				return new Pawn(row, col, isWhite);
			}
			case "Rook":
			{
				return new Rook(row, col, isWhite);
			}
			case "Knight":
			{
				return new Knight(row, col, isWhite);
			}
			case "Bishop":
			{
				return new Bishop(row, col, isWhite);
			}
			case "Queen":
			{
				return new Queen(row, col, isWhite);
			}
			default:
				return new King(row, col, isWhite);
		}
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

	public void emptySquare(int row, int col)
	{
		this.board[row][col] = null;
		System.out.println("emptied square");
	}

	public void fillSquare(Piece piece)
	{
		int row = piece.getRow();
		int col = piece.getCol();
		this.board[row][col] = piece;
	}
	
	private boolean canTakePiece(Piece piece, Piece maybePiece)
	{
		if (maybePiece != null)
		{
			return piece.isWhite() ^ maybePiece.isWhite();
		} else
		{
			return false;
		}
	}

	public Piece getPieceOnSquare(int row, int col)
	{
		return this.board[row][col];
	}

	public Queen promotePawn(Piece pawn)
	{

		int row = pawn.getRow();
		int col = pawn.getCol();
		boolean isWhite = pawn.isWhite();
		
		Queen newQueen = new Queen(row, col, isWhite);
		this.board[row][col] = newQueen;
		if(isWhite)
		{
			// whitePieces[col] os where the pawn to be promoted is stored
			whitePieces[col] = newQueen;
		}
		else
		{
			// blackPieces[col] os where the pawn to be promoted is stored
			blackPieces[col] = newQueen;
		}
		return newQueen;
	}

	public boolean isKingChecked(boolean iswhite)
	{
		if (iswhite)
		{
			return whiteKing.isCheck();
		}
		else
		{
			return blackKing.isCheck();
		}
	}
	
	public Piece[] getWhitePieces()
	{
		return whitePieces;
	}
	
	public Piece[] getBlackPieces()
	{
		return blackPieces;
	}
	
	public void removePiece(Piece removePiece)
	{
		if (removePiece.isWhite())
		{
			int lenght = whitePieces.length;
			Piece[] newWhitePieces = new Piece[whitePieces.length - 1];
			King newKing = whiteKing;
			boolean reached = false;
			for (int i = 0; i < lenght - 1; i++)
			{
				
				if(removePiece.equals(whitePieces[i]))
				{
					reached = true;
				}
				if (reached)
				{
					newWhitePieces[i] = whitePieces[i+1];
					if (newWhitePieces[i].getName() == "King")
					{
						newKing = (King) newWhitePieces[i];
						System.out.println("New White King");
					}
				}
				else
				{
					newWhitePieces[i] = whitePieces[i];
				}
			}
			whitePieces = newWhitePieces;
			whiteKing = newKing;
		}
		else
		{
			int lenght = blackPieces.length;
			Piece[] newBlackPieces = new Piece[blackPieces.length - 1];
			King newKing = blackKing;
			boolean reached = false;
			for (int i = 0; i < lenght - 1; i++)
			{
				
				if(removePiece.equals(blackPieces[i]))
				{
					reached = true;
				}
				if (reached)
				{
					newBlackPieces[i] = blackPieces[i+1];
					if (newBlackPieces[i].getName() == "King")
					{
						newKing = (King) newBlackPieces[i];
						System.out.println("New King");
					}
					
				}
				else
				{
					newBlackPieces[i] = blackPieces[i];
				}
			}
			blackPieces = newBlackPieces;
			blackKing = newKing;
		}
	}
	public void printPieces()
	{
		System.out.println("White Pieces: ");
		for(int i = 0; i < whitePieces.length; i++)
		{
			System.out.println(whitePieces[i].getName() + ", id: " + i);
			if(whitePieces[i] instanceof King)
			{
				System.out.println("This is the King");
			}
		}
		System.out.println("Black Pieces: ");
		for(int i = 0; i < blackPieces.length; i++)
		{
			System.out.println(blackPieces[i].getName() + ", id: " + i);
			if(blackPieces[i] instanceof King)
			{
				System.out.println("This is the King");
			}
		}

	}
}
