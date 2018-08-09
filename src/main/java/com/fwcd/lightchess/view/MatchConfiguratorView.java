package com.fwcd.lightchess.view;

import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.MutableComboBoxModel;

import com.fwcd.fructose.swing.Viewable;
import com.fwcd.lightchess.model.match.ChessPlayer;
import com.fwcd.lightchess.model.match.MatchConfiguratorModel;
import com.fwcd.lightchess.utils.ObservableMap;

public class MatchConfiguratorView implements Viewable {
	private final JPanel view;
	
	private final MutableComboBoxModel<String> whiteDropDownModel = new DefaultComboBoxModel<>();
	private final MutableComboBoxModel<String> blackDropDownModel = new DefaultComboBoxModel<>();
	
	private final JButton newMatchButton;
	private final JComboBox<String> whiteDropDown;
	private final JComboBox<String> blackDropDown;
	
	public MatchConfiguratorView(MatchConfiguratorModel model) {
		view = new JPanel();
		
		whiteDropDown = new JComboBox<>(whiteDropDownModel);
		blackDropDown = new JComboBox<>(blackDropDownModel);
		
		view.add(new JLabel("White: "));
		view.add(whiteDropDown);
		view.add(new JLabel("Black: "));
		view.add(blackDropDown);
		
		newMatchButton = new JButton("New Match");
		view.add(newMatchButton);
		
		ObservableMap<String, Supplier<ChessPlayer>> playerFactory = model.getPlayerFactory();
		playerFactory.listenToPut(this::addPlayer);
		playerFactory.listenToRemove(this::removePlayer);
		
		whiteDropDown.addItemListener(e -> model.setWhite(playerFactory.get((String) whiteDropDown.getSelectedItem()).get()));
		blackDropDown.addItemListener(e -> model.setBlack(playerFactory.get((String) blackDropDown.getSelectedItem()).get()));
	}
	
	private void addPlayer(String name, Supplier<ChessPlayer> constructor) {
		whiteDropDownModel.addElement(name);
		blackDropDownModel.addElement(name);
	}
	
	private void removePlayer(String name, Supplier<ChessPlayer> constructor) {
		whiteDropDownModel.removeElement(name);
		blackDropDownModel.removeElement(name);
	}
	
	public JButton getNewMatchButton() { return newMatchButton; }
	
	@Override
	public JComponent getView() { return view; }
}