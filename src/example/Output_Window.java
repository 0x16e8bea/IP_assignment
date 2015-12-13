package example;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Mikkel on 18-Nov-15.
 */
public class Output_Window extends Window {

    PImage img;

    public Output_Window(PApplet pApplet, int vW, int vH, int x, int y, int id) {

        super(pApplet);

        this.bW = vW;
        this.bH = vH;
        this.bX = x;
        this.bY = y;

        img = new PImage(vW, vH);

    }

    public void display(int[] c) {

        img.loadPixels();

        pApplet.image(img, bX, bY);

        for (int i = 0; i < c.length; i++) {
            img.pixels[i] = c[i];
        }

        img.updatePixels();
    }
}
