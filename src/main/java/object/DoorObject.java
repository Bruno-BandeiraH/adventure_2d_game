package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DoorObject extends SuperObject{

    GamePanel gp;

    public DoorObject(GamePanel gp){
        this.gp = gp;
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
