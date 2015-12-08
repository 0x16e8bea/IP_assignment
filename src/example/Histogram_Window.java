package example;

import controlP5.ControlP5;
import controlP5.Group;
import gab.opencv.Histogram;
import gab.opencv.OpenCV;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Mikkel on 07-Dec-15.
 */
public class Histogram_Window extends Window {

    PImage img;
    OpenCV openCV;
    int color = 1;

    public int range_min = 100;
    public int range_max = 150;

    Histogram histogram;

    Histogram_Window(PApplet pApplet, ControlP5 cp5, int vW, int vH, int x, int y) {

        this.pApplet = pApplet;

        this.bw = vW;
        this.bh = vH;
        this.bx = x;
        this.by = y;

        img = new PImage(vW, vH);
        openCV = new OpenCV(pApplet, bw, bh);

        // ControlP5
        this.cp5 = cp5;

        cp5.begin();
        cp5.addRange("hsb_range")
                .setBroadcast(false)
                .setPosition(0, 480)
                .setSize(bw, 20)
                .setHandleSize(20)
                .setRange(0, 255)
                .setRangeValues(range_min, range_max)
                .setBroadcast(true)
                .hide();


        cp5.end();

        cp5.getController("hsb_range").moveTo("global");

    }

    public void display(int[] c) {

        if (isActive)
            cp5.getController("hsb_range").show();


        img.loadPixels();

        for (int i = 0; i < c.length; i++) {
            img.pixels[i] = c[i];
        }

        img.updatePixels();

        openCV.loadImage(img);

        openCV.useColor(pApplet.HSB);
        openCV.setGray(openCV.getH().clone());
        histogram = openCV.findHistogram(openCV.getH(), 255);
        openCV.inRange(range_min, range_max);

        pApplet.image(openCV.getOutput(), bx, by);

        draggable();

        update_interface();
        update_histogram();

    }

    public void update_interface() {

        cp5.getController("hsb_range").setPosition(bx, by + bh);

    }

    public void update_histogram() {

        pApplet.noStroke();
        pApplet.pushStyle();

        pApplet.fill(255, 0, 0);
        histogram.draw(bx, by, bw, bh);
        pApplet.fill(0, 255, 0);


        pApplet.popStyle();

    }
}
