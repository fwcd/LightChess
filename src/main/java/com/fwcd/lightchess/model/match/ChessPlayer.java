package com.fwcd.lightchess.model.match;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;

public interface ChessPlayer {
	ChessMove pickMove(ChessBoardModel board);
}
