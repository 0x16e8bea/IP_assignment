package example;

import gab.opencv.OpenCV;
import org.w3c.dom.css.RGBColor;
import processing.core.*;
import processing.video.*;

import java.awt.*;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Webcam_Window extends Window {

    Capture video;

    Point_Processing processP;
    Neighbourhood_Processing processN = new Neighbourhood_Processing();

    public void start(PApplet pApplet, int vW, int vH, int x, int y) {

        this.bw = vW;
        this.bh = vH;
        this.bx = x;
        this.by = y;

        video = new Capture(pApplet, bw, bh);
        video.start();
        isActive = true;

        processP = new Point_Processing(video.pixels);

    }

    public int[] applyNormalize() {

        processP.normalize(video.pixels);

        return processP.out;

    }

    public void display(PApplet applet) {

        applet.image(video, bx, by);

    }



}
