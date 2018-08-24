package com.fwcd.lightchess;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.fwcd.fructose.time.Stopwatch;
import com.fwcd.lightchess.controller.AppController;
import com.fwcd.lightchess.model.AppModel;
import com.fwcd.lightchess.view.AppView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightChessMain {
	private static final Logger LOG = LoggerFactory.getLogger(LightChessMain.class);
	
	public static void main(String[] args) {
		Stopwatch initStopwatch = new Stopwatch();
		initStopwatch.start();
		LOG.info("Launching application");
		
		AppModel model = new AppModel();
		AppView view = new AppView(model);
		new AppController(model, view);
		
		JFrame frame = new JFrame("LightChess");
		frame.setSize(640, 670);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(view.getComponent(), BorderLayout.CENTER);
		
		frame.setVisible(true);
		LOG.info("Initialized GUI in {} ms", initStopwatch.getMillis());
	}
}
