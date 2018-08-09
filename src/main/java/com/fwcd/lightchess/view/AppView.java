package com.fwcd.lightchess.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.fwcd.fructose.swing.Viewable;
import com.fwcd.lightchess.model.AppModel;
import com.fwcd.lightchess.view.board.ChessBoardView;

public class AppView implements Viewable {
	private final JPanel view;
	private final ChessBoardView chessBoard;
	private final MatchConfiguratorView matchConfigurator;
	
	public AppView(AppModel model) {
		view = new JPanel();
		view.setLayout(new BorderLayout());
		
		chessBoard = new ChessBoardView(model.getChessBoard());
		matchConfigurator = new MatchConfiguratorView(model.getMatchConfigurator());
		
		model.getMatchManager().listenToResults(result -> {
			JOptionPane.showMessageDialog(view, result.getResultType() + " - " + result.getWinner() + " wins!");
		});
		
		view.add(chessBoard.getView(), BorderLayout.CENTER);
		view.add(matchConfigurator.getView(), BorderLayout.NORTH);
	}
	
	@Override
	public JComponent getView() {
		return view;
	}
	
	public MatchConfiguratorView getMatchConfigurator() { return matchConfigurator; }
	
	public ChessBoardView getChessBoard() { return chessBoard; }
}
