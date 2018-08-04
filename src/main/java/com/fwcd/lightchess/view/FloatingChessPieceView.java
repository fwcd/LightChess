package com.fwcd.lightchess.view;

import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.lightchess.model.ChessFieldModel;

public class FloatingChessPieceView {
	private ChessPieceView piece;
	private ChessFieldModel origin;
	private Vector2D pos;
	private Vector2D offset;
	
	public FloatingChessPieceView(ChessPieceView piece, ChessFieldModel origin, Vector2D pos, Vector2D offset) {
		this.piece = piece;
		this.origin = origin;
		this.pos = pos;
		this.offset = offset;
	}
	
	public Vector2D getOffset() { return offset; }
	
	public ChessFieldModel getOrigin() { return origin; }
	
	public ChessPieceView getPiece() { return piece; }
	
	public Vector2D getPos() { return pos; }
	
	public void setPos(Vector2D pos) { this.pos = pos; }
}
