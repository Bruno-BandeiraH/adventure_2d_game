package main;

import java.awt.*;

public class EventRectangle extends Rectangle {

    int eventDefaultX, eventDefaultY;
    boolean eventDone = false;

    public EventRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
