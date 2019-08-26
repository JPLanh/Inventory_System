package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import Components.UIFrame;

public class GUIController extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3149851920030223090L;
	UIFrame currentFrame;
	int UIwidth, UIheight;

	public GUIController(){
		addMouseListener(this);
		setBackground(new Color(8, 81, 0));
		currentFrame = new LoginUI();

		new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tick();
				repaint();
			}
		}).start();
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		currentFrame.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String getAction = currentFrame.clickAction(arg0.getX(), arg0.getY());
		if (getAction != null) {
			action(getAction);
		}
		repaint();
	}

	private void action(String action) {
		String[] takeAction = action.split(" ");
		if (action.equals("Goto Staff Console")) {
			currentFrame = new StaffConsoleUI();
		} else if (action.equals("Goto Admin Console")) {
			currentFrame = new AdminConsoleUI();
		} else if (action.equals("Goto Tech Console")) {
			currentFrame = new TechConsoleUI();
		} else if (action.equals("Logout")) {
			currentFrame = new LoginUI();
		}
		repaint();
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void keyPress(KeyEvent event) {
		String getAction = currentFrame.keyPress(event);
		if (getAction != null) {
			action(getAction);
		}
	}
	public void tick() {
		currentFrame.tick();
	}
}
