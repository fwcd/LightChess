package com.fwcd.lightchess.view;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.MutableComboBoxModel;

import com.fwcd.fructose.swing.Viewable;
import com.fwcd.lightchess.model.match.ChessPlayer;

public class NewMatchPanel implements Viewable {
	private final JPanel view;
	private final MutableComboBoxModel<String> whiteDropDownModel = new DefaultComboBoxModel<>();
	private final MutableComboBoxModel<String> blackDropDownModel = new DefaultComboBoxModel<>();
	private final Map<String, Supplier<ChessPlayer>> playerFactory = new HashMap<>();
	
	public NewMatchPanel() {
		view = new JPanel();
		view.add(new JComboBox<>(whiteDropDownModel));
		view.add(new JComboBox<>(blackDropDownModel));
	}
	
	public void addPlayer(String name, Supplier<ChessPlayer> constructor) {
		if (playerFactory.containsKey(name)) {
			throw new IllegalStateException("Tried to add duplicate player name: " + name);
		}
		whiteDropDownModel.addElement(name);
		blackDropDownModel.addElement(name);
		playerFactory.put(name, constructor);
	}
	
	public void addPlayerAsync(String name, Supplier<ChessPlayer> constructor) {
		
	}
	
	@Override
	public JComponent getView() { return view; }
}
