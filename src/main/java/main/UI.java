package main;

import entity.Entity;
import object.HeartObject;
import object.ManaCrystalObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    public String currentDialogue = "";
    public int commandNumber = 0;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
    public int slotCol = 0;
    public int slotRow = 0;

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
        Entity crystal = new ManaCrystalObject(gp);
        crystal_full = crystal.down1;
        crystal_blank = crystal.down2;

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
            drawPlayerLifeBar();
            drawMessage();
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
        // CHARACTER STATE
        if(gp.gameState == gp.characterState) {
            drawCharacterStatusScreen();
            drawInventory();
        }
    }

    public void addMessage(String message) {
        messages.add(message);
        messageCounter.add(0);
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

    public void drawCharacterStatusScreen() {
        // CREATE A FRAME
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = (gp.tileSize * 10) + 24;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 24;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 36;

        // NAMES
        g2.drawString("Level", textX, textY);
        textY+=lineHeight;
        g2.drawString("Life", textX, textY);
        textY+=lineHeight;
        g2.drawString("Mana", textX, textY);
        textY+=lineHeight;
        g2.drawString("Strength", textX, textY);
        textY+=lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY+=lineHeight;
        g2.drawString("Attack", textX, textY);
        textY+=lineHeight;
        g2.drawString("Defense", textX, textY);
        textY+=lineHeight;
        g2.drawString("Exp", textX, textY);
        textY+=lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY+=lineHeight;
        g2.drawString("Coin", textX, textY);
        textY+=lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY+=lineHeight + 20;
        g2.drawString("Shield", textX, textY);
        textY+=lineHeight;

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        // reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.currentLife + "/" + gp.player.maxLife);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.currentMana + "/" + gp.player.maxMana);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXToAlignTextToTheRightL(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-14, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-14, null);
    }

    public void drawInventory() {

        // Frame
        int frameX = gp.tileSize * 12;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize+3;


        // DRAW PLAYER'S ITEMS
        for(int i = 0; i < gp.player.inventory.size(); i++) {

            // EQUIP CURSOR
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
                gp.player.inventory.get(i) == gp.player.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10 );
            }


            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;

            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }


        // cursor
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // Draw Cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // Description Frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;


        // Draw Description Text
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();
        if(itemIndex < gp.player.inventory.size()) {
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for(String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }

        }

    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26));

        for(int i = 0; i < messages.size(); i++) {
            if(messages.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(messages.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(messages.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;
                if(messageCounter.get(i) > 180) {
                    messages.remove(i);
                    messageCounter.remove(i);
                }
            }
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

    public int getXToAlignTextToTheRightL(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;


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

        // DRAW MAX MANA
        x = (gp.tileSize/2) - 5;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while(i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        // DRAW MANA
        x = (gp.tileSize/2) - 5;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while (i < gp.player.currentMana) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }
}
