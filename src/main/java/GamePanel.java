import javax.swing.*;
import java.awt.*;

// this class will work as a game screen
public class GamePanel extends JPanel implements Runnable{


    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * 3; // 48x48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    Thread gameThread;

    public GamePanel(){
        // set the size of this class
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // all the drawing from this component will be done in a offscreen painting buffer. Improves game's rendering performance
    }

    public void startGameThread(){
        gameThread = new Thread(this); // passing this class to the thread.
        gameThread.start();  
    }



    @Override
    public void run() {
        // when we start the thread this method will run automatically
    }
}
