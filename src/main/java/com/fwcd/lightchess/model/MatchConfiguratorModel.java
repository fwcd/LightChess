package com.fwcd.lightchess.model;

import java.util.function.Supplier;

import com.fwcd.lightchess.model.match.ChessPlayer;
import com.fwcd.lightchess.utils.ObservableMap;

public class MatchConfiguratorModel {
	private final ObservableMap<String, Supplier<ChessPlayer>> playerFactory = new ObservableMap<>();
	
	public ObservableMap<String, Supplier<ChessPlayer>> getPlayerFactory() { return playerFactory; }
}
