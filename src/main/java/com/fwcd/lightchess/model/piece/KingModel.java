package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class KingModel extends AbstractPieceModel {
	private boolean moved = false;
	
	public KingModel(PlayerColor color, ChessPosition position) {
		super(color, position);
	}
	
	private KingModel(PlayerColor color, ChessPosition position, boolean moved) {
		super(color, position);
		this.moved = moved;
	}
	
	@Override
	protected Stream<ChessMove> getIntendedMoves(ChessBoardModel board) {
		Stream.Builder<ChessMove> moves = Stream.builder();
		ChessPosition origin = getPosition();
		
		if (!moved && !isChecked(board)) {
			// Calculate possible castlings
			board.rooks()
				.filter(it -> canCastleWithRook(it, board))
				.map(it -> ChessMove.createCastling(getType(), origin, castlingKingDestination(it), it.getPosition(), castlingRookDestination(it)))
				.forEach(moves::accept);
		}
		
		for (int dy=-1; dy<=1; dy++) {
			for (int dx=-1; dx<=1; dx++) {
				origin.plus(dx, dy)
					.filter(it -> !board.fieldAt(it).hasPieceOfColor(getColor()))
					.map(it -> ChessMove.create(getType(), origin, it))
					.ifPresent(moves::accept);
			}
		}
		
		return moves.build().distinct();
	}
	
	private boolean canCastleWithRook(RookModel rook, ChessBoardModel board) {
		return rook.getColor().equals(getColor())
			&& !rook.hasMoved()
			&& noFiguresBetweenThisAnd(rook, board);
	}
	
	private boolean noFiguresBetweenThisAnd(RookModel rook, ChessBoardModel board) {
		ChessPosition pos = getPosition();
		ChessPosition rookPos = rook.getPosition();
		
		if (pos.getY() != rookPos.getY()) {
			throw new IllegalStateException("Can't determine figures between king and rook on different ranks");
		}
		
		int leftX = Math.min(pos.getX(), rookPos.getX());
		int rightX = Math.max(pos.getX(), rookPos.getX());
		int y = pos.getY();
		
		for (int x=leftX+1; x<rightX; x++) {
			if (board.fieldAt(ChessPosition.at(x, y)).hasPiece()) {
				return false;
			}
		}
		return true;
	}
	
	private ChessPosition castlingKingDestination(RookModel rook) {
		if (rook.isInLeftCorner()) {
			return getPosition().left(2).orElseThrow(() -> new IllegalStateException("Invalid king position"));
		} else if (rook.isInRightCorner()) {
			return getPosition().right(2).orElseThrow(() -> new IllegalStateException("Invalid king position"));
		} else {
			throw new IllegalStateException("Can't determine castlingKingDestination when the rook is not in a corner");
		}
	}
	
	private ChessPosition castlingRookDestination(RookModel rook) {
		if (rook.isInLeftCorner()) {
			return getPosition().left(1).orElseThrow(() -> new IllegalStateException("Invalid king position"));
		} else if (rook.isInRightCorner()) {
			return getPosition().right(1).orElseThrow(() -> new IllegalStateException("Invalid king position"));
		} else {
			throw new IllegalStateException("Can't determine castlingRookDestination when the rook is not in a corner");
		}
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
	protected void onMove() {
		moved = true;
	}
	
	@Override
	public ChessPieceModel copy() { return new KingModel(getColor(), getPosition(), moved); }
	
	@Override
	public int getValue() { return 0; }
}
