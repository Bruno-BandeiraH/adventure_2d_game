package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BootsObject extends SuperObject{

    public BootsObject(){
        name = "Boots";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
