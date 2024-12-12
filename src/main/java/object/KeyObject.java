package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KeyObject extends SuperObject{

    GamePanel gp;


    public KeyObject(GamePanel gp){
        this.gp = gp;
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
