package com.fwcd.lightchess.model.match;

import java.util.Optional;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.PlayerColor;
import com.fwcd.lightchess.model.piece.ChessPieceModel;

public class ChessMatch {
	private final ChessBoardModel board = ChessBoardModel.withInitialSetup();
	private final ChessPlayer white;
	private final ChessPlayer black;
	private boolean started = false;
	private boolean whiteHasTurn = true;
	
	public ChessMatch(ChessPlayer white, ChessPlayer black) {
		this.white = white;
		this.black = black;
	}
	
	public ChessMatchResult play() {
		if (started) {
			throw new IllegalStateException("Can't play an already started ChessMatch");
		}
		started = true;
		
		while (!board.isGameOver()) {
			ChessMove move;
			if (whiteHasTurn) {
				move = white.pickMove(board);
			} else {
				move = black.pickMove(board);
			}
			board.performMove(move);
			whiteHasTurn = !whiteHasTurn;
		}
		
		Optional<PlayerColor> checkmate = board.getCheckmate().map(ChessPieceModel::getColor);
		Optional<PlayerColor> stalemate = board.getStalemate();
		
		if (checkmate.isPresent()) {
			return new ChessMatchResult(checkmate.orElse(null), ChessResultType.CHECKMATE);
		} else if (stalemate.isPresent()) {
			return new ChessMatchResult(stalemate.orElse(null), ChessResultType.STALEMATE);
		} else {
			throw new IllegalStateException("Invalid match result: Neither a checkmate nor a stalemate");
		}
	}
}