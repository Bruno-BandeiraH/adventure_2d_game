package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed, shootKeyPressed;
    public boolean showDebugText;

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
        else if (gp.gameState == gp.optionsState) {
            optionsState(code);
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
                    gp.playMusic(0);
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    public void optionsState(int code) {
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (code == KeyEvent.VK_W) {
            if(gp.ui.commandNumber == 0) {
                gp.ui.commandNumber = 5;
            } else {
                gp.ui.commandNumber--;
            }
        }
        if (code == KeyEvent.VK_S) {
            if(gp.ui.commandNumber == 5) {
                gp.ui.commandNumber = 0;
            } else {
                gp.ui.commandNumber++;
            }
        }

        if(code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }
                if (gp.ui.commandNumber == 2 && gp.soundEffect.volumeScale > 0) {
                    gp.soundEffect.volumeScale--;
                }
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }
                if (gp.ui.commandNumber == 2 && gp.soundEffect.volumeScale < 5) {
                    gp.soundEffect.volumeScale++;
                }
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
        if (code == KeyEvent.VK_SPACE) {
            shootKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }


        // TIMER
        if (code == KeyEvent.VK_T) {
            if (!showDebugText) {
                showDebugText = true;
            } else if (showDebugText) {
                showDebugText = false;
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
        if(code == KeyEvent.VK_W) {
            if(gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
            }
        }
        if(code == KeyEvent.VK_A) {
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
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
        if(code == KeyEvent.VK_SPACE){
            shootKeyPressed = false;
        }
    }
}
