package com.fwcd.lightchess.controller;

import com.fwcd.lightchess.model.match.MatchConfiguratorModel;
import com.fwcd.lightchess.view.MatchConfiguratorView;

public class MatchConfiguratorController {
	public MatchConfiguratorController(MatchConfiguratorModel model, MatchConfiguratorView view) {
		view.getNewMatchButton().addActionListener(l -> model.createMatch());
	}
}
