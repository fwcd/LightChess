package com.fwcd.lightchess.model;

public class AppModel {
	private final ChessBoardModel chessBoard = ChessBoardModel.withInitialSetup();
	private final MatchConfiguratorModel matchConfigurator = new MatchConfiguratorModel();
	
	public ChessBoardModel getChessBoard() { return chessBoard; }
	
	public MatchConfiguratorModel getMatchConfigurator() { return matchConfigurator; }
}
