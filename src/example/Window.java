package example;

import controlP5.ControlP5;
import processing.core.PApplet;

/**
 * Created by Mikkel on 16-Nov-15.
 */
public class Window {

    public int bx, by, bw, bh;
    public int iw = 25, ih = 15;
    public int xCenter, yCenter;

    ControlP5 cp5;

    boolean isActive = false;
    boolean isVisible = false;

    boolean locked = false;
    boolean overBox = false;

    PApplet pApplet;

    public void drawBar() {
        pApplet.pushStyle();
        pApplet.fill(0, 72, 145);
        pApplet.stroke(0, 72, 145);
        pApplet.rect(bx, by - ih, bw - 1, bh - 1);
        pApplet.popStyle();
    }

    public void draggable() {
        if (pApplet.mouseX > bx && pApplet.mouseX < bx + bw &&
                pApplet.mouseY > by - ih && pApplet.mouseY < by) {
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
