package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRectangle ;
    int eventDefaultX, eventDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRectangle = new Rectangle(23, 23, 2, 2);
        eventDefaultX = eventRectangle.x;
        eventDefaultY = eventRectangle.y;
    }

    public void checkEvent() {
        if(hit(19,15, "left")) {damagePit(gp.dialogueState);}
        if(hit(23,12,"up")) {healingPool(gp.dialogueState);}
    }

    public boolean hit(int eventCol, int eventRow, String requiredDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRectangle.x = eventCol * gp.tileSize + eventRectangle.x;
        eventRectangle.y = eventRow * gp.tileSize + eventRectangle.y;

        if(gp.player.solidArea.intersects(eventRectangle)) {
            if(gp.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRectangle.x = eventDefaultX;
        eventRectangle.y = eventDefaultY;

        return hit;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "tu caiu no buraco invisível, kkkkkkkk. Faz u éle";
        gp.player.currentLife--;
    }

    public void healingPool(int gameState) {
        if(gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "A água não tá gelada, mas pelo menos tá limpa.\n" +
                "você sente seu corpo se recuperar";
            gp.player.currentLife = gp.player.maxLife;
        }
    }
}
