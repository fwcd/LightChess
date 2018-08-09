package com.fwcd.lightchess.model.match;

import java.util.Optional;
import java.util.function.Supplier;

import com.fwcd.lightchess.model.match.ChessPlayer;
import com.fwcd.lightchess.utils.ObservableMap;

public class MatchConfiguratorModel {
	private final ObservableMap<String, Supplier<ChessPlayer>> playerFactory = new ObservableMap<>();
	private final MatchManager manager;
	private Optional<ChessPlayer> white = Optional.empty();
	private Optional<ChessPlayer> black = Optional.empty();
	
	public MatchConfiguratorModel(MatchManager manager) {
		this.manager = manager;
	}
	
	public void setWhite(ChessPlayer white) { this.white = Optional.of(white); }
	
	public void setBlack(ChessPlayer black) { this.black = Optional.of(black); }
	
	public boolean createMatch() {
		return manager.createMatch(
			white.orElseThrow(() -> new IllegalStateException("Can't create a match without a white player")),
			black.orElseThrow(() -> new IllegalStateException("Can't create a match without a black player"))
		);
	}
	
	public ObservableMap<String, Supplier<ChessPlayer>> getPlayerFactory() { return playerFactory; }
}
