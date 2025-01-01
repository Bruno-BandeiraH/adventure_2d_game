package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class HeartObject extends Entity {

    private final BufferedImage fullHeart;
    private final BufferedImage halfHeart;
    private final BufferedImage blankHeart;

    public HeartObject(GamePanel gp){
        super(gp);

        name = "Heart";
        fullHeart = setup("/objects/heart_full");
        halfHeart = setup("/objects/heart_half");
        blankHeart = setup("/objects/heart_blank");
    }


    public BufferedImage getFullHeart() {
        return fullHeart;
    }

    public BufferedImage getHalfHeart() {
        return halfHeart;
    }

    public BufferedImage getBlankHeart() {
        return blankHeart;
    }
}
