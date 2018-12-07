package fwcd.lightchess.controller;

import java.util.ConcurrentModificationException;
import fwcd.fructose.Option;

import fwcd.lightchess.controller.ChessBoardController;
import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessMove;
import fwcd.lightchess.model.PlayerColor;
import fwcd.lightchess.model.match.ChessPlayer;

public class HumanPlayer implements ChessPlayer {
	private final ChessBoardController chessBoard;
	private Option<ChessMove> move = Option.empty();
	
	public HumanPlayer(ChessBoardController chessBoard) {
		this.chessBoard = chessBoard;
		chessBoard.getMoveListeners().add(move -> this.move = Option.of(move));
	}
	
	@Override
	public ChessMove pickMove(PlayerColor me, ChessBoardModel board) throws InterruptedException {
		move = Option.empty();
		chessBoard.setMoveableColors(me);
		while (!move.isPresent()) {
			Thread.sleep(100);
		}
		chessBoard.setMoveableColors(/* none */);
		return move.orElseThrow(ConcurrentModificationException::new);
	}
}
