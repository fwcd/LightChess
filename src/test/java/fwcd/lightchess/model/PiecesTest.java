package fwcd.lightchess.model;

import static org.junit.Assert.assertEquals;

import fwcd.lightchess.model.piece.BishopModel;
import fwcd.lightchess.model.piece.KnightModel;
import fwcd.lightchess.model.piece.PawnModel;
import fwcd.lightchess.model.piece.QueenModel;
import fwcd.lightchess.model.piece.RookModel;

import org.junit.Test;

public class PiecesTest {
	@Test
	public void testValues() {
		ChessPosition dummyPos = ChessPosition.at(0, 0);
		assertEquals(1, new PawnModel(PlayerColor.WHITE, dummyPos).getValue());
		assertEquals(3, new KnightModel(PlayerColor.WHITE, dummyPos).getValue());
		assertEquals(3, new BishopModel(PlayerColor.WHITE, dummyPos).getValue());
		assertEquals(5, new RookModel(PlayerColor.WHITE, dummyPos).getValue());
		assertEquals(9, new QueenModel(PlayerColor.WHITE, dummyPos).getValue());
	}
}
