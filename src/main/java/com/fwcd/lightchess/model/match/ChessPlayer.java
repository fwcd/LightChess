package com.fwcd.lightchess.model.match;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.PlayerColor;

public interface ChessPlayer {
	ChessMove pickMove(PlayerColor me, ChessBoardModel board);
}
