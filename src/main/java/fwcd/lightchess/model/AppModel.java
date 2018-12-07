package fwcd.lightchess.model;

import fwcd.lightchess.model.match.MatchConfiguratorModel;
import fwcd.lightchess.model.match.MatchManager;

public class AppModel {
	private final ChessBoardModel chessBoard = ChessBoardModel.withInitialSetup();
	private final MatchManager matchManager = new MatchManager(chessBoard);
	private final MatchConfiguratorModel matchConfigurator = new MatchConfiguratorModel(matchManager);
	
	public ChessBoardModel getChessBoard() { return chessBoard; }
	
	public MatchConfiguratorModel getMatchConfigurator() { return matchConfigurator; }
	
	public MatchManager getMatchManager() { return matchManager; }
}
