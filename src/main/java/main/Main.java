package main;

import javax.swing.*;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        // basic window configuration
        // We're using the JFrame library to create a canvas that we will use to show our game
        // we can close it clicking in the exit option on the window.
        // the window isn't resizable
        // and its visible to us

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Adventure");
        window.setUndecorated(true);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // will make this window size fit the preferred size and layouts of its subcomponents (gamePanel)

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
