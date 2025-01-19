package main;

import entity.Entity;
import entity.Player;
import entity.Projectile;
import interactiveTile.InteractiveTile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.IntStream;

// this class works as the game screen
public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    public final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // scaling
    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxTileCol = 20; // making a
    public final int maxTileRow = 12; // 4:3 aspect ratio
    public final int screenWidth = tileSize * maxTileCol; // 960 pixels
    public final int screenHeight = tileSize * maxTileRow; // 576 pixels
    int fullScreenWidth = screenWidth;
    int fullScreenHeight = screenHeight;
    BufferedImage temporaryScreen;
    Graphics2D g2;


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
    public Entity[] objectSlots = new Entity[20]; // 10 slots
    public Entity[] npc = new Entity[10];
    public Entity[] monsters = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectiles = new ArrayList<>();
    public InteractiveTile[] interactiveTiles = new InteractiveTile[50];
    public ArrayList<Entity> particleList = new ArrayList<>();



    // GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

    // METHODS

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // setting the size of the game screen
        this.setDoubleBuffered(true); // all the drawings from this component will be done in a offscreen painting buffer. Improves game's rendering performance
        this.setFocusable(true); // the game can be focused to receive key input
        this.addKeyListener(keyH); // listening commands controls
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNpc();
        assetSetter.setMonsters();
        gameState = titleState;
        assetSetter.setInteractiveTiles();

        temporaryScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) temporaryScreen.getGraphics();
        setFullScreen();
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
            //repaint();
            drawToTemporaryScreen(); // draw everything to the buffered image
            drawToScreen(); // draw the buffered image to the screen
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

    public void setFullScreen() {
        // get local screen device
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        fullScreenWidth = Main.window.getWidth();
        fullScreenHeight = Main.window.getHeight();

    }

    public void update() {
        if(gameState == playState)
            player.update();
        Arrays.stream(npc)
            .filter(Objects::nonNull)
            .forEach(Entity::update);

        IntStream.range(0, monsters.length)
            .forEach(i -> {
                Entity monster = monsters[i];
                if(monster != null) {
                    if(monster.alive && !monster.dying) {
                        monster.update();
                    }
                    if(!monster.alive) {
                        monsters[i].checkDrop();
                        monsters[i] = null;
                    }
                }
            });

        Iterator<Entity> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = (Projectile) iterator.next();
            if (projectile != null) {
                if (projectile.alive) {
                    projectile.update();
                }
                if (!projectile.alive) {
                    iterator.remove();
                }
            }
        }

        for(int i = 0; i < particleList.size(); i++) {
            if(particleList.get(i) != null) {
                if(particleList.get(i).alive) {
                    particleList.get(i).update();
                }
                if(!particleList.get(i).alive) {
                    particleList.remove(i);
                }
            }
        }

        Arrays.stream(interactiveTiles)
            .filter(Objects::nonNull)
            .forEach(InteractiveTile::update);

    }

    public void drawToTemporaryScreen() {
        // time stamp
        long drawStart = 0;
        if(keyH.showDebugText){
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState) {
            ui.draw(g2);
        } else {
            // TILE
            tileManager.draw(g2);

            for(int i = 0; i < interactiveTiles.length; i++) {
                if(interactiveTiles[i] != null) {
                    interactiveTiles[i].draw(g2);
                }
            }


            // POPULATING ENTITY LIST

            entityList.add(player); // PLAYER

            Arrays.stream(npc) // NPC
                .filter(Objects::nonNull)
                .forEach(entityList::add);

            Arrays.stream(objectSlots) // OBJECTS
                .filter(Objects::nonNull)
                .forEach(entityList::add);

            Arrays.stream(monsters)
                .filter(Objects::nonNull)
                .forEach(entityList::add);

            projectiles.stream()
                .filter(Objects::nonNull)
                .forEach(entityList::add);

            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

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
        if(keyH.showDebugText){
            long drawEnd = System.nanoTime();
            long passedTime = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            g2.drawString("WorldX: " + player.worldX, x, y);
            y+= lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y);
            y+= lineHeight;
            g2.drawString("Column: " + ((player.worldX + player.solidArea.x) / tileSize), x, y);
            y+= lineHeight;
            g2.drawString("Row: " + ((player.worldY + player.solidArea.y) / tileSize), x, y);
            y+= lineHeight;
            g2.drawString("Draw time: " + passedTime, x, y);
            System.out.println("Draw time: " + passedTime);
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(temporaryScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        g.dispose();
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
