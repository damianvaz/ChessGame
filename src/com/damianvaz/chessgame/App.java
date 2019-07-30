package com.damianvaz.chessgame;

import com.damianvaz.chessgame.pieces.Piece;
import com.damianvaz.chessgame.view.MainWindow;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application
{
	private MainWindow mainWindow;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		mainWindow = new MainWindow("Chess");
		primaryStage = mainWindow;

		mainWindow.addPiecesToBoard();
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
			if (piece.canPieceMoveHere(newSquareRow, newSquareCol))
			{
				piece.setRow(newSquareRow);
				piece.setCol(newSquareCol);
				piece.setTranslateX(newTranslateX);
				piece.setTranslateY(newTranslateY);
			} else // if not move piece back to original square
			{
				piece.setTranslateX(originalTranslateX);
				piece.setTranslateY(originalTranslateY);
			}

		});
	}

}
