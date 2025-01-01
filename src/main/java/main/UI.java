package main;

import entity.Entity;
import object.HeartObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    public String currentDialogue = "";
    public int commandNumber = 0;
    BufferedImage heart_full, heart_half, heart_blank;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // CREATE HUD OBJECT
        HeartObject heart = new HeartObject(gp);
        heart_full = heart.getFullHeart();
        heart_half = heart.getHalfHeart();
        heart_blank = heart.getBlankHeart();

    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // PLAY STATE
        if(gp.gameState == gp.playState){
            // do playState stuff later
            drawPlayerLifeBar();
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPlayerLifeBar();
            drawPauseScreen();

        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLifeBar();
            drawDialogueScreen();
        }
    }

    public void drawDialogueScreen() {
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawTitleScreen() {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 94F));
        String title = "Blue Boy Adventure";
        int x = getXForCenteredText(title);
        int y = gp.screenHeight / 4;

        // SHADOW
        g2.setColor(Color.gray);
        g2.drawString(title,x+5,y+5);

        g2.setColor(Color.white); // of the font
        g2.drawString(title, x ,y);

        // BLUE BOY IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize*2)/2;
        y = gp.tileSize * 4;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
        String text = "NOVO JOGO";
        x = getXForCenteredText(text);
        y += (int) (gp.tileSize * 3.5);
        g2.drawString(text, x ,y);
        if(commandNumber == 0) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "CARREGAR JOGO";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x ,y);
        if(commandNumber == 1) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "SAIR";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x ,y);
        if(commandNumber == 2) {
            g2.drawString(">", x-gp.tileSize, y);
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 210); // the alpha variable sets the opacity
        g2.setColor(color);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255); // white
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5)); // defines the width of outlines of graphics which are rendered with a Graphics2D. 5 pixels
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return ((gp.screenWidth/2) - (length/2));

    }

    public void drawPlayerLifeBar() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE
        while(i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // RESET VALUES
        i = 0;
        x = gp.tileSize / 2;

        // DRAW CURRENT LIFE
        while(i < gp.player.currentLife) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.currentLife) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
}
