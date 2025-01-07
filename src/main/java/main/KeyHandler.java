package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed;
    public boolean checkDrawTime;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returns the number of the key pressed

        if(gp.gameState == gp.titleState) {
            titleState(code);
        }
        else if(gp.gameState == gp.playState) {
            playState(code);
        }
        else if(gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        else if(gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            if(gp.ui.commandNumber == 0) {
                gp.ui.commandNumber = 2;
            } else {
                gp.ui.commandNumber--;
            }
        }
        if (code == KeyEvent.VK_S) {
            if(gp.ui.commandNumber == 2) {
                gp.ui.commandNumber = 0;
            } else {
                gp.ui.commandNumber++;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            switch (gp.ui.commandNumber) {
                case 0:
                    gp.gameState = gp.playState;
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        // TIMER
        if (code == KeyEvent.VK_T) {
            if (!checkDrawTime) {
                checkDrawTime = true;
            } else if (checkDrawTime) {
                checkDrawTime = false;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code) {
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
