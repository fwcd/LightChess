package com.fwcd.lightchess.model;

import com.fwcd.lightchess.model.match.MatchConfiguratorModel;
import com.fwcd.lightchess.model.match.MatchManager;

public class AppModel {
	private final ChessBoardModel chessBoard = ChessBoardModel.withInitialSetup();
	private final MatchManager matchManager = new MatchManager(chessBoard);
	private final MatchConfiguratorModel matchConfigurator = new MatchConfiguratorModel(matchManager);
	
	public ChessBoardModel getChessBoard() { return chessBoard; }
	
	public MatchConfiguratorModel getMatchConfigurator() { return matchConfigurator; }
}
