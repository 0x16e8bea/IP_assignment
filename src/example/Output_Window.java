package example;

import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Mikkel on 18-Nov-15.
 */
public class Output_Window extends Window {

    PImage img;

    public void init(PApplet pApplet, ControlP5 cp5, int vW, int vH, int x, int y) {

        this.pApplet = pApplet;

        this.bw = vW;
        this.bh = vH;
        this.bx = x;
        this.by = y;

        img = new PImage(vW, vH);
        this.cp5 = cp5;

    }

    public void display(int[] c) {

        img.loadPixels();

        pApplet.image(img, bx, by);

        for (int i = 0; i < c.length; i++) {
            img.pixels[i] = c[i];
        }

        img.updatePixels();
    }
}
