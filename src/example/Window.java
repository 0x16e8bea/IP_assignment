package example;

import processing.core.PApplet;

/**
 * Created by Mikkel on 16-Nov-15.
 */
public class Window {

    public int bx, by, bw, bh;
    public int xCenter, yCenter;
    float alpha = 0;

    boolean isActive = false;
    boolean locked = false;
    boolean animate = true;
    boolean overBox = false;

    PApplet pApplet;

    public void draggable(PApplet pApplet) {
        pApplet.pushStyle();

        if (pApplet.mouseX > bx && pApplet.mouseX < bx + bw &&
                pApplet.mouseY > by && pApplet.mouseY < by + bh) {
            overBox = true;
        } else {
            overBox = false;
        }

        pApplet.stroke(0, 170, 255);

        if (animate == true)
            animate(pApplet);

        // Draw the box
        pApplet.rect(bx, by, bw, bh);
        pApplet.popStyle();

    }

    public void animate(PApplet pApplet) {

        if (overBox == true) {

                pApplet.fill(0, 170, 255, alpha);

            if (pApplet.mousePressed == true) {
                if (alpha < 150)
                    alpha += 8;
            } else {
                if (alpha <= 50)
                    alpha += 4;
                else {
                    if (alpha > 50)
                        alpha -= 1 + alpha / 10;
                }
            }
        } else {
            if (alpha > 0)
                alpha -= alpha / 4 + 1;
            pApplet.fill(0, 170, 255, alpha);
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
