package com.fwcd.lightchess.controller;

import com.fwcd.lightchess.model.AppModel;
import com.fwcd.lightchess.view.AppView;
import com.fwcd.lightchess.view.match.HumanPlayer;

public class AppController {
	public AppController(AppModel model, AppView view) {
		ChessBoardController chessBoard = new ChessBoardController(model.getChessBoard(), view.getChessBoard());
		view.getNewMatchPanel().addPlayer("Human", () -> new HumanPlayer(chessBoard));
	}
}
