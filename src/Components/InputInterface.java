package Components;

import java.awt.event.KeyEvent;

public interface InputInterface {	
	public boolean isFocus();
	public void getFocus(boolean getFocus);
	public boolean isClickedOn(int mouseX, int mouseY);
	public String clickAction();
	public String keyPress(KeyEvent c);
	
}
