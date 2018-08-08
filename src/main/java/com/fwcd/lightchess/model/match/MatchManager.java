package com.fwcd.lightchess.model.match;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fwcd.lightchess.model.ChessBoardModel;

public class MatchManager implements AutoCloseable {
	private final ExecutorService matchExecutor = Executors.newSingleThreadExecutor();
	private final ChessBoardModel board;
	private ChessMatch match = null;
	
	public MatchManager(ChessBoardModel board) {
		this.board = board;
	}
	
	public void createMatch(ChessPlayer white, ChessPlayer black) {
		match = new ChessMatch(board, white, black);
		matchExecutor.execute(match::play);
	}
	
	@Override
	public void close() {
		matchExecutor.shutdown();
	}
}
