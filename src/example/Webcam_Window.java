package example;

import processing.core.PApplet;
import processing.video.Capture;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Webcam_Window extends Window {

    Capture video;
    Image_Processing[] processImg = new Image_Processing[1];

    boolean draw = false;

    public void start(PApplet pApplet, int vW, int vH, int x, int y) {

        this.bw = vW;
        this.bh = vH;
        this.bx = x;
        this.by = y;

        //this.pApplet = pApplet;

        video = new Capture(pApplet, bw, bh);
        video.start();

        isActive = true;

        for (int i = 0; i < processImg.length; i++) {
            processImg[i] = new Image_Processing(video);
        }
    }

    public void display(PApplet applet) {

        applet.image(video, bx, by);

    }

    public int[] applyFilters() {

        processImg[0].run(video.pixels)
                .normalize()
                .threshold(40, 0, 153, 101)
                .erosion(4)
                .dilation(3)
        ;

        return processImg[0].out;
    }
}
