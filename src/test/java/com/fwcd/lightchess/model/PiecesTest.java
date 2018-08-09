package com.fwcd.lightchess.model;

import static org.junit.Assert.assertEquals;

import com.fwcd.lightchess.model.piece.BishopModel;
import com.fwcd.lightchess.model.piece.KnightModel;
import com.fwcd.lightchess.model.piece.PawnModel;
import com.fwcd.lightchess.model.piece.QueenModel;
import com.fwcd.lightchess.model.piece.RookModel;

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
