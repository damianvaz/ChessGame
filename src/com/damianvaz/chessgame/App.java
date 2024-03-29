package com.damianvaz.chessgame;

import com.damianvaz.chessgame.pieces.Bishop;
import com.damianvaz.chessgame.pieces.King;
import com.damianvaz.chessgame.pieces.Knight;
import com.damianvaz.chessgame.pieces.Pawn;
import com.damianvaz.chessgame.pieces.Piece;
import com.damianvaz.chessgame.pieces.Queen;
import com.damianvaz.chessgame.pieces.Rook;
import com.damianvaz.chessgame.view.MainWindow;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application
{
	private MainWindow mainWindow;
	private Board board;
	private Piece[] whitePieces, blackPieces;
	private boolean isWhitesMove;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		mainWindow = new MainWindow("Chess");
		primaryStage = mainWindow;
		isWhitesMove = true;
		// TODO check if it's really necessary to have white's and black's pieces as
		// instance variables
		// Piece[] whitePieces = makeWhitePieces();
		// Piece[] blackPieces = makeBlackPieces();
		makeAllPieces();
		board = new Board(whitePieces, blackPieces);
		board.getAllPossibleMoves(isWhitesMove); // populate pieces move array
		mainWindow.addPiecesToBoard(whitePieces, blackPieces);
		mainWindow.setListener(e ->
		{
			Piece piece = e.getPiece();
			int newSquareRow = e.getNewSquareRow();
			int newSquareCol = e.getNewSquareCol();
			double originalTranslateX = e.getOriginalTranslateX();
			double originalTranslateY = e.getOriginalTranslateY();
			double newTranslateX = e.getNewTranslateX();
			double newTranslateY = e.getNewTranslateY();

			if (board.isMoveLegal(piece, newSquareRow, newSquareCol, isWhitesMove))
			{
				// check if there's a piece here, and if there is, take it
				Piece maybePiece = board.getPieceOnSquare(newSquareRow, newSquareCol);
				if (maybePiece != null)
				{
					mainWindow.eatPiece(maybePiece);
				}
				
				int originalRow = piece.getRow();
				int originalCol = piece.getCol();
				boolean squareEmpty = (board.getPieceOnSquare(newSquareRow, newSquareCol) == null) ? true : false;
				setPieceOnBoard(newSquareRow, newSquareCol, piece, newTranslateX, newTranslateY);

				if (piece.getName() == "Pawn")
				{
					Pawn pawn = (Pawn) piece;
					checkPromotingPawn(pawn, newSquareRow);
					
					// check En passant
					if(((piece.isWhite() && originalRow == 3) || (!piece.isWhite() && originalRow == 4)) && squareEmpty)
					{
						checkEnPassant(pawn, newSquareRow, newSquareCol, originalCol);						
					}
				}

				if (piece.getName() == "King")
				{
					King king = (King) piece;
					if (!king.HasMoved())
					{
						king.setHasMoved(true);
						checkIsCastling(king, newSquareCol);
					}
				}

				if (piece.getName() == "Rook")
				{
					Rook rook = (Rook) piece;
					if (!rook.hasMoved())
					{
						rook.setHasMoved(true);
					}
				}
				board.getAllPossibleMoves(isWhitesMove); // To check if king is checked
				changePiecesToMove();
				board.getAllPossibleMoves(isWhitesMove); // to get all the possible moves of the next movement

			} else // if not move piece back to original square
			{
				piece.setTranslateX(originalTranslateX);
				piece.setTranslateY(originalTranslateY);

			}

		});
	}

	private void checkIsCastling(King king, int newSquareCol)
	{
		if (king.isWhite())
		{
			if (newSquareCol == 6)
			{
				Rook rook = (Rook) board.getPieceOnSquare(7, 7);
				board.emptySquare(rook.getRow(), rook.getCol());
				mainWindow.kingSideCastle(rook);
				board.fillSquare(rook);
				System.out.println("" + king.getCol());
			}
			if (newSquareCol == 2)
			{
				Rook rook = (Rook) board.getPieceOnSquare(7, 0);
				board.emptySquare(rook.getRow(), rook.getCol());
				mainWindow.queenSideCastle(rook);
				board.fillSquare(rook);
			}
		} else
		{
			if (newSquareCol == 6)
			{
				Rook rook = (Rook) board.getPieceOnSquare(0, 7);
				board.emptySquare(rook.getRow(), rook.getCol());
				mainWindow.kingSideCastle(rook);
				board.fillSquare(rook);
			}
			if (newSquareCol == 2)
			{
				Rook rook = (Rook) board.getPieceOnSquare(0, 0);
				board.emptySquare(rook.getRow(), rook.getCol());
				mainWindow.queenSideCastle(rook);
				board.fillSquare(rook);
			}
		}
		
	}

	private void checkEnPassant(Pawn pawn, int newSquareRow, int newSquareCol, int originalCol)
	{
		if (pawn.isWhite())
		{
			if ((newSquareCol == originalCol - 1 || newSquareCol == originalCol + 1))
			{
				Piece enPassantPawn = board.getPieceOnSquare(newSquareRow + 1, newSquareCol);
				if (enPassantPawn != null)
				{
					mainWindow.eatPiece(enPassantPawn);
				}
			}
		} else
		{
			if ((newSquareCol == originalCol - 1 || newSquareCol == originalCol + 1))
			{
				Piece enPassantPawn = board.getPieceOnSquare(newSquareRow - 1, newSquareCol);
				if (enPassantPawn != null)
				{
					mainWindow.eatPiece(enPassantPawn);
				}
			}
		}
		
	}

	private void setPieceOnBoard(int newRow, int newCol, Piece piece, double newTranslateX, double newTranslateY)
	{
		board.emptySquare(piece.getRow(), piece.getCol());
		piece.setRow(newRow);
		piece.setCol(newCol);
		board.fillSquare(piece);
		piece.setTranslateX(newTranslateX);
		piece.setTranslateY(newTranslateY);
		board.setLastMove(new Move(newRow, newCol));
	}
	
	private void checkPromotingPawn(Pawn pawn, int row)
	{
		if (pawn.isWhite() && row == 0)
		{
			pawn.setRow(row);
			Queen newQueen = board.promotePawn(pawn);
			mainWindow.promotePawn(pawn, newQueen);
		} else if (!pawn.isWhite() && row == 7)
		{
			pawn.setRow(row);
			Queen newQueen = board.promotePawn(pawn);
			mainWindow.promotePawn(pawn, newQueen);
		}
	}

	private void changePiecesToMove()
	{
		if (isWhitesMove)
		{
			isWhitesMove = false;
		} else
		{
			isWhitesMove = true;
		}

	}

	private void makeAllPieces()
	{
		makeBlackPieces();
		makeWhitePieces();
	}

	private void makeBlackPieces()
	{
		Piece[] blackPieces = new Piece[16]; // 16 with all the pieces
		blackPieces[0] = new Pawn(1, 0, false);
		blackPieces[1] = new Pawn(1, 1, false);
		blackPieces[2] = new Pawn(1, 2, false);
		blackPieces[3] = new Pawn(1, 3, false);
		blackPieces[4] = new Pawn(1, 4, false);
		blackPieces[5] = new Pawn(1, 5, false);
		blackPieces[6] = new Pawn(1, 6, false);
		blackPieces[7] = new Pawn(1, 7, false);
		blackPieces[8] = new Rook(0, 7, false);
		blackPieces[9] = new Knight(0, 6, false);
		blackPieces[10] = new Bishop(0, 5, false);
		blackPieces[12] = new King(0, 4, false);
		blackPieces[11] = new Queen(0, 3, false);
		blackPieces[13] = new Bishop(0, 2, false);
		blackPieces[14] = new Knight(0, 1, false);
		blackPieces[15] = new Rook(0, 0, false);

		this.blackPieces = blackPieces;
	}

	private void makeWhitePieces()
	{
		Piece[] whitePieces = new Piece[16]; // 16 with all the pieces
		whitePieces[0] = new Pawn(6, 0, true);
		whitePieces[1] = new Pawn(6, 1, true);
		whitePieces[2] = new Pawn(6, 2, true);
		whitePieces[3] = new Pawn(6, 3, true);
		whitePieces[4] = new Pawn(6, 4, true);
		whitePieces[5] = new Pawn(6, 5, true);
		whitePieces[6] = new Pawn(6, 6, true);
		whitePieces[7] = new Pawn(6, 7, true);
		whitePieces[8] = new Rook(7, 7, true);
		whitePieces[9] = new Knight(7, 6, true);
		whitePieces[10] = new Bishop(7, 5, true);
		whitePieces[11] = new King(7, 4, true);
		whitePieces[12] = new Queen(7, 3, true);
		whitePieces[13] = new Bishop(7, 2, true);
		whitePieces[14] = new Knight(7, 1, true);
		whitePieces[15] = new Rook(7, 0, true);
		this.whitePieces = whitePieces;
	}

}
