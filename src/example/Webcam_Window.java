package example;

import processing.core.*;
import processing.video.*;

import java.awt.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Webcam_Window extends Window {

    Capture video;

    Image_Processing[] processImg = new Image_Processing[3];

    public void start(PApplet pApplet, int vW, int vH, int x, int y) {

        this.bw = vW;
        this.bh = vH;
        this.bx = x;
        this.by = y;

        video = new Capture(pApplet, bw, bh);
        video.start();
        isActive = true;

        for (int i = 0; i < processImg.length; i++) {
            processImg[i] = new Image_Processing(video);
        }

    }

    public int[] applyFilters() throws ExecutionException, InterruptedException {


        processImg[0].run(video.pixels)
                .normalize()
                .threshold(50, 0, 92, 162)
                .erosion(4)
        ;

        return processImg[0].out;
    }

    public void display(PApplet applet) {

        applet.image(video, bx, by);

    }
}
