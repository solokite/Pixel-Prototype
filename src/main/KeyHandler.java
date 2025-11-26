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

		// LEVEL CLEARED STATE
if (gp.gameState == gp.levelClearedState) {

    // Move selector UP
    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
        gp.ui.commandNum--;
        if (gp.ui.commandNum < 0) gp.ui.commandNum = 2;
    }

    // Move selector DOWN
    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
        gp.ui.commandNum++;
        if (gp.ui.commandNum > 2) gp.ui.commandNum = 0;
    }

    // CONFIRM SELECTION
    if (code == KeyEvent.VK_ENTER) {
        
        // NEXT LEVEL
        if (gp.ui.commandNum == 0) {
            // gp.loadNextLevel();
						
            gp.gameState = gp.playState;
        } 
				if (gp.ui.commandNum == 1) {
					gp.gameState = gp.titleState;
					
				}

        // EXIT
        if (gp.ui.commandNum == 2) {
            System.exit(0);
        }
    }

    return; // IMPORTANT → block movement
}


    // TITLE STATE
    if (gp.gameState == gp.titleState) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 2;
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) gp.ui.commandNum = 0;
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
							gp.resetGame();
							gp.gameState = gp.playState;
						}
            if (gp.ui.commandNum == 2) System.exit(0);
        }
    }

    // 🔥 TOGGLE PAUSE ANYTIME DURING GAMEPLAY
    if (code == KeyEvent.VK_P) {
        if (gp.gameState == gp.playState) {
            gp.gameState = gp.pauseState;
        } 
        else if (gp.gameState == gp.pauseState) {
            gp.gameState = gp.playState;
        }
        return; // prevent overlapping controls
    }

    // GAMEPLAY CONTROLS
    if (gp.gameState == gp.playState) {

        if (code == KeyEvent.VK_A) leftPressed = true;
        if (code == KeyEvent.VK_D) rightPressed = true;
        if (code == KeyEvent.VK_SHIFT) shiftPressed = true;
        if (code == KeyEvent.VK_SPACE) spacePressed = true;
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
