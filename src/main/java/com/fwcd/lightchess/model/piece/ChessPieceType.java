package com.fwcd.lightchess.model.piece;

import java.util.function.BiFunction;

import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public enum ChessPieceType {
	ROOK(RookModel::new),
	KNIGHT(KnightModel::new),
	BISHOP(BishopModel::new),
	QUEEN(QueenModel::new),
	KING(KingModel::new),
	PAWN(PawnModel::new);
	
	private final BiFunction<PlayerColor, ChessPosition, ChessPieceModel> constructor;
	
	private ChessPieceType(BiFunction<PlayerColor, ChessPosition, ChessPieceModel> constructor) {
		this.constructor = constructor;
	}
	
	public ChessPieceModel construct(PlayerColor color, ChessPosition position) {
		return constructor.apply(color, position);
	}
}
