package example;

import controlP5.ControlP5;
import controlP5.Group;
import gab.opencv.OpenCV;
import processing.core.*;
import processing.video.*;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Webcam_Window extends Window {

    Capture video;
    Image_Processing processImg;
    OpenCV openCV;

    Histogram_Window histogram;

    Webcam_Window(PApplet pApplet, ControlP5 cp5, int vW, int vH, int x, int y) {

        this.pApplet = pApplet;

        this.bw = vW;
        this.bh = vH;
        this.bx = x;
        this.by = y;

        video = new Capture(pApplet, bw, bh);

        video.start();
        processImg = new Image_Processing(video);

        histogram =  new Histogram_Window(pApplet, cp5, 320, 240, 100, 100);

        // ControlP5
        this.cp5 = cp5;

        cp5.begin(0,0);

        Group option_group = cp5.addGroup("option_group")
                .setLabel("options");

        cp5.addButton("calibrate_btn")
                .setLabel("CALIBRATE")
                .setGroup(option_group);

        cp5.addButton("x_btn")
                .setLabel("X")
                .setSize(iw,ih)
                .hide();

        cp5.getGroup("option_group").moveTo("global");
        cp5.getGroup("option_group").hide();
        cp5.getGroup("option_group").close();

        cp5.end();

        cp5.getController("x_btn").moveTo("global");
    }

    public int[] applyFilters() {

        processImg.init(video.pixels).normalize();

        return processImg.out;
    }

    public Webcam_Window display() {

        if (isActive)
        update();

        if (histogram.isActive) {
            histogram.display(applyFilters());
        }

        if (isActive && !isVisible) {

            cp5.getGroup("option_group").show();
            cp5.getController("x_btn").show();

            isVisible = true;
        } else if(!isActive && isVisible) {
            cp5.getController("x_btn").hide();
            cp5.getGroup("option_group").hide();

            isVisible = false;
        }

        return this;
    }

    public void update() {
        draggable();
        drawBar();

        cp5.begin();
        cp5.getGroup("option_group").setPosition(bx, by + bh + cp5.getGroup("option_group").getHeight());
        cp5.end();

        cp5.begin();
        cp5.getController("x_btn").setPosition(bx + bw - cp5.getController("x_btn").getWidth(), by - cp5.getController("x_btn").getHeight());
        cp5.end();

        pApplet.image(video, bx, by);
    }


        public Webcam_Window debugBrightestPoint() {
        PVector loc = openCV.max();
        pApplet.pushStyle();
        pApplet.stroke(255, 0, 0);
        pApplet.strokeWeight(4);
        pApplet.noFill();
        pApplet.ellipse(bx + loc.x, by + loc.y, 10, 10);
        pApplet.popStyle();

        return this;
    }
}
