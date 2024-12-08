package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.management.remote.SubjectDelegationPermission;
import javax.swing.*;
import java.awt.*;

// this class will work as a game screen
public class GamePanel extends JPanel implements Runnable{


    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    Thread gameThread;
    public UI ui = new UI(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);

    // ENTITY AND OBJECTS
    public Player player = new Player(this, keyH);
    public SuperObject objectSlots[] = new SuperObject[10]; // 10 slots



    public GamePanel(){
        // set the size of this class
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // all the drawing from this component will be done in a offscreen painting buffer. Improves game's rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // the game can be focused to receive key input
    }

    public void setupGame(){

        assetSetter.setObject();
        playMusic(0);
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
        player.update();
    }

    public void paintComponent(Graphics g) {
        // this Graphics class is like  a brush that will paint the game on the screen for us
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g; // this is a better class for better control in 2D games

        // TILE
        tileManager.draw(g2);

        // OBJECT
        for (int i = 0; i < objectSlots.length; i++) {
            if (objectSlots[i] != null) {
                objectSlots[i].draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose(); // helps to release resources after drawing
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSoundEffect(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
