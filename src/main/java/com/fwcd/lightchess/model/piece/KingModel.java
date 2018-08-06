package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class KingModel extends AbstractPieceModel {
	public KingModel(PlayerColor color, ChessPosition position) {
		super(color, position);
	}
	
	@Override
	protected Stream<ChessMove> getIntendedMoves(ChessBoardModel board) {
		// TODO: Castling
		Stream.Builder<ChessMove> moves = Stream.builder();
		ChessPosition origin = getPosition();
		
		// TODO: Filter moves into checks
		
		for (int dy=-1; dy<=1; dy++) {
			for (int dx=-1; dx<=1; dx++) {
				origin.plus(dx, dy)
					.filter(it -> !board.fieldAt(it).hasPieceOfColor(getColor()))
					.map(it -> new ChessMove(getType(), origin, it))
					.ifPresent(moves::add);
			}
		}
		
		return moves.build().distinct();
	}
	
	public boolean isChecked(ChessBoardModel board) {
		return board.piecesOfColor(getColor().opponent())
			.anyMatch(it -> it.threatens(getPosition(), board));
	}
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitKing(this);
	}
	
	@Override
	public boolean threatens(ChessPosition target, ChessBoardModel board) {
		ChessPosition pos = getPosition();
		return (pos.xDistanceTo(target) <= 1) && (pos.yDistanceTo(target) <= 1);
	}
	
	@Override
	public ChessPieceType getType() { return ChessPieceType.KING; }
	
	@Override
	public ChessPieceModel copy() { return new KingModel(getColor(), getPosition()); }
}
