package com.fwcd.lightchess.model.match;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.fwcd.fructose.EventListenerList;
import com.fwcd.lightchess.model.ChessBoardModel;

public class MatchManager implements AutoCloseable {
	private final ExecutorService matchExecutor = Executors.newSingleThreadExecutor();
	private final ChessBoardModel board;
	private final EventListenerList<ChessMatchResult> resultListeners = new EventListenerList<>();
	private Optional<ChessMatch> match = Optional.empty();
	
	public MatchManager(ChessBoardModel board) {
		this.board = board;
	}
	
	public boolean createMatch(ChessPlayer white, ChessPlayer black) {
		if (match.filter(it -> !it.isGameOver()).isPresent()) {
			// A match is currently running
			return false;
		} else {
			board.resetToInitialSetup();
			
			ChessMatch newMatch = new ChessMatch(board, white, black);
			newMatch.setResultListeners(resultListeners);
			matchExecutor.execute(newMatch::play);
			match = Optional.of(newMatch);
			
			return true;
		}
	}
	
	public void listenToResults(Consumer<ChessMatchResult> listener) {
		resultListeners.add(listener);
	}
	
	public void unlistenFromResults(Consumer<ChessMatchResult> listener) {
		resultListeners.remove(listener);
	}
	
	@Override
	public void close() {
		matchExecutor.shutdown();
	}
}
