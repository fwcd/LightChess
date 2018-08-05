package com.fwcd.lightchess.model;

import com.fwcd.fructose.game.GameMove;

public interface ChessPlayer {
	GameMove pickMove(ChessBoardModel board);
}
