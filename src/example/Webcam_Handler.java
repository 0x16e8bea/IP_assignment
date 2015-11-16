package example;

import gab.opencv.OpenCV;
import processing.core.*;
import processing.video.*;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Webcam_Handler extends Window {

    Capture video;
    boolean isActive = false;

    public void start(PApplet pApplet, int vW, int vH, int x, int y) {

        this.bw = vW;
        this.bh = vH;
        this.bx = x;
        this.by = y;

        video = new Capture(pApplet, bw, bh);
        video.start();
        isActive = true;


        // Temporarily here because of a bug

    }

    public void display(PApplet applet) {

        applet.image(video, bx, by);

    }



}
