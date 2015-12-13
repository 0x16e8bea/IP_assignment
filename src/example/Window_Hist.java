package example;

import controlP5.ControlEvent;
import gab.opencv.Histogram;
import gab.opencv.OpenCV;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Mikkel on 07-Dec-15.
 */
public class Window_Hist extends Window {

    PImage img;
    OpenCV openCV;
    int color = 1;
    int[] c;

    public int range_min = 100;
    public int range_max = 150;

    Histogram histogram;

    Window_Hist(PApplet pApplet, int[] c, int vW, int vH, int x, int y) {

        super(pApplet);

        this.bW = vW;
        this.bH = vH;
        this.bX = x;
        this.bY = y;
        this.c = c;

        img = new PImage(vW, vH);
        openCV = new OpenCV(pApplet, bW, bH);

        cp5.getController("bar").setLabel("HISTOGRAM_WINDOW_ID: " + id);

        cp5.begin();
        cp5.addRange("hsb_range")
                .setBroadcast(false)
                .setPosition(0, 480)
                .setSize(bW, 20)
                .setHandleSize(20)
                .setRange(0, 255)
                .setRangeValues(range_min, range_max)
                .setBroadcast(true)
                .hide();


        cp5.end();

        cp5.getController("hsb_range").moveTo("global");

    }

    public void update() {

        super.update();

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

        pApplet.image(openCV.getOutput(), bX, bY);

        cp5.getController("hsb_range").setPosition(bX, bY + bH);
        update_histogram();

    }

    public void update_histogram() {

        pApplet.noStroke();
        pApplet.pushStyle();

        pApplet.fill(255, 0, 0);
        histogram.draw(bX, bY, bW, bH);
        pApplet.fill(0, 255, 0);

        pApplet.popStyle();

    }

    @Override
    public void controlEvent(ControlEvent theControlEvent) {
        super.controlEvent(theControlEvent);

        if (theControlEvent.isFrom("hsb_range")) {
            range_min = (int) (theControlEvent.getController().getArrayValue(0));
            range_max = (int) (theControlEvent.getController().getArrayValue(1));
        }
    }
}
