package fwcd.lightchess.model.match;

import fwcd.fructose.Option;
import java.util.function.Consumer;

import fwcd.fructose.EventListenerList;
import fwcd.lightchess.model.ChessBoardModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchManager {
	private static final Logger LOG = LoggerFactory.getLogger(MatchManager.class);
	private final ChessBoardModel board;
	private final EventListenerList<ChessMatchResult> resultListeners = new EventListenerList<>();
	private Option<Thread> matchThread = Option.empty();
	private Option<ChessMatch> match = Option.empty();
	
	public MatchManager(ChessBoardModel board) {
		this.board = board;
	}
	
	public boolean createMatch(ChessPlayer white, ChessPlayer black) {
		if (match.filter(it -> !it.isGameOver()).isPresent()) {
			// A match is currently running
			stopMatch();
		}
		
		board.resetToInitialSetup();
		
		ChessMatch newMatch = new ChessMatch(board, white, black);
		newMatch.setResultListeners(resultListeners);
		Thread newThread = new Thread(() -> {
			try {
				newMatch.play();
			} catch (InterruptedException e) {
				// Swallow this exception (which is usually thrown by
				// stopMatch() to indicate that a match should terminate)
			}
		}, "ChessMatch runner");
		newThread.start();
		
		LOG.info("Starting match");
		
		matchThread = Option.of(newThread);
		match = Option.of(newMatch);
		
		return true;
	}
	
	public void stopMatch() {
		matchThread.ifPresent(it -> {
			it.interrupt();
			try {
				LOG.info("Waiting for stopped match to terminate...");
				it.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
	}
	
	public void listenToResults(Consumer<ChessMatchResult> listener) {
		resultListeners.add(listener);
	}
	
	public void unlistenFromResults(Consumer<ChessMatchResult> listener) {
		resultListeners.remove(listener);
	}
}
