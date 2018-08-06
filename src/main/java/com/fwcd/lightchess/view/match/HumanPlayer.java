package com.fwcd.lightchess.view.match;

import java.util.ConcurrentModificationException;
import java.util.Optional;

import com.fwcd.lightchess.controller.ChessBoardController;
import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.match.ChessPlayer;

public class HumanPlayer implements ChessPlayer {
	private final ChessBoardController chessBoard;
	private Optional<ChessMove> move = Optional.empty();
	
	public HumanPlayer(ChessBoardController chessBoard) {
		this.chessBoard = chessBoard;
		chessBoard.getMoveListeners().add(move -> this.move = Optional.of(move));
	}
	
	@Override
	public ChessMove pickMove(ChessBoardModel board) {
		chessBoard.setUserInteractionsEnabled(true);
		while (!move.isPresent()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		chessBoard.setUserInteractionsEnabled(false);
		return move.orElseThrow(ConcurrentModificationException::new);
	}
}
