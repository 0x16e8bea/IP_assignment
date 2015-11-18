package example;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Mikkel on 18-Nov-15.
 */
public class Output_Window extends Window {

    PImage img;

    public void start(PApplet pApplet, int vW, int vH, int x, int y) {

        this.bw = vW;
        this.bh = vH;
        this.bx = x;
        this.by = y;

        img = new PImage(vW,vH);
    }

    public void display(PApplet applet, int[] c) {

        img.loadPixels();
        applet.image(img, bx, by);

        for (int i = 0; i < c.length; i++) {
            img.pixels[i] = c[i];
        }

        img.updatePixels();

    }

}
