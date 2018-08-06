package com.fwcd.lightchess.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import com.fwcd.lightchess.model.piece.ChessPieceModel;
import com.fwcd.lightchess.utils.ChessConstants;

import org.junit.Test;

public class ChessBoardModelTest {
	@Test
	public void testCopy() {
		ChessBoardModel orig = ChessBoardModel.withInitialSetup();
		ChessBoardModel copy = orig.copy();
		
		for (int y=0; y<ChessConstants.RANKS; y++) {
			for (int x=0; x<ChessConstants.FILES; x++) {
				ChessPosition pos = ChessPosition.at(x, y);
				ChessFieldModel origField = orig.fieldAt(pos);
				ChessFieldModel copyField = copy.fieldAt(pos);
				if (origField.hasPiece()) {
					ChessPieceModel origPiece = origField.getPiece().orElse(null);
					ChessPieceModel copyPiece = copyField.getPiece().orElse(null);
					assertNotSame(origPiece, copyPiece);
					assertEquals(origPiece.getColor(), copyPiece.getColor());
					assertEquals(origPiece.getPosition(), copyPiece.getPosition());
				}
			}
		}
	}
}
