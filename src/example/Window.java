package example;

import processing.core.PApplet;

/**
 * Created by Mikkel on 16-Nov-15.
 */
public class Window {

    public int bx, by, bw, bh;
    float alpha = 0;
    public int xCenter, yCenter;

    boolean isActive = false;

    boolean animate = true;
    boolean overBox;

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
            if (!Main.locked) {
                if (alpha < 50)
                    alpha += 4;

                pApplet.fill(0, 170, 255, alpha);
            }
        } else {
            if (alpha > 0)
                alpha -= alpha / 4 + 1;

            overBox = false;
            pApplet.fill(0, 170, 255, alpha);
        }

        if (pApplet.mousePressed == true && overBox == true) {
            if(alpha < 150)
                alpha += 8;
        } else if (pApplet.mousePressed == false && overBox == true) {
            if(alpha > 50)
                alpha -= 1 + alpha/10;
        }
    }

    public void mousePressed(int mouseX, int mouseY, int button) {

        xCenter = mouseX - (bx + bw/2);
        yCenter = mouseY - (by + bh/2);

    }

    public void mouseDragged(int mouseX, int mouseY, int button) {
        if(!Main.locked && overBox == true) {


            this.bx = mouseX - xCenter - bw/2;
            this.by = mouseY - yCenter - bh/2;
        }
    }
}
