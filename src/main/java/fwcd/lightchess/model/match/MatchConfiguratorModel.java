package fwcd.lightchess.model.match;

import fwcd.fructose.Option;
import java.util.function.Supplier;

import fwcd.lightchess.model.match.ChessPlayer;
import fwcd.lightchess.utils.ObservableMap;

public class MatchConfiguratorModel {
	private final ObservableMap<String, Supplier<ChessPlayer>> playerFactory = new ObservableMap<>();
	private final MatchManager manager;
	private Option<ChessPlayer> white = Option.empty();
	private Option<ChessPlayer> black = Option.empty();
	
	public MatchConfiguratorModel(MatchManager manager) {
		this.manager = manager;
	}
	
	public void setWhite(ChessPlayer white) { this.white = Option.of(white); }
	
	public void setBlack(ChessPlayer black) { this.black = Option.of(black); }
	
	public boolean createMatch() {
		return manager.createMatch(
			white.orElseThrow(() -> new IllegalStateException("Can't create a match without a white player")),
			black.orElseThrow(() -> new IllegalStateException("Can't create a match without a black player"))
		);
	}
	
	public ObservableMap<String, Supplier<ChessPlayer>> getPlayerFactory() { return playerFactory; }
}
