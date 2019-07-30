package com.damianvaz.chessgame.view;

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

	public void addPiecesToBoard()
	{
		boardPane.addPiecesToBoard();
	}

	public void setListener(MainWindowListener listener)
	{
		this.mainWindowListener = listener;
		boardPane.setListener(e ->
		{
			mainWindowListener.pieceMoved(e);
		});
	}
}
