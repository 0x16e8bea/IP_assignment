package example;

import controlP5.ControlP5;
import processing.core.PApplet;

/**
 * Created by Mikkel on 16-Nov-15.
 */
public class Window {

    public int bx, by, bw, bh;
    public int xCenter, yCenter;
    float alpha = 0;
    ControlP5 cp5;


    boolean isActive = false;
    boolean isVisible = false;

    boolean locked = false;
    boolean overBox = false;

    PApplet pApplet;

    public void draggable() {
        if (pApplet.mouseX > bx && pApplet.mouseX < bx + bw &&
                pApplet.mouseY > by - 17 && pApplet.mouseY < by + bh) {
            overBox = true;
        } else {
            overBox = false;
        }
    }

    public void mousePressed(int mouseX, int mouseY) {
        locked = true;

        xCenter = mouseX - (bx + bw / 2);
        yCenter = mouseY - (by + bh / 2);
    }

    public void mouseDragged(int mouseX, int mouseY) {
        if (locked && overBox == true) {

            this.bx = mouseX - xCenter - bw / 2;
            this.by = mouseY - yCenter - bh / 2;
        }
    }

    public void mouseReleased() {
        locked = false;
    }
}
