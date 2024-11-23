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

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        // set the size of this class
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // all the drawing from this component will be done in a offscreen painting buffer. Improves game's rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // the game can be focused to receive key input
    }

    public void startGameThread(){
        gameThread = new Thread(this); // passing this class to the thread.
        gameThread.start();
    }



    // GAME LOOP
    @Override
    public void run() {
        // when we start the thread this method will run automatically

        double drawInterval = 1000000000/FPS; // 1 billion nano seconds for 60. o.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){


            // 1  UPDATE: update information such as character positions
            update();

            // 2 DRAW: draw the screen with the updated information
            repaint();

            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); // pause the game loop

                nextDrawTime += drawInterval;
            } catch (InterruptedException e){
                e.getMessage();
            }


        }
    }


    public void update(){
        if(keyH.upPressed){
            playerY -= playerSpeed;
        } else if (keyH.downPressed) {
            playerY += playerSpeed;
        } else if (keyH.leftPressed) {
            playerX -= playerSpeed;
        } else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g){
        // this Graphics class is like  a brush that will paint the game on the screen for us
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // this is a better class for better control in 2D games

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose(); // helps to release resources after drawing
    }
}
