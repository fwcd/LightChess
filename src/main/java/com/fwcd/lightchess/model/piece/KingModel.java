package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class KingModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public KingModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public Stream<ChessMove> getPossibleMoves(ChessPosition origin, ChessBoardModel board) {
		// TODO: Castling
		Stream.Builder<ChessMove> moves = Stream.builder();
		
		for (int dy=-1; dy<=1; dy++) {
			for (int dx=-1; dx<=1; dx++) {
				origin.plus(dx, dy)
					.filter(it -> !board.fieldAt(it).hasPieceOfColor(color))
					.map(it -> new ChessMove(this, origin, it))
					.ifPresent(moves::add);
			}
		}
		
		return moves.build().distinct();
	}
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitKing(this);
	}
}
