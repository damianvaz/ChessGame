package com.damianvaz.chessgame.view;

/**
 * This Interface Allows for the MainWindow to listen to BoardPane class
 * 
 * 
 */
public interface BoardPaneListener
{
	public void PieceMoved(PieceMovedEvent e);
}
