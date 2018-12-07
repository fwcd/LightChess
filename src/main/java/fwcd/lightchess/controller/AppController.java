package fwcd.lightchess.controller;

import fwcd.lightchess.model.AppModel;
import fwcd.lightchess.model.ai.AlphaBetaPlayer;
import fwcd.lightchess.view.AppView;

public class AppController {
	private final ChessBoardController chessBoard;
	
	public AppController(AppModel model, AppView view) {
		chessBoard = new ChessBoardController(model.getChessBoard(), view.getChessBoard());
		new MatchConfiguratorController(model.getMatchConfigurator(), view.getMatchConfigurator());
		
		model.getMatchConfigurator().getPlayerFactory().put("Human", () -> new HumanPlayer(chessBoard));
		model.getMatchConfigurator().getPlayerFactory().put("AlphaBeta", () -> new AlphaBetaPlayer(/* depth*/ 2));
	}
}
