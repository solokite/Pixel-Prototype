package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public boolean leftPressed, rightPressed, shiftPressed, spacePressed;
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp; 
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_SHIFT) {
			shiftPressed = true;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
		if (code == KeyEvent.VK_SHIFT) {
			shiftPressed = false;
		}
	}

}
