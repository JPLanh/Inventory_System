package Main;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import UI.GUIController;

public class mainFrame extends JFrame{

	GUIController myUI = null;

	public mainFrame(int frameWidth, int frameHeight)
	{
		setSize(frameWidth, frameHeight);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(myUI = new GUIController());
		revalidate();
		setFocusTraversalKeysEnabled(false);

		ActionListener updateListener = new myUpdateListener();
		Timer timer = new Timer(1000, updateListener);
		timer.start();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				myUI.keyPress(event);
				repaint();
			}      
		});
	}
	
	class myUpdateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			revalidate();			
		}

	}
}
