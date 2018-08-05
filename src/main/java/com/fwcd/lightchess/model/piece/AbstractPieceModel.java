package com.fwcd.lightchess.model.piece;

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
