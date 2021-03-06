package fwcd.lightchess.controller;

import javax.swing.JOptionPane;

import fwcd.lightchess.model.match.MatchConfiguratorModel;
import fwcd.lightchess.view.MatchConfiguratorView;

public class MatchConfiguratorController {
	public MatchConfiguratorController(MatchConfiguratorModel model, MatchConfiguratorView view) {
		view.getNewMatchButton().addActionListener(l -> {
			boolean success = model.createMatch();
			if (!success) {
				JOptionPane.showMessageDialog(view.getComponent(), "A match can not be created!");
			}
		});
	}
}
