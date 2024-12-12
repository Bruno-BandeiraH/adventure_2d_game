package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ChestObject extends SuperObject{

    GamePanel gp;

    public ChestObject(GamePanel gp){
        this.gp = gp;
        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
