package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ChestObject extends SuperObject{

    public ChestObject(){
        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
