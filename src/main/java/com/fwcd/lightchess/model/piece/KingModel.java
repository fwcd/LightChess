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
	public Stream<ChessMove> getPossibleMoves(ChessBoardModel board) {
		// TODO: Castling
		Stream.Builder<ChessMove> moves = Stream.builder();
		ChessPosition origin = getPosition();
		
		for (int dy=-1; dy<=1; dy++) {
			for (int dx=-1; dx<=1; dx++) {
				origin.plus(dx, dy)
					.filter(it -> !board.fieldAt(it).hasPieceOfColor(getColor()))
					.map(it -> new ChessMove(this, origin, it))
					.ifPresent(moves::add);
			}
		}
		
		return moves.build().distinct();
	}
	
	public boolean isChecked(ChessBoardModel board) {
		return board.piecesOfColor(getColor().opponent())
			.filter(it -> it.threatens(this, board))
			.findAny()
			.isPresent();
			
	}
	
	public boolean isCheckmate(ChessBoardModel board) {
		return isChecked(board) && !canMove(board);
	}
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitKing(this);
	}
	
	@Override
	public ChessPieceType getType() { return ChessPieceType.KING; }
}
