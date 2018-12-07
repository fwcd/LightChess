package fwcd.lightchess.model.match;

import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessMove;
import fwcd.lightchess.model.PlayerColor;

public interface ChessPlayer {
	ChessMove pickMove(PlayerColor me, ChessBoardModel board) throws InterruptedException;
}
