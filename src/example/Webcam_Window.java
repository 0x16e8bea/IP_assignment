package example;

import processing.core.*;
import processing.video.*;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Webcam_Window extends Window {

    Capture video;

    Image_Processing processImg;

    public void start(PApplet pApplet, int vW, int vH, int x, int y) {

        this.bw = vW;
        this.bh = vH;
        this.bx = x;
        this.by = y;

        video = new Capture(pApplet, bw, bh);
        video.start();
        isActive = true;

        processImg = new Image_Processing(video);

    }

    public int[] applyNormalize() {

        processImg.run(video.pixels)
                .normalize()
                .threshold()
                .erosion()
                .dilation()
        ;

        return processImg.out;
    }

    public void display(PApplet applet) {

        applet.image(video, bx, by);

    }
}
