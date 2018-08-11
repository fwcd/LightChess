package com.fwcd.lightchess.utils;

import com.fwcd.lightchess.model.piece.ChessPieceType;

public final class ChessConstants {
	private ChessConstants() {}
	
	public static final ChessPieceType[] PROMOTION_PIECES = {
		ChessPieceType.BISHOP,
		ChessPieceType.KNIGHT,
		ChessPieceType.ROOK,
		ChessPieceType.QUEEN
	};
	
	public static final int RANKS = 8;
	public static final int FILES = 8;
}
