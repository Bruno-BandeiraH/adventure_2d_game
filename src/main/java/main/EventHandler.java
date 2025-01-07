package main;

import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRectangle[][] eventRectangle;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRectangle = new EventRectangle[gp.maxWorldCol][gp.maxWorldRow];

        for(int i = 0; i < gp.maxWorldCol; i++) {
            for(int j = 0; j < gp.maxWorldRow; j++) {
                eventRectangle[i][j] = new EventRectangle(23,23,2,2);
                eventRectangle[i][j].eventDefaultX = eventRectangle[i][j].x;
                eventRectangle[i][j].eventDefaultY = eventRectangle[i][j].y;
            }
        }
    }

    public void checkEvent() {

        // CHECK IF PLAYER CHARACTER IS MORE THAN 1 TILE AWAY FROM THE LAST EVENT
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if(canTouchEvent) {
            if(hit(19,15, "left")) {damagePit(19,15, gp.dialogueState);}
            if(hit(23,19, "any")) {damagePit(23,19, gp.dialogueState);}
            if(hit(23,12,"up")) {healingPool(23, 12, gp.dialogueState);}


        }
    }

    public boolean hit(int col, int row, String requiredDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRectangle[col][row].x = col * gp.tileSize + eventRectangle[col][row].x;
        eventRectangle[col][row].y = row * gp.tileSize + eventRectangle[col][row].y;

        if(gp.player.solidArea.intersects(eventRectangle[col][row]) && !eventRectangle[col][row].eventDone) {
            if(gp.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRectangle[col][row].x = eventRectangle[col][row].eventDefaultX;
        eventRectangle[col][row].y = eventRectangle[col][row].eventDefaultY;

        return hit;
    }

    public void damagePit( int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "tu caiu no buraco invisível, kkkkkkkk.";
        gp.player.currentLife--;
        canTouchEvent = false;
    }

    public void healingPool( int col, int row, int gameState) {
        if(gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "A água não tá gelada, mas pelo menos tá limpa.\n" +
                "você sente seu corpo se recuperar";
            gp.player.currentLife = gp.player.maxLife;
            gp.assetSetter.setMonsters();
        }
    }
}
