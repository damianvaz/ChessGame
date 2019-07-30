package com.damianvaz.chessgame.view;

import java.util.EventListener;

/**
 * Interface that allows App Class to listen to Main Window
 * 
 */
public interface MainWindowListener extends EventListener
{
	public void pieceMoved(PieceMovedEvent e);
}
