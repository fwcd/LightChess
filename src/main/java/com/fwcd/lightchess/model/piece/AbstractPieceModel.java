package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

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
	public boolean threatens(ChessPosition position, ChessBoardModel board) {
		return getPossibleMoves(board)
			.map(ChessMove::getDestination)
			.anyMatch(dest -> dest.equals(position));
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
