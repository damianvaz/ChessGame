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

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		mainWindow = new MainWindow("Chess");
		primaryStage = mainWindow;
		//TODO check if it's really necessary to have white's and black's pieces as instanve variables 
		//Piece[] whitePieces = makeWhitePieces();
		//Piece[] blackPieces = makeBlackPieces();
		makeAllPieces();
		board = new Board(whitePieces, blackPieces);
		
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

			// if is a square that the piece can go, (not considering blocking pieces or if
			// it's a legal move) move the piece there both in view and in piece values
			// if (piece.canPieceMoveHere(newSquareRow, newSquareCol))
			if (board.isMoveLegal(piece, newSquareRow, newSquareCol))
			{
				board.emptySquare(piece.getRow(), piece.getCol());
				piece.setRow(newSquareRow);
				piece.setCol(newSquareCol);
				board.fillSquare(piece);
				piece.setTranslateX(newTranslateX);
				piece.setTranslateY(newTranslateY);
				System.out.println("Moved");
			} else // if not move piece back to original square
			{
				piece.setTranslateX(originalTranslateX);
				piece.setTranslateY(originalTranslateY);
				System.out.println("didnt move");
			}
			
		});
	}
	
	private void makeAllPieces()
	{
		makeBlackPieces();
		makeWhitePieces();
	}
	private void makeBlackPieces()
	{
		// TODO Make all the white pieces
		Piece[] blackPieces = new Piece[6]; // 16 with all the pieces
		blackPieces[0] = new Pawn(1, 7, false);
		blackPieces[1] = new Rook(0, 7, false);
		blackPieces[2] = new Knight(0, 6, false);
		blackPieces[3] = new Bishop(0, 5, false);
		blackPieces[4] = new King(0, 3, false);
		blackPieces[5] = new Queen(0, 4, false);
		this.blackPieces = blackPieces;
	}

	private void makeWhitePieces()
	{
		// TODO Make all the black pieces
		Piece[] whitePieces = new Piece[6]; // 16 with all the pieces
		whitePieces[0] = new Pawn(6, 0, true);
		whitePieces[1] = new Rook(7, 7, true);
		whitePieces[2] = new Knight(7, 6, true);
		whitePieces[3] = new Bishop(7, 5, true);
		whitePieces[4] = new King(7, 4, true);
		whitePieces[5] = new Queen(7, 3, true);
		this.whitePieces = whitePieces;
	}

}
