package fwcd.lightchess.model.match;

import fwcd.lightchess.model.PlayerColor;

public class ChessMatchResult {
	private final PlayerColor winner;
	private final ChessResultType resultType;
	
	public ChessMatchResult(PlayerColor winner, ChessResultType resultType) {
		this.winner = winner;
		this.resultType = resultType;
	}
	
	public ChessResultType getResultType() { return resultType; }
	
	public PlayerColor getWinner() { return winner; }
}
