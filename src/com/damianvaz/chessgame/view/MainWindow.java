package com.damianvaz.chessgame.view;

import com.damianvaz.chessgame.pieces.Piece;
import com.damianvaz.chessgame.pieces.Queen;
import com.damianvaz.chessgame.pieces.Rook;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Stage
{
	private BoardPane boardPane;
	private MainWindowListener mainWindowListener;

	public MainWindow(String title)
	{
		setTitle(title);
		boardPane = new BoardPane();

		Scene scene = new Scene(boardPane);
		setScene(scene);
		show();
	}

	public void addPiecesToBoard(Piece[] whitePieces, Piece[] blackPieces)
	{
		boardPane.addPiecesToBoard(whitePieces, blackPieces);
	}

	public void setListener(MainWindowListener listener)
	{
		this.mainWindowListener = listener;
		boardPane.setListener(e ->
		{
			mainWindowListener.pieceMoved(e);
		});
	}

	public void eatPiece(Piece piece)
	{
		boardPane.eatPiece(piece);
	}

	public void promotePawn(Piece pawn, Queen newQueen)
	{
		boardPane.promotePawn(pawn, newQueen);
	}

	public void kingSideCastle(Rook rook)
	{
		boardPane.kingSideCastle(rook);
	}

	public void queenSideCastle(Rook rook)
	{
		boardPane.queenSideCastle(rook);
		
	}
}
