package com.fwcd.lightchess.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.fwcd.fructose.swing.Viewable;
import com.fwcd.lightchess.model.AppModel;
import com.fwcd.lightchess.view.board.ChessBoardView;

public class AppView implements Viewable {
	private final JPanel view;
	private final ChessBoardView chessBoard;
	private final NewMatchPanel newMatchPanel;
	
	public AppView(AppModel model) {
		view = new JPanel();
		view.setLayout(new BorderLayout());
		
		chessBoard = new ChessBoardView(model.getChessBoard());
		newMatchPanel = new NewMatchPanel();
		
		view.add(chessBoard.getView(), BorderLayout.CENTER);
		view.add(newMatchPanel.getView(), BorderLayout.NORTH);
	}
	
	@Override
	public JComponent getView() {
		return view;
	}
	
	public NewMatchPanel getNewMatchPanel() { return newMatchPanel; }
	
	public ChessBoardView getChessBoard() { return chessBoard; }
}
