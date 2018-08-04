package com.fwcd.lightchess.model.piece;

public interface ChessPieceVisitor {
	void visitBishop(BishopModel piece);
	
	void visitKing(KingModel piece);
	
	void visitQueen(QueenModel piece);
	
	void visitKnight(KnightModel piece);
	
	void visitPawn(PawnModel piece);
	
	void visitRook(RookModel piece);
}
