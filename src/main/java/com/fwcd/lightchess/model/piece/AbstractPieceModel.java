package com.fwcd.lightchess.model.piece;

import java.util.Optional;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public abstract class AbstractPieceModel implements ChessPieceModel {
	private final PlayerColor color;
	private ChessPosition position;
	
	public AbstractPieceModel(PlayerColor color, ChessPosition position) {
		this.color = color;
		this.position = position;
	}
	
	@Override
	public boolean threatens(ChessPosition target, ChessBoardModel board) {
		ChessBoardModel boardWithoutTarget = board.copy();
		boardWithoutTarget.fieldAt(target).setPiece(Optional.empty());
		return getPossibleMoves(boardWithoutTarget)
			.map(ChessMove::getDestination)
			.anyMatch(dest -> dest.equals(target));
	}
	
	@Override
	public ChessPosition getPosition() { return position; }
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public final void moveTo(ChessPosition position) {
		this.position = position;
		onMove();
	}
	
	protected void onMove() {}
}
