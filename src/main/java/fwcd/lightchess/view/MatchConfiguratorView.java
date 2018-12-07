package fwcd.lightchess.view;

import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.MutableComboBoxModel;

import fwcd.fructose.swing.View;
import fwcd.lightchess.model.match.ChessPlayer;
import fwcd.lightchess.model.match.MatchConfiguratorModel;
import fwcd.lightchess.utils.ObservableMap;

public class MatchConfiguratorView implements View {
	private final JPanel component;
	
	private final MutableComboBoxModel<String> whiteDropDownModel = new DefaultComboBoxModel<>();
	private final MutableComboBoxModel<String> blackDropDownModel = new DefaultComboBoxModel<>();
	
	private final JButton newMatchButton;
	private final JComboBox<String> whiteDropDown;
	private final JComboBox<String> blackDropDown;
	
	public MatchConfiguratorView(MatchConfiguratorModel model) {
		component = new JPanel();
		
		whiteDropDown = new JComboBox<>(whiteDropDownModel);
		blackDropDown = new JComboBox<>(blackDropDownModel);
		
		component.add(new JLabel("White: "));
		component.add(whiteDropDown);
		component.add(new JLabel("Black: "));
		component.add(blackDropDown);
		
		newMatchButton = new JButton("New Match");
		component.add(newMatchButton);
		
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
	public JComponent getComponent() { return component; }
}
