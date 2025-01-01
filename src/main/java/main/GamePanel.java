package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.*;

// this class works as the game screen
public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // scaling
    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxTileCol = 16; // making a
    public final int maxTileRow = 12; // 4:3 aspect ratio
    public final int screenWidth = tileSize * maxTileCol; // 768 pixels
    public final int screenHeight = tileSize * maxTileRow; // 576 pixels

    // WORLD MAP SIZE SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    Thread gameThread;
    public UI ui = new UI(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public EventHandler eventHandler = new EventHandler(this);

    // ENTITY AND OBJECTS
    public Player player = new Player(this, keyH);
    public Entity[] objectSlots = new Entity[10]; // 10 slots
    public Entity[] npc = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    // METHODS

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // setting the size of the game screen
        this.setDoubleBuffered(true); // all the drawings from this component will be done in a offscreen painting buffer. Improves game's rendering performance
        this.setFocusable(true); // the game can be focused to receive key input
        this.addKeyListener(keyH); // listening commands controls
    }

    public void setupGame() {
        assetSetter.setObject(); // setting interactable objects in the map
        assetSetter.setNpc();
        // playMusic(0); // playing the music theme
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // GAME LOOP
    @Override
    public void run() {
        // when we start the thread this method will run automatically

        double drawInterval = 1000000000/FPS; // 1 billion nano seconds for 60. o.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){
            // 1  UPDATE: update information such as character positions, tiles, objects, npc's
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

    public void update() {
        if(gameState == playState)
            player.update();
        for(int i = 0; i < npc.length; i++) {
            if(npc[i] != null) {
                npc[i].update();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // this Graphics class is like a brush that will paint the game on the screen for us
        Graphics2D g2 = (Graphics2D) g; // this is a class for better control in 2D games

        // time stamp
        long drawStart = 0;
        if(keyH.checkDrawTime){
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState) {
            ui.draw(g2);
        } else {
            // TILE
            tileManager.draw(g2);

            // POPULATING ENTITY LIST

            entityList.add(player); // PLAYER

            Arrays.stream(npc) // NPC
                .filter(Objects::nonNull)
                    .forEach(entityList::add);

            Arrays.stream(objectSlots) // OBJECTS
                .filter(Objects::nonNull)
                    .forEach(entityList::add);

            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            // DRAW ENTITIES
            for(Entity entity : entityList) {
                entity.draw(g2);
            }
            // CLEAR LIST FOR THE NEXT FRAME
            entityList.clear();

            // UI
            ui.draw(g2);
        }

        // time stamp
        if(keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passedTime = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passedTime, 10, 400);
            System.out.println("Draw time: " + passedTime);
        }
        g2.dispose(); // helps to release resources after drawing
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
