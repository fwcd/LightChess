package com.fwcd.lightchess.model.match;

public enum ChessResultType {
	CHECKMATE("Checkmate"), STALEMATE("Stalemate");
	
	private final String stringRepresentation;
	
	private ChessResultType(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}
	
	@Override
	public String toString() { return stringRepresentation; }
}
