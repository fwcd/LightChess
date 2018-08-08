package com.fwcd.lightchess.view;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.MutableComboBoxModel;

import com.fwcd.fructose.swing.Viewable;
import com.fwcd.lightchess.model.MatchConfiguratorModel;
import com.fwcd.lightchess.model.match.ChessPlayer;

public class MatchConfiguratorView implements Viewable {
	private final JPanel view;
	private final MutableComboBoxModel<String> whiteDropDownModel = new DefaultComboBoxModel<>();
	private final MutableComboBoxModel<String> blackDropDownModel = new DefaultComboBoxModel<>();
	
	public MatchConfiguratorView(MatchConfiguratorModel model) {
		view = new JPanel();
		view.add(new JLabel("White: "));
		view.add(new JComboBox<>(whiteDropDownModel));
		view.add(new JLabel("Black: "));
		view.add(new JComboBox<>(blackDropDownModel));
		
		model.getPlayerFactory().listenToPut(this::addPlayer);
		model.getPlayerFactory().listenToRemove(this::removePlayer);
	}
	
	private void addPlayer(String name, Supplier<ChessPlayer> constructor) {
		whiteDropDownModel.addElement(name);
		blackDropDownModel.addElement(name);
	}
	
	private void removePlayer(String name, Supplier<ChessPlayer> constructor) {
		whiteDropDownModel.removeElement(name);
		blackDropDownModel.removeElement(name);
	}
	
	@Override
	public JComponent getView() { return view; }
}
