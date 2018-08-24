package com.fwcd.lightchess.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.fwcd.fructose.swing.View;
import com.fwcd.lightchess.model.AppModel;
import com.fwcd.lightchess.view.board.ChessBoardView;

public class AppView implements View {
	private final JPanel component;
	private final ChessBoardView chessBoard;
	private final MatchConfiguratorView matchConfigurator;
	
	public AppView(AppModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		chessBoard = new ChessBoardView(model.getChessBoard());
		matchConfigurator = new MatchConfiguratorView(model.getMatchConfigurator());
		
		model.getMatchManager().listenToResults(result -> {
			JOptionPane.showMessageDialog(component, result.getResultType() + " - " + result.getWinner() + " wins!");
		});
		
		component.add(chessBoard.getComponent(), BorderLayout.CENTER);
		component.add(matchConfigurator.getComponent(), BorderLayout.NORTH);
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
	
	public MatchConfiguratorView getMatchConfigurator() { return matchConfigurator; }
	
	public ChessBoardView getChessBoard() { return chessBoard; }
}
