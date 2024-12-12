package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BootsObject extends SuperObject{

    GamePanel gp;

    public BootsObject(GamePanel gp){
        this.gp = gp;
        name = "Boots";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
