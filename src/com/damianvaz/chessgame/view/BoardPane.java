package com.damianvaz.chessgame.view;

import com.damianvaz.chessgame.pieces.Bishop;
import com.damianvaz.chessgame.pieces.King;
import com.damianvaz.chessgame.pieces.Knight;
import com.damianvaz.chessgame.pieces.Pawn;
import com.damianvaz.chessgame.pieces.Piece;
import com.damianvaz.chessgame.pieces.Queen;
import com.damianvaz.chessgame.pieces.Rook;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class BoardPane extends GridPane
{
	private BorderPane[][] squares = new BorderPane[8][8];
	//TODO change Squares from panes to backgroing img
	private String color = "white;";
	private int squareSide = 80;
	private double orgSceneX, orgSceneY;
	private double originalTranslateX, originalTranslateY;
	private BoardPaneListener listener;

	public BoardPane()
	{
		// getStylesheets().add("/css/style.css");
		makeSquares();
	}

	private void makeSquares()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				squares[i][j] = new BorderPane();
				squares[i][j].setMinHeight(squareSide);
				squares[i][j].setMinWidth(squareSide);
				squares[i][j].setStyle("-fx-background-color: " + color);
				add(squares[i][j], j, i);
				// group.getChildren().add(squares[i][j]);
				changeColor();
			}
			changeColor();
		}
		this.toBack();

	}

	private void changeColor()
	{
		if (color == "white;")
		{
			color = "black;";
		} else
		{
			color = "white;";
		}
	}

	public void addPiecesToBoard(Piece[] whitePieces, Piece[] blackPieces)
	{
		for(Piece piece : whitePieces)
		{
			addPieceToBoardView(piece);
		}
		for(Piece piece : blackPieces)
		{
			addPieceToBoardView(piece);
		}
	}
	private void addPieceToBoardView(Piece piece)
	{
		piece.setOnMousePressed(pieceOnMousePressedEventHandler);
		piece.setOnMouseDragged(pieceOnMouseDraggedEventHandler);
		piece.setOnMouseReleased(pieceOnMouseReleasedEventHandler);
		
		setHalignment(piece, HPos.CENTER);
		add(piece, piece.getCol(), piece.getRow());
		
	}

	EventHandler<MouseEvent> pieceOnMousePressedEventHandler = new EventHandler<MouseEvent>()
	{

		@Override
		public void handle(MouseEvent e)
		{
			Piece source = (Piece) e.getSource();
			orgSceneX = e.getSceneX();
			orgSceneY = e.getSceneY();
			originalTranslateX = source.getTranslateX();
			originalTranslateY = source.getTranslateY();
			// sourceSquare = squares[source.getRow()][source.getCol()];
		}
	};

	EventHandler<MouseEvent> pieceOnMouseReleasedEventHandler = new EventHandler<MouseEvent>()
	{

		@Override
		public void handle(MouseEvent e)
		{
			Piece source = (Piece) e.getSource();
			int newSquareRow = (int) Math.ceil(e.getSceneY() / squareSide) - 1;
			int newSquareCol = (int) Math.ceil(e.getSceneX() / squareSide) - 1;
			int originalSquareRow = (int) Math.ceil(orgSceneY / squareSide) - 1;
			int originalSquareCol = (int) Math.ceil(orgSceneX / squareSide) - 1;

			System.out.println("New Pos: (" + newSquareRow + ", " + newSquareCol + ") " + "Old Pos: ("
					+ originalSquareRow + ", " + originalSquareCol + ")");
			// TODO remove the syso
			double offsetY = newSquareRow * squareSide - originalSquareRow * squareSide;
			double offsetX = newSquareCol * squareSide - originalSquareCol * squareSide;
			double newTranslateX = originalTranslateX + offsetX;
			double newTranslateY = originalTranslateY + offsetY;
			if (listener != null)
			{
				PieceMovedEvent event = new PieceMovedEvent(source, newSquareRow, newSquareCol, originalTranslateX,
						originalTranslateY, newTranslateX, newTranslateY);
				listener.PieceMoved(event);
			}
		}
	};

	EventHandler<MouseEvent> pieceOnMouseDraggedEventHandler = new EventHandler<MouseEvent>()
	{

		@Override
		public void handle(MouseEvent e)
		{
			Piece source = (Piece) e.getSource();
			double offsetX = e.getSceneX() - orgSceneX;
			double offsetY = e.getSceneY() - orgSceneY;
			double newTranslateX = originalTranslateX + offsetX;
			double newTranslateY = originalTranslateY + offsetY;

			source.setTranslateX(newTranslateX);
			source.setTranslateY(newTranslateY);

			// System.out.println("X: " + newTranslateX + " Y: " + newTranslateY);
		}
	};

	public void setListener(BoardPaneListener listener)
	{
		this.listener = listener;
	}

	public void eatPiece(Piece piece)
	{
		getChildren().remove(piece);
	}
	
	public void promotePawn(Piece pawn, Queen queen)
	{
		int row = pawn.getRow();
		int col = pawn.getCol();
		boolean isWhite = pawn.isWhite();
		
		getChildren().remove(pawn);
		addPieceToBoardView(queen);
	}
}
