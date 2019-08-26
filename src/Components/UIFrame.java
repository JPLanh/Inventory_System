package Components;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public interface UIFrame{
	public void draw(Graphics g);
	public String clickAction(int mouseX, int mouseY);
	public String keyPress(KeyEvent c);
	public void tick();
}
