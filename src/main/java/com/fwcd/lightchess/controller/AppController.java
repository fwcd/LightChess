package com.fwcd.lightchess.controller;

import com.fwcd.lightchess.model.AppModel;
import com.fwcd.lightchess.model.ai.AlphaBetaPlayer;
import com.fwcd.lightchess.view.AppView;

public class AppController {
	private final ChessBoardController chessBoard;
	
	public AppController(AppModel model, AppView view) {
		chessBoard = new ChessBoardController(model.getChessBoard(), view.getChessBoard());
		new MatchConfiguratorController(model.getMatchConfigurator(), view.getMatchConfigurator());
		
		model.getMatchConfigurator().getPlayerFactory().put("Human", () -> new HumanPlayer(chessBoard));
		model.getMatchConfigurator().getPlayerFactory().put("AlphaBeta", () -> new AlphaBetaPlayer(/* depth*/ 2));
	}
}
